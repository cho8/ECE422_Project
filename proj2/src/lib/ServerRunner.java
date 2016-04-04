package lib;

import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

public class ServerRunner {

	private HashMap<long[], String> users;
	private int port = 16000;

	public ServerRunner(HashMap<long[], String> users) {
		this.users = users;
	}

	public void runServer() {
		int count = 0;	
		try {
			ServerSocket socket1 = new ServerSocket(port);
			while (true) {
				Runnable runnable = new Server(socket1.accept(), ++count);
//				runnable.loadUsers(users);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		} catch (Exception e) {}
	}
	
}
