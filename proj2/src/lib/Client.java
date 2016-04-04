package lib;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Client {
	private long[] key;
	private String user;
	private String host = "localhost";
	public static int portNumber 16000;
	
	Socket connection;
	
	public Client(String user, long[] key) {
		this.user = user;
		this.key = key;
	}
	
	public void connectSocket() {
		try {
			// Address object of server
			InetAddress address = InetAddress.getByName(host);
			connection = new Socket(address, port);
		}
	}
	
	public void writeToSocket() {
		BufferedOutputStream bos = new BufferedOutputStream(connection.)
	}
	
	
	
}
