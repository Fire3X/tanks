package io.github.tanks;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;

public class player {
    
    private Rectangle hitbox;
    private Vector2 movementVector;
    private float speed;
    private ArrayList<bullet> bullets;

    ShapeRenderer shapeRenderer;

    private float bulletStartX;
    private float bulletStartY;

    private float bulletDestX;
    private float bulletDestY;

    private float clickX;
    private float clickY;

    private boolean hasShot;
    private boolean hasMoved;

    public player(int posx, int posy, ShapeRenderer s){

        shapeRenderer = s;
        hitbox = new Rectangle(posx, posy, 100, 100);
        movementVector = new Vector2();
        speed = 500;
        bullets = new ArrayList<bullet>();
        hasShot = false;
        hasMoved = false;

        bulletDestX = 0;
        bulletDestY = 0;
    }

    public Rectangle getHitbox() {
    	return this.hitbox;
    }
    
    public float getBulletDestX() {
    	return this.bulletDestX;
    }
    
    public float getBulletDestY() {
    	return this.bulletDestY;
    }
    
    public boolean hasShot() {
    	return this.hasShot;
    }

    public void resetShot(){
        this.hasShot = false;
    }

    public boolean hasMoved() {
    	return this.hasMoved;
    }

    public void resetMovement(){
        this.hasMoved = false;
    }


    public void shoot(){

        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            hasShot = true;
            bulletStartX = hitbox.x + hitbox.width / 2;
            bulletStartY = hitbox.y + hitbox.height / 2;

            clickX = Gdx.input.getX();
            clickY = Gdx.input.getY();

            bulletDestX = clickX - bulletStartX;
            bulletDestY = Gdx.graphics.getHeight() - clickY - bulletStartY;
            
            bullets.add(new bullet(bulletStartX, bulletStartY, bulletDestX, bulletDestY, shapeRenderer));
        }


        for(bullet b : bullets)
            b.updatePos();
    }

    public void update(){

        move();
        updatePos();
        shoot();
    }

    public void move(){

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            movementVector.add(0, 1);
            hasMoved = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            movementVector.add(-1, 0);
            hasMoved = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            movementVector.add(0, -1);
            hasMoved = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            movementVector.add(1, 0);
            hasMoved = true;
        }
    }

    void updatePos(){

        hitbox.x+=movementVector.nor().x * speed * Gdx.graphics.getDeltaTime();
        hitbox.y+=movementVector.nor().y * speed * Gdx.graphics.getDeltaTime();
        movementVector.setZero();
    }

    public void draw(){

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        for(bullet b : bullets){

            b.draw();
        }
    }
}