package io.github.tanks;

import java.io.BufferedReader;
import java.io.IOException;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Opponent {
    private Rectangle hitbox;

    public Opponent(){

        hitbox = new Rectangle(0, 0, 100, 100);

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
    	}while(s == null);
    	System.out.println("coordinate ricevute: "+s);/////////////////////////////////////////////
    	String[] coord = new String[2];
    	coord = s.trim().split("\\s+");
    	System.out.println();
    	
    	setPos(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]));
    	
    }



    public void draw(ShapeRenderer s){

        s.setColor(Color.RED);
        s.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

    }
}
