package lib;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class ServerRunner {

	private static HashMap<long[], String> users;
	private static int port = 16000; //hardcoded 16000

	private static ServerSocket serversock;
	
	public static void initializeUsers() {
		users = new HashMap<long[], String>();
		users.put(new long[] {1827361872L,194291L, 91487L, 1784619L}, 	"Client1");
		users.put(new long[] {129048L, 1249081L, 89L, 9035717L}, 		"Client2");
		users.put(new long[] {5139841L, 1839741L, 80808808L, 279381L}, 	"Client3");
		users.put(new long[] {8018048L, 192837L, 938401983L, 2342L}, 	"Client4");
		users.put(new long[] {1827L,11111L, 90923201L, 178461119L}, 	"Client5");
		users.put(new long[] {361872L, 1421L, 9999147L, 22222222L}, 	"Client6");
	}

	public static void runServer() {
		int count = 0;	
		try {
			ServerSocket serversock = new ServerSocket(port);
			System.out.println("Starting Server");
			while (true) {
				Server server = new Server(serversock.accept(), ++count);
				System.out.println("Incoming socket connection");
				server.loadUsers(users);
				
				Thread thread = new Thread(server);
				thread.start();
			}
		} catch (Exception e) {
			System.err.println("Error in runServer." +e);
			
		} finally {
			try {
				serversock.close();
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Error closing server socket " +e);
			}
		}
		//TODO: killing the threads!
		System.out.println("Server Closed. Goodbye.");

	}
	
	public static void main (String[] args) {
		initializeUsers();
		runServer();
	}
	
}
