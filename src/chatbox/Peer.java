package chatbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Peer for a simple client-server application
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Peer implements Runnable {
	public static final String EXIT = "exit";

	protected String name;
	protected Socket sock;
	protected BufferedReader in;
	protected BufferedWriter out;
	protected static boolean running;


	/*@
	   requires (nameArg != null) && (sockArg != null);
	 */
	/**
	 * Constructor. creates a peer object based in the given parameters.
	 * @param   nameArg name of the Peer-proces
	 * @param   sockArg Socket of the Peer-proces
	 */
	public Peer(String nameArg, Socket sockArg) throws IOException
	{
		this.name = nameArg;
		this.sock = sockArg;
	}

	/**
	 * Reads strings of the stream of the socket-connection and writes the characters to the default output
	 */
	public void run() {
		try {
			this.in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
			this.out = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
			this.out.write("NAME "+this.name+"\n");
			this.out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		Peer.running = true;
		Scanner networkScanner = new Scanner(this.in);
		while(Peer.running){
			//Gebroken???
			while( networkScanner.hasNextLine() && Peer.running){
				if(Peer.running){
					String message = networkScanner.nextLine();
					if(message.equals(EXIT)){
						Peer.running = false;
						System.exit(0);
					}
					System.out.println(message);	
				}
			}
			
		}
		networkScanner.close();
				
	}


	/**
	 * Reads a string from the console and sends this string over the socket-connection to the Peer proces. On Peer.EXIT the method ends
	 * @throws IOException 
	 */
	public void handleTerminalInput(){
		Scanner systemInScanner = new Scanner(System.in);

		while(systemInScanner.hasNextLine() && Peer.running){
			String input = systemInScanner.nextLine();

			if (input.equals(EXIT)){
				
				System.out.println("Exit!");
				try {
					this.out.write("exit");
					this.out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Peer.running = false;
				System.exit(0);
				
			} else {
				try{
					this.out.write(String.format("%s \n", input));
					this.out.flush();
				} catch(IOException e){
					System.err.println("Network error: "+e.getMessage());
				}
			}
		}
	
		systemInScanner.close();
		
	}

	/**
	 * Closes the connection, the sockets will be terminated
	 */
	public void shutDown() {
		try {
			this.in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
	

	/**  returns name of the peer object*/
	public String getName() {
		return name;
	}

	/** read a line from the default input */
	static public String readString(String tekst) {
		System.out.print(tekst);
		String antw = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			antw = in.readLine();
		} catch (IOException e) {
		}

		return (antw == null) ? "" : antw;
	}
}
