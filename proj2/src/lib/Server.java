package lib;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;


public class Server implements Runnable {
	public static String FILE_NOT_FOUND = "file_not_found";
	public static String FILE_ACK = "file_ACK";
	public static String ACCESS_DENIED = "access denied";
	public static String ACCESS_GRANTED = "access granted";
	public static String FINISHED = "finished";


	private static ServerSocket socket1;
	private static int port;
	private Socket connection;
	private ReadWriteHandler rw;

	private HashMap<long[], String> userHash;
	private String currentUser = "";

	public Server(Socket s, int port) {
		this.connection = s;
		this.port = port;
		rw = new ReadWriteHandler(s);
	}


	public void loadUsers(HashMap<long[], String> users) {
		userHash = users;
	}


	//TODO: login!!!
	public boolean checkUser() throws IOException {
		byte[] receivedUser = rw.read();
		for(long[] key : userHash.keySet()) {
			// check known keys against received encrypted user
			rw.setKey(key);
			byte[] decr = rw.decrypt(receivedUser);
			String decrStr = new String(decr).trim();
			if (userHash.get(key).equals(decrStr)) {
				System.out.println("Found a user!");
				currentUser = userHash.get(key);
				rw.setKey(key);
				System.out.println(currentUser + " granted access.");
				rw.write(rw.encrypt(ACCESS_GRANTED.getBytes()));
				return true;
			}
		}
		byte[] message = ACCESS_DENIED.getBytes();
		rw.write(rw.encrypt(message));
		return false;

	}


	public void stop() throws IOException {
		String message = "Client connection stopped. ";
		if (!currentUser.isEmpty()) {
			message += "Goodbye #{currentUser}.";
		}
		System.out.println(message);
		connection.close();
		Thread.currentThread().interrupt();
	}

	
	@Override
	public void run() {
		try {
			if (checkUser()) {
				byte[] receivedRequest = null;
				String reqString = "";
				Path path = Paths.get(reqString);
				while (!reqString.equals(FINISHED)) {
					receivedRequest = rw.decrypt(rw.read());
					reqString = new String(receivedRequest);
					path = Paths.get(reqString);
					if (Files.isRegularFile(path) && Files.isReadable(path)) {
						System.out.println("Sending "+reqString+" to "+currentUser);
						rw.write(rw.encrypt(FILE_ACK.getBytes()));
						
						byte[] file = Files.readAllBytes(path);
						rw.write(file);
					} else {
						rw.write(rw.encrypt(FILE_NOT_FOUND.getBytes()));
					}
				}
			} // end checkUser

		} catch (SocketException e) {
			System.err.println("Server: Socket Error-- "+e);
		} catch (Exception e) {
			System.err.println("Server: Error--" +e);
		
		} finally {
			try { stop(); } catch (IOException e) {}
		}	
	}

}
