package lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


public class ClientHandler {

	private static Client client;
	private static String  user;

/*		For testing purposes: run clientHandler with these command line arguments
		keys = 1827361872 ,194291, 91487, 1784619}; 	user = "Client1";
		keys = 129048L, 1249081L, 89L, 9035717L, 	user = "Client2";
		keys = 5139841L, 1839741L, 80808808L, 279381L, 	user = "Client3";
		keys = 8018048L, 192837L, 938401983L, 2342L, 	user=  "Client4";
		keys = 1827L,11111L, 90923201L, 178461119L}, 	user = "Client5";
		keys = 361872L, 1421L, 9999147L, 22222222L, 	user = "Client6";
*/



	public static void main(String[] args) {
		//	initializeClientID();
		//		 parse command line args
		if (args.length != 5) {
			System.out.println("USAGE: <username> <key (4 longs)>");
		} else {
			user = args[0];
			long[] key = new long[4];
			for (int i=1; i<args.length; i++) {
				key[i-1] = Long.parseLong(args[i]);
			}
			//		logging into server
			client = new Client(user, key);
			try {
				if (client.login()) {
					client.requestFileNames();
				}
			} catch (IOException e) {
				System.err.println("Client: Login Error--"+e);
			} catch (Exception e) {
				System.err.println("Client: Error--"+e);
			} finally {
				
				try { client.closeSocket(); } catch (IOException e) {
					System.out.println("Error in closing client connection");
				}
			}
		}
	}
}
