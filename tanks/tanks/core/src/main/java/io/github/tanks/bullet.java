package io.github.tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class bullet {

    Vector2 startingPos;
    Vector2 direction;
    float speed;
    Circle hitbox;
    float radius;

    public bullet(float x, float y, float directionX, float directionY){

        speed = 10;
        radius = 20;
        startingPos = new Vector2(x, y);
        hitbox = new Circle(startingPos, radius);
        direction = new Vector2(directionX - x, Gdx.graphics.getHeight() - directionY - y).nor();
    }

    

    void updatePos(){

        hitbox.x+=direction.x * speed;
        hitbox.y+=direction.y * speed;
    }

    public void draw(ShapeRenderer s){

        s.setColor(Color.BLUE);
        s.circle(hitbox.x, hitbox.y, hitbox.radius);
    }
}


