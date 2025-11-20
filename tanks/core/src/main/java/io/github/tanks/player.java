package io.github.tanks;

import java.io.PrintWriter;
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

    public player(int posx, int posy){

        hitbox = new Rectangle(posx, posy, 100, 100);
        movementVector = new Vector2();
        speed = 10;
        bullets = new ArrayList<bullet>();
    }

    public void create(){

    } 

    public void shoot(){

        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
            bullets.add(new bullet(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height / 2, Gdx.input.getX(), Gdx.input.getY()));

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
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            movementVector.add(-1, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            movementVector.add(0, -1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            movementVector.add(1, 0);
        }
    }

    void updatePos(){

        hitbox.x+=movementVector.nor().x * speed;
        hitbox.y+=movementVector.nor().y * speed;
        movementVector.setZero();


    }
    
    void sendData(PrintWriter out) {
    	System.out.println("dati mandati");
    	out.println(hitbox.x+" "+hitbox.y);
    }

    public void draw(ShapeRenderer s){

        s.setColor(Color.BLUE);
        s.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        for(bullet b : bullets){

            b.draw(s);
        }
    }
}