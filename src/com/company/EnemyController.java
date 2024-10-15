package com.company;

import java.awt.*;
import java.util.LinkedList;

public class EnemyController {
    private LinkedList<Enemy> enemies = new LinkedList<Enemy>();
    private Enemy tempEnemy;

    public void changePosition(){
        for (int i = 0 ; i < enemies.size() ; i++){
            tempEnemy = enemies.get(i);
            tempEnemy.changePosition();
        }
    }
    public void render(Graphics2D g){
        for (int i = 0 ; i < enemies.size() ; i++){
            tempEnemy = enemies.get(i);
            tempEnemy.render(g);
        }
    }
    public void addEnemy(Enemy enemy){
        enemies.add(enemy);

    }
    public void removeEnemy(Enemy enemy){
        enemies.remove(enemy);
    }

    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }
    public int getX(){
        return enemies.get(enemies.size()-1).getX();
    }
    public int getY(){
        return enemies.get(enemies.size()-1).getY();
    }

}
