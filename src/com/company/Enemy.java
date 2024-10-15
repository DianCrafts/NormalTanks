package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private int x;
    private int y;
    private boolean flag;
    private BufferedImage enemyTank;
    public Enemy(int x , int y){
        this.x = x;
        this.y = y;
        flag = false;
        try {
            enemyTank =  ImageIO.read(new File("Resources\\Images\\Enemy1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void changePosition(){
        if (x == 1000){
            flag = true;
        }
        if (x == 300){
            flag = false;
        }
        if (flag == false) {
            x += 5;
        }
        if (flag == true)
            x -= 5;
    }
    public void render(Graphics2D g){
        g.drawImage(enemyTank, x, y, null);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
