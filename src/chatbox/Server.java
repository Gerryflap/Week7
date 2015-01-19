package chatbox;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server extends Thread{
	
	private static final String USAGE = "usage: " + Server.class.getName() + " <port>";
	private ServerSocket socket;
	private ArrayList<ClientObject> clients;
	private boolean active;
	
	public Server(ServerSocket socket){
		this.socket = socket;
		this.clients = new ArrayList<ClientObject>();
		this.active = true;
	}

	/** Start een Server-applicatie op. */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(USAGE);
			System.exit(0);
		}


		InetAddress addr = null;
		int port = 0;
		ServerSocket sock = null;

		// parse args[1] - the port
		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println(USAGE);
			System.out.println("ERROR: port " + args[0] + " is not an integer");
			System.exit(0);
		}

		// try to open a Socket to the server
		try {
			sock = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("ERROR: could not create a socket on " + addr
					+ " and port " + port);
		}
		
		Server server = new Server(sock);
		server.start();
		// create Peer object and start the two-way communication

	}
	
	public void run(){
		while (active){
			try {
				Socket theSocket = this.socket.accept();
				ClientObject tempClient = new ClientObject(theSocket, this);
				System.out.println("A new client has connected");
				this.clients.add(tempClient);
				new Thread(tempClient).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void messageAll(ClientObject sender, String message){
		synchronized(this.clients){
			for (ClientObject client: this.clients){
				if (client != sender){
					try{
						client.message(sender, message);
					} catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void removeClient(ClientObject client){
		synchronized(this.clients){
			this.clients.remove(client);
		}
	}
	
}
