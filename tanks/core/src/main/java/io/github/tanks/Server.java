package io.github.tanks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements Runnable {
	
	private ServerSocket ss = null;
	private ArrayList<ClientHandler> clientHandlers;
	private Random r;

		public Server(int porta) {

		r = new Random();
		clientHandlers = new ArrayList<ClientHandler>();

		try {
			ss = new ServerSocket(porta);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true) {
			//inizializza client
			try {
				Socket temp = ss.accept();
				ClientHandler c = new ClientHandler(temp, r.nextInt(999), clientHandlers);
				clientHandlers.add(c);
				new Thread(c).start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
