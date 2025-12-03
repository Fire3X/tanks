package io.github.tanks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Client {


    private Socket connectionSocket;
    private player thisPlayer;
	private BufferedReader in;
	private PrintWriter out;
    private int clientID;
    private ShapeRenderer shapeRenderer;
    private ArrayList<Opponent> opponents;
	


    Client(String a, int p, ShapeRenderer s) throws UnknownHostException, IOException { 

        opponents = new ArrayList<Opponent>();
        shapeRenderer = s;

        connectionSocket = new Socket(InetAddress.getByName(a), p);
        in = new BufferedReader(new InputStreamReader((connectionSocket.getInputStream())));
        out = new PrintWriter(connectionSocket.getOutputStream(), true);
    	
        thisPlayer = new player(0, 0, shapeRenderer);
        System.out.println("yo this is player");
    }
    
    public Socket getSocket() {
    	return this.connectionSocket;
    }
    
    public void setId(int i){

        clientID = i;
    }

    public void update(){   //l'unico metodo che fa update bullets
                            //prima veniva fatto anche dagli update di opponent == velocità anomale
        thisPlayer.update();

        for(Opponent o : opponents){
            o.updateBullets();
        }
    }

    public void firstConnection(String[] message){ // f (id di questo client) (vari id di client già connessi)

        this.clientID = Integer.parseInt(message[1]);

        for(int i = 2; i < message.length; i++){

            opponents.add(new Opponent(message[i], shapeRenderer));
        }
    }

    public void addOpponent(String m){ // n (numero del client nuovo)

        opponents.add(new Opponent(m, shapeRenderer));
        out.println(thisPlayer.getHitbox().x+" "+thisPlayer.getHitbox().y);
    }

    public BufferedReader getInputStream() {
    	return this.in;
    }
    
    public PrintWriter getOutputStream() {
    	return this.out;
    }

    public void sendData(){

        if(thisPlayer.hasShot()){
            out.println(thisPlayer.getHitbox().x+" "+thisPlayer.getHitbox().y+" "
                +thisPlayer.getBulletDestX()+" "+thisPlayer.getBulletDestY());
            thisPlayer.resetShot();
        }
        //no else, così può mandarli tutti e due
        if (thisPlayer.hasMoved()) {
            out.println(thisPlayer.getHitbox().x+" "+thisPlayer.getHitbox().y);
            thisPlayer.resetMovement();
        }

        
        
    }
    
    public void receiveData() throws IOException {

    	String s = null;

        
        while (in.ready()) {//ready controlla se il buffer è pronto, altrimenti sarebbe bloccante
                            //con un while potrà leggere più messaggi ogni frame
            s = in.readLine();
            System.out.println("recived: " + s);
            String[] message = s.split("\\s+");

            String flag = message[0];           //il flag può essere f, n, oppure un'id

            if(flag.equals("f")){ firstConnection(message); } 
            else if(flag.equals("n")){ addOpponent(message[1]); }
            else {

                int opponentToUpdate = Integer.parseInt(message[0]);
                    
                for(Opponent o : opponents){
                    if (o.getId() == opponentToUpdate){

                        float enemyPosX = Float.parseFloat(message[1]);
                        float enemyPosY = Float.parseFloat(message[2]);

                        if(message.length > 3){
                            //coord is not what you think
                            float enemyBulletX = Float.parseFloat(message[3]);
                            float enemyBulletY = Float.parseFloat(message[4]);
                            o.update(enemyPosX, enemyPosY, enemyBulletX, enemyBulletY);

                        } else {

                            o.update(enemyPosX, enemyPosY);
                        }
                    }
                }
            }
        }           
    }

    public void drawOpponents(){

        for(Opponent o : opponents){

            o.draw();
        }
    }

    public void draw(){

        thisPlayer.draw();
        drawOpponents();
    }
}
