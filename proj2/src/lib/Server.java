package lib;

import java.util.HashMap;
import java.util.Set;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server {
	public static String ACK = "ack";
	public static String FILE_NOT_FOUND = "file_not_found";
	public static int portNumber 16000;
	
	private ServerSocket serverSocket;
	
	private HashMap<long[], String> users;
	
	public Server(HashMap<long[], String> users) {
		this.userHash = users;
		serverSocket = new ServerSocket()
	}
	
	public getKeys() {
		return userHash.keySet();
	}
	
	public String getUser(long[] key) {
		return userHash.get(key);
	}
	
}
