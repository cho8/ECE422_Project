package lib;

import java.io.*;
import java.net.*;
import java.util.*;


public class Server implements Runnable {
	public static String ACK = "ack";
	public static String FILE_NOT_FOUND = "file_not_found";
	public static String ACCESS_DENIED = "access denied";
	public static String ACCESS_GRANTED = "access granted";
	public static int port = 16000;
	
	static ServerSocket socket1;
	private Socket connection;
	private int socketID;
	
	static StringBuffer process;
	private HashMap<long[], String> userHash;
	
	public Server(Socket s, int i) {
		this.connection = s;
		this.socketID = i;
	}
	
	public void loadUsers(HashMap<long[], String> users) {
		userHash = users;
	}
	
	//TODO: login!!!
//	public checkUsers (String client, TinyEncryption key)
	
	public String readFromSocket() throws IOException {
		BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
		InputStreamReader isr = new InputStreamReader(bis);
		process = new StringBuffer();
		//TODO: What is 13?
		int c;
		while( (c=isr.read()) != 13) {
			process.append((char)c);
		}
		return process.toString();
	}
	
	public void writeToSocket(String returnCode) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
		OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
		osw.write(returnCode);
		osw.flush();
	}

	@Override
	public void run() {
		try {
			String receive = readFromSocket();
			//TODO: do filename read things
			//TODO: What am I returning?
//			writeToSocket();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try { connection.close(); } catch (IOException e) {}
		}	
	}
	
}
