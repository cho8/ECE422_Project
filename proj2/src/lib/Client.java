package lib;

import java.io.*;
import java.net.*;
import java.lang.StringBuffer;


public class Client {
	private long[] key;
	private String user;
	private String host = "localhost";
	public static int port = 16000;

	Socket connection;
	StringBuffer instr = new StringBuffer();

	public Client(String user, long[] key) {
		this.user = user;
		this.key = key;
	}

	public void connectSocket() throws IOException {
		System.out.println("Client initialized");
		// Address object of server
		InetAddress address = InetAddress.getByName(host);
		connection = new Socket(address, port);
	}
	
	public void closeSocket() throws IOException {
		connection.close();		
	}

	public void writeToSocket(String instr) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
		OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
		//TODO: Is it efficient to initialize these every time we want to write something?
		osw.write(instr);
		osw.flush();
	}
	
	public void readFromSocket() throws IOException {
		BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
		InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
		//TODO: Is it efficient to initialize these every time we read?
		// Read socket's inputstream and append to stringbuff
		int c;
		//TODO: What is 13?
		while ( (c=isr.read()) != 13) {
			instr.append((char)c);
		}
		System.out.println(instr);
		
	}



}
