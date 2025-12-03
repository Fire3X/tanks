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
    

    ShapeRenderer shapeRenderer;

    public bullet(float x, float y, float directionX, float directionY, ShapeRenderer s){

        shapeRenderer = s;
        speed = 10;
        radius = 20;
        startingPos = new Vector2(x, y);
        hitbox = new Circle(startingPos, radius);
        direction = new Vector2(directionX, directionY).nor();
    }

    

    void updatePos(){

        hitbox.x+=direction.x * speed * Gdx.graphics.getDeltaTime();
        hitbox.y+=direction.y * speed * Gdx.graphics.getDeltaTime();
    }

    public void draw(){

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(hitbox.x, hitbox.y, hitbox.radius);
    }
}


