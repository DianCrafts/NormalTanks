package com.company;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame implements ActionListener{
    private BufferedImage tankImage;// = new ImageIO();
    private BufferedImage bullImage;
    private BufferedImage enemyTank;
    private BufferedImage tankGunImage , tankGunImage2;// = new ImageIO();
    public static final int GAME_HEIGHT = 1080;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio

    //uncomment all /*...*/ in the class for using Tank icon instead of a simple circle
    /*private BufferedImage image;*/

    private long lastRender;
    private ArrayList<Float> fpsHistory;
    Graphics2D g2;
    private BufferStrategy bufferStrategy;

    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        lastRender = -1;
        fpsHistory = new ArrayList<>(100);

        try{
            enemyTank =  ImageIO.read(new File("Resources\\Images\\Enemy2.png"));
            bullImage =  ImageIO.read(new File("Resources\\Images\\HeavyBullet.png"));
            tankImage =  ImageIO.read(new File("Resources\\Images\\tank.png"));
            tankGunImage = ImageIO.read(new File("Resources\\Images\\tankGun01.png"));
            tankGunImage2 = ImageIO.read(new File("Resources\\Images\\tankGun02.png"));
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    /**
     * This must be called once after the JFrame is shown:
     *    frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());
            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {
        // Draw background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        // Draw ball

        g2 = (Graphics2D)g2d;
        if (state.getRotationFlag() == true){
            g2.rotate(state.getTetha() , state.locX , state.locY);
            g2.drawImage(tankImage , state.locX, state.locY  , null);
            g2.rotate(-state.getTetha() , state.locX , state.locY);
        }
        else {
            g2.drawImage(tankImage , state.locX, state.locY, null);
        }
        state.bulletController.render(g2d);
        state.enemyController.render(g2d);
        if (state.getRightClick() == false) {
            g2d.rotate(state.getAtan(), state.getGunX()+30, state.getGunY()+30);
            g2d.drawImage(tankGunImage, state.getGunX(), state.getGunY(), null);
            g2d.rotate(-state.getAtan(), state.locX + 30, state.locY + 30);
        }
        else {
            g2d.rotate(state.getAtan(), state.locX + 47, state.locY + 47);
            g2d.drawImage(tankGunImage2 , state.locX + 17, state.locY, null);
            g2d.rotate(-state.getAtan(), state.locX + 47, state.locY + 47);
        }
        // Print FPS info
        long currentRender = System.currentTimeMillis();
        if (lastRender > 0) {
            fpsHistory.add(1000.0f / (currentRender - lastRender));
            if (fpsHistory.size() > 100) {
                fpsHistory.remove(0); // remove oldest
            }
            float avg = 0.0f;
            for (float fps : fpsHistory) {
                avg += fps;
            }
            avg /= fpsHistory.size();
            // String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
            //   avg, (currentRender - lastRender));
//            g2d.setColor(Color.CYAN);
//            g2d.setFont(g2d.getFont().deriveFont(18.0f));
//            int strWidth = g2d.getFontMetrics().stringWidth(str);
//            int strHeight = g2d.getFontMetrics().getHeight();
//            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, strHeight+50);
        }
        lastRender = currentRender;
        // Print user guide
        String userGuide
                = "Use the MOUSE or ARROW KEYS to move the BALL. "
                + "Press ESCAPE to end the game.";
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        g2d.drawString(userGuide, 10, GAME_HEIGHT - 10);
        // Draw GAME OVER
        if (state.gameOver) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
