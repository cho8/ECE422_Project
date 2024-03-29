package lib;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.lang.StringBuffer;


public class Client {
	private String user;
	private String host = "localhost";
	private static int port = 16000; //hardcoded 16000
	
	private static String FILE_NOT_FOUND = "file_not_found";
	private static String FILE_ACK = "file_ACK";
	private static String ACCESS_DENIED = "access denied";
	private static String ACCESS_GRANTED = "access granted";
	private static String FINISHED = "finished";

	private Socket connection;
	private ReadWriteHandler rw;

	public Client(String user, long[] key) {
		this.user = user;
		try {
			connectSocket();
			rw = new ReadWriteHandler(connection);
			rw.setKey(key);
		} catch (Exception e) {
			System.err.println(e);
		}
		System.out.println("Client initialized");
	}

	public void connectSocket() throws IOException {
		
		// Address object of server
		InetAddress address = InetAddress.getByName(host);
		connection = new Socket(address, port);
	}
	
	public void closeSocket() throws IOException {
		System.out.println("Closing client connection.");
		connection.close();		
	}
	
	public boolean login() throws IOException {
		System.out.println("Logging in...");
		byte[] encrUser = rw.encrypt(user.getBytes());
		rw.write(encrUser);
		
		System.out.println("Receiving response...");
		byte[] receivedBytes = rw.read();
		String receivedMessage = new String(rw.decrypt(receivedBytes)).trim();

		if (receivedMessage.equals(ACCESS_DENIED)) {
			System.out.println("Login denied.");
			rw.write(FINISHED.getBytes());
			return false;
		} else if (receivedMessage.equals(ACCESS_GRANTED)) {
			System.out.println("Login successful.");
			return true;
		}
		System.out.println("No access message received from server.");
		return false;
	}
	
	public void requestFileNames() throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Request a filename. ('finished' to exit)\n>");
		String filename = "";
		// loop for multiple filenames
		while (true) {
			filename = scanner.next();
			if (filename.equals(FINISHED)) {
				rw.write(FINISHED.getBytes());
				return;
			}
			rw.write(filename.getBytes());
			byte[] received = rw.decrypt(rw.read());
			String receivedStr = new String(received).trim();
			
			if (receivedStr.equals(FILE_ACK)) {
				System.out.println("File found on server. Transfering...");
				byte[] receivedFile= rw.read();
				Path path = Paths.get(filename);
				Files.write(path, receivedFile);
				System.out.print("Done. Next?\n>");
			} else if (receivedStr.equals(FILE_NOT_FOUND)) {
				System.err.print("Filename not found, try again...\n>");
			} else {
				throw new IOException ("Unexpected response to filename");
			}
		}
	}





}
