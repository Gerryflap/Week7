package chatbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientObject implements Runnable {
	private String name;
	private Socket socket;
	private boolean active;
	private Server server;
	private BufferedReader in;
	private BufferedWriter out;

	public ClientObject(Socket socket, Server server) throws IOException{
		this.name = "Connecting...";
		this.socket = socket;
		this.active = true;
		this.server = server;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	public void run() {
		while(active){
			try{
				String message = in.readLine();
				if(message.equals("exit")){
					remove();
				} else if(message.split(" ").length > 1 && message.split(" ")[0].equals("NAME")){
					String name = "";
					String[] messageData = message.split(" ");
					for (int i = 1; i < messageData.length; i++){
						if(i != 1){ name += " ";}
						name += messageData[i];
					}
					this.name = name;
				} else {
					server.messageAll(this, message);
				}
				
			} catch(IOException e){
				System.err.println("Client Disconnected");
				try {
					remove();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}	

		
	}
	
	public void message(ClientObject sender, String message) throws IOException{
		this.out.write(String.format("%s: %s \n", sender.getName(), message));
		this.out.flush();
	}
	
	public String getName(){
		return this.name;
	}
	
	public void remove() throws IOException{
		this.server.messageAll(this, "I have disconnected");
		active = false;
		this.server.removeClient(this);
		this.socket.close();
	}
	
	
}
