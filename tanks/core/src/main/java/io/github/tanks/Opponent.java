package io.github.tanks;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Opponent {
    private Rectangle hitbox;
	private ArrayList<bullet> enemyBullets;
	private float enemyPosX;
	private float enemyPosY;
	private float enemyBulletX;
	private float enemyBulletY;

    public Opponent(){

        hitbox = new Rectangle(0, 0, 100, 100);
		enemyBullets = new ArrayList<bullet>();
    }

    public void setPos(float x, float y){
    	hitbox.setX(x);
    	hitbox.setY(y);

    }
    
    void receiveData(BufferedReader in) {

    	String s = null;
    	do {
    		try {
				s = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} while(s == null);
    	System.out.println("coordinate ricevute: "+s);
    	String[] coord = s.split("\\s+");

    	System.out.println();
    	enemyPosX = Float.parseFloat(coord[0]);
		enemyPosY = Float.parseFloat(coord[1]);
    	setPos(enemyPosX, enemyPosY);
		//System.out.println(coord[2] + "bleb");
		if(coord.length > 2){
			//coord is not what you think
			enemyBulletX = Float.parseFloat(coord[2]);
			enemyBulletY = Float.parseFloat(coord[3]);
			bullet b = new bullet(enemyPosX + hitbox.width / 2, enemyPosY + hitbox.height / 2, enemyBulletX, enemyBulletY);
			enemyBullets.add(b);
		}
		for(bullet b : enemyBullets){
			b.updatePos();
		}
		System.out.println(enemyBullets.size() + "size of this bleb");
    }



    public void draw(ShapeRenderer s){

        s.setColor(Color.RED);
        s.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		for(bullet b : enemyBullets){
			b.draw(s);
		}
    }
}
