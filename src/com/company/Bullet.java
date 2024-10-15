package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet {
    private double alpha;
    private int x;
    private int y;
    private double sin;
    private double cos;
    private BufferedImage bullImage;
    public Bullet(int x , int y , double alpha){
        this.x = x;
        this.y = y;
        //
        sin = 0;
        cos = 0;
        //
        this.alpha = alpha;
        try {
            bullImage =  ImageIO.read(new File("Resources\\Images\\HeavyBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void changePosition(){
        sin = Math.sin(alpha);
        cos = Math.cos(alpha);
        x += cos * 15;
        y += sin * 15;
    }
    public void render(Graphics2D g){
        g.rotate(alpha, x , y);
        g.drawImage(bullImage, x, y, null);
        g.rotate(- alpha, x, y);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
