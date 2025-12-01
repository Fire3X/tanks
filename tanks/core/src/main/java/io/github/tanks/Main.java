package io.github.tanks;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;

import com.badlogic.gdx.utils.*;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private Client c;
    
    private Scanner scanner;
    private Server server;
        
    @Override
    public void create() {
    	//input iniziale per decidere chi fa da server e chi da client
    	scanner = new Scanner(System.in);
        shapeRenderer = new ShapeRenderer();
        

    	System.out.print("0 per host, 1 per client: ");
    	String choice = scanner.nextLine();
    	
    	//gestione client-server
    	if(choice.equals("0")) {
            
    		server = new Server(7979);
            new Thread(server).start();

            try {
                
                c = new Client("127.0.0.1", 7979, shapeRenderer);
                
                
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    	}
    	else {
    		System.out.print("indirizzo: ");
    		choice = scanner.nextLine();
    		System.out.print("porta: ");
    		int porta = scanner.nextInt();
    		
    		try {
    			c = new Client(choice, porta, shapeRenderer);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }

    @Override
    public void render() {

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        shapeRenderer.begin(ShapeType.Filled);
        
        //logica
        
        try {
            c.update();
            c.sendData();
            c.receiveData();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        //rendering
        
        c.draw();
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
