package com.company;

import java.awt.*;
import java.util.LinkedList;

public class BulletController {
    private LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    private Bullet tempBullet;

    public BulletController(){
    }

    public void changePosition(){
        for (int i = 0 ; i < bullets.size() ; i++){
            tempBullet = bullets.get(i);
            tempBullet.changePosition();
        }
    }
    public void render(Graphics2D g){
        for (int i = 0 ; i < bullets.size() ; i++){
            tempBullet = bullets.get(i);
            tempBullet.render(g);
        }
    }
    public void addBullet(Bullet bullet){
        bullets.add(bullet);

    }
    public void removeBullet(Bullet bullet){
        bullets.remove(bullet);
    }
    public LinkedList<Bullet> getBullets(){
        return bullets;
    }
    public int getX(){
        return bullets.get(bullets.size()-1).getX();
    }
    public int getY(){
        return bullets.get(bullets.size()-1).getY();
    }
}
