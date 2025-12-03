package io.github.tanks;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Opponent {
	
    private Rectangle hitbox;
	private ArrayList<bullet> enemyBullets;
	private int opponentID;
	private ShapeRenderer shapeRenderer;

	private float randomR;
	private float randomG;
	private float randomB;

    public Opponent(String id, ShapeRenderer s){

		randomR = MathUtils.random();
		randomG = MathUtils.random();
		randomB = MathUtils.random();

		shapeRenderer = s;
        hitbox = new Rectangle(0, 0, 100, 100);
		enemyBullets = new ArrayList<bullet>();
		setId(id);
    }

	public void setId(String id){ this.opponentID  = Integer.parseInt(id);}

	public int getId() { return this.opponentID; };

    public void setPos(float x, float y){
    	hitbox.setX(x);
    	hitbox.setY(y);
    }
    
	public void update(float x, float y){

		setPos(x, y);
	}

	public void update(float x, float y, float bulletDestX, float bulletDestY){

		setPos(x, y);

		bullet b = new bullet(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height / 2, bulletDestX, bulletDestY, shapeRenderer);
		enemyBullets.add(b);
	}

	public void updateBullets(){

		for(bullet b : enemyBullets){
			b.updatePos();
		}
	}

	public void draw(){

		shapeRenderer.setColor(randomR, randomG, randomB, 1);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

		for(bullet b : enemyBullets){
			b.draw();
		}
    }
}
