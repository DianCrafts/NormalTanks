package com.company;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState{
    public BulletController bulletController;
    public EnemyController enemyController;
    private int enemyX , enemyY;
    private Random random;
    private double alpha , firstAlpha;
    private boolean rotationFlag;
    public double tetha;
    public int locX, locY, diam;
    private int bullX , bullY;
    public boolean gameOver;
    private String preDirection;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress , rightClick;
    private double mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private int gunX , gunY;
    private double sin , cos;
    public GameState() {
        bulletController = new BulletController();
        enemyController = new EnemyController();
        enemyController.addEnemy(new Enemy(200 , 400));
        sin = 0;
        cos = 0;
        //
        gunX = 120;
        gunY = 120;
        //
        alpha = 0;
        firstAlpha = 0;
        //
        locX = 100;
        locY = 100;
        //
        bullX = 100;
        bullY = 100;
        //
        diam = 32;
        gameOver = false;
        //
        tetha = 0;
        preDirection = "right";
        rotationFlag = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        mousePress = false;
        rightClick = false;
        mouseX = 0;
        mouseY = 0;
        //
        enemyX = 100;
        enemyY = 100;
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

    }
    /**
     * The method which updates the game state.
     */

    public void update() {
        if (bulletController.getBullets().size()>0 && enemyController.getEnemies().size() > 0) {
            Bullet tempBullet = bulletController.getBullets().get(bulletController.getBullets().size() - 1);
            Enemy tempEnemy = enemyController.getEnemies().get(enemyController.getEnemies().size() - 1);
            if (tempBullet.getX() > tempEnemy.getX() && tempBullet.getX() < tempEnemy.getX() + 50 && tempBullet.getY() > tempEnemy.getY() && tempBullet.getY() < tempEnemy.getY() + 50) {
                enemyController.removeEnemy(tempEnemy);
                bulletController.removeBullet(tempBullet);
                //.getEnemies().get(enemyController.getEnemies().size() - 1).
            }
        }
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        setAtan(mouseX - locX - 31, mouseY - locY - 40);

        if (keyUP) {
            locY -= 8;
            gunY -= 8;
        }
        if (keyDOWN) {
            locY += 8;
            gunY += 8;
        }
        if (keyLEFT) {
            locX -= 8;
            gunX -= 8;
        }
        if (keyRIGHT) {
            locX += 8;
            gunX += 8;
        }

        locX = Math.max(locX, 0);
        locX = Math.min(locX, GameFrame.GAME_WIDTH - diam);
        locY = Math.max(locY, 0);
        locY = Math.min(locY, GameFrame.GAME_HEIGHT - diam);
        bulletController.changePosition();
        enemyController.changePosition();
    }
    public void setMousePress(){
        mousePress = false;
    }
    public int getBullX(){
        return bullX;
    }

    public int getBullY(){
        return bullY;
    }
    public KeyListener getKeyListener() {
        return keyHandler;
    }
    public MouseListener getMouseListener() {
        return mouseHandler;
    }
    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }

    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    if (preDirection.equals("right")) {
                        tetha -= Math.PI / 2;
                        rotationFlag = true;
                        locY += 80;
                        gunY -= 20;

                    }
                    else if (preDirection.equals("left")) {

                        tetha += Math.PI / 2;
                        rotationFlag = true;
                        locX -= 80;
                        gunX += 20;
                    }
                    keyUP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    if (preDirection.equals("right")) {
                        tetha += Math.PI / 2;
                        locX += 80;
                        gunX -= 20;
                        rotationFlag = true;
                    }
                    else if (preDirection.equals("left")) {
                        tetha -= Math.PI / 2;
                        locY -= 80;
                        gunY += 20;
                        rotationFlag = true;
                    }
                    keyDOWN = true;
                    break;
                case KeyEvent.VK_LEFT:
                    if (preDirection.equals("up")) {
                        tetha -= Math.PI / 2;
                        locX += 80;
                        gunX -= 20;
                        rotationFlag = true;
                    }
                    else if (preDirection.equals("down")) {
                        tetha += Math.PI / 2;
                        locY += 80;
                        gunY -= 20;
                        rotationFlag = true;
                    }
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (preDirection.equals("up")) {
                        tetha += Math.PI / 2;
                        locY -= 80;
                        gunY += 20;
                        rotationFlag = true;
                    }
                    else if (preDirection.equals("down")) {
                        tetha -= Math.PI / 2;
                        locX -= 80;
                        gunX += 20;
                        rotationFlag = true;
                    }
                    keyRIGHT = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    gameOver = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    if (!preDirection.equals("down"))
                        preDirection = "up";
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    if (!preDirection.equals("up"))
                        preDirection = "down";
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    if (!preDirection.equals("right"))
                        preDirection = "left";
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    if (!preDirection.equals("left"))
                        preDirection = "right";
                    break;
            }
        }

    }

    /**
     * The mouse handler.
     */
    class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                bulletController.addBullet(new Bullet(gunX + 23, gunY + 23, alpha));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == 3) {
                if (rightClick == true)
                    rightClick = false;
                else
                    rightClick = true;
            }
        }
    }
    public boolean getRightClick(){
        return rightClick;
    }
    public double getTetha(){
        return tetha;
    }
    public  boolean getRotationFlag(){
        return rotationFlag;
    }
    public void setAtan(double a , double b) {
        alpha = Math.atan((b / a));
        if (a < 0) {
            a = -a;
            alpha = Math.atan((b / a));
            alpha = Math.PI - alpha;
        }
    }

    public int getEnemyX(){
        return enemyX;
    }

    public int getEnemyY() {
        return enemyY;
    }

    public  void  setSin(){
        sin = Math.sin(firstAlpha);
        System.out.println("sin: " + sin);
    }
    public void setCos(){
        cos = Math.cos(firstAlpha);
    }
    public double getAtan(){
        return alpha;
    }
    public double getFirstAlpha(){
        return firstAlpha;
    }
    public boolean isMousePress() {
        return mousePress;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getCos() {
        return cos;
    }

    public double getSin() {
        return sin;
    }

    public int getGunX() {
        return gunX;
    }

    public int getGunY() {
        return gunY;
    }
}
