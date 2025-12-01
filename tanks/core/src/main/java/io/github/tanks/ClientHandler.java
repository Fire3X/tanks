package io.github.tanks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler implements Runnable {


    private BufferedReader in;
	private PrintWriter out;

    private ArrayList<ClientHandler> connectedClients;

    public int clientID;

    ClientHandler(Socket s, int id, ArrayList<ClientHandler> a) throws IOException {

        in = new BufferedReader(new InputStreamReader((s.getInputStream())));
        out = new PrintWriter(s.getOutputStream(), true);

        connectedClients = a;
        clientID = id;

        firstConnection();
        sendToEveryone("n " + clientID); //così i client già dentro sanno che c'è un nuovo user
    }

    public int getId(){ return this.clientID; }

    public void firstConnection(){

        String temp = "f " + clientID;

        for(ClientHandler c : connectedClients){
            temp = temp.concat(" " + c.getId()); //concat ritorna la stringa concatenata
        }

        out.println(temp);
    }

    public void sendData(String message){
        out.println(message);
    }

    public void sendToEveryone(String message){
        for(ClientHandler c : connectedClients){

            if(c.getId() != clientID){
                c.sendData(message);
            }
        }
    }

    public String receiveData() throws IOException{

        String s = null;
    	do {
    		try {
				s = in.readLine();
                
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} while (s == null);

        return s;
    }

    public String getStringWithID(String msg){

        return clientID + " " + msg;
    }

    @Override
    public void run() {
        
        try {

            while(true){
                String msg = receiveData();
                sendToEveryone(getStringWithID(msg));
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
