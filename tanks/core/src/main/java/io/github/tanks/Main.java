package io.github.tanks;import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;

import com.badlogic.gdx.utils.*;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private player p;
    private Opponent o;
    
    private Scanner scanner;
    
  //classi per la parte rete
    private ServerSocket ss = null;
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;
        
    @Override
    public void create() {
    	//input iniziale per decidere chi fa da server e chi da client
    	scanner = new Scanner(System.in);
    	System.out.print("0 per host, 1 per client: ");
    	String choice = scanner.nextLine();
    	
    	//gestione client-server                                                     QUESTA FUNZIONE E BLOCCANTE, DA ESEGUIRE IN UN THREAD SEPARATO
    	if(choice.equals("0")) {
    		try {
				this.ss = new ServerSocket(7979);
				this.socket = ss.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	else {
    		System.out.print("indirizzo: ");
    		choice = scanner.nextLine();
    		System.out.println("porta: ");
    		int porta = scanner.nextInt();
    		
    		try {
    			socket = new Socket(choice, porta);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	//init input-output socket
    	try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//inizializzazione giocatori
        shapeRenderer = new ShapeRenderer();
        p = new player(0,0);
        o = new Opponent();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        p.create();
        
        
        //logica
        p.update();
        p.sendData(out);
        o.receiveData(in);
        
        //rendering
        shapeRenderer.begin(ShapeType.Filled);
        p.draw(shapeRenderer);
        o.draw(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
