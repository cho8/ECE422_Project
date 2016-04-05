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


	static ServerSocket socket1;
	public static int port;
	private Socket connection;
	ReadWriteHandler rw;

	static StringBuffer process;
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
		//		 checks all keys with the client provided ID
		//		 if matches the clientID in its hash, send back an ack;
		byte[] receivedUser = rw.read();
		for(long[] key : userHash.keySet()) {
			byte[] decr = TinyEncryption.decryptData(receivedUser, key);
			if (userHash.get(key).equals(new String(decr))) {
				currentUser = userHash.get(key);
				rw.setKey(key);
				break;
			}
		}
		if (rw.getKey() != null) {
			System.out.println(ACCESS_GRANTED);
			byte[] message = ACCESS_GRANTED.getBytes();
			rw.write(rw.encrypt(message));
			return true;
		} else {
			System.out.println(ACCESS_DENIED);
			byte[] message = ACCESS_DENIED.getBytes();
			rw.write(rw.encrypt(message));
			return false;
		}
	}


	public void stop() throws IOException {
		System.out.println("Client is finished. Goodbye "+currentUser);
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
						System.out.println("Server found file "+ reqString);
						rw.write(rw.encrypt(FILE_ACK.getBytes()));
						
						byte[] file = Files.readAllBytes(path);
						rw.write(file);
					} else {
						rw.write(rw.encrypt(FILE_NOT_FOUND.getBytes()));
					}
				}
				stop();
			} // end checkUser

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { connection.close(); } catch (IOException e) {}
		}	
	}

}
