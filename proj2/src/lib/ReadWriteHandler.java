package lib;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ReadWriteHandler {

	private Socket connection;
	private long[] key = null;

	private DataOutputStream out;
	private DataInputStream in;

	public ReadWriteHandler(Socket connection) {
		this.connection = connection;
		try {
			out = new DataOutputStream(connection.getOutputStream());
			in = new DataInputStream(connection.getInputStream());
		} catch (IOException e) {
			System.err.println("ReadWriteHandler init err: "+e);
		}
	}

	public void setKey(long[] key) {
		this.key = key;
	}

	public long[] getKey() {
		return this.key;
	}


	public byte[] read() throws IOException {
		int len = in.readInt();
		if (len>0){
			byte[] message = new byte[len];
			in.readFully(message,0,message.length);
			return message;
		}
		return new byte[0];
	}

	public void write(byte[] message) throws IOException {
		out.writeInt(message.length);
		out.write(message);
	}


	public byte[] encrypt(byte[] message) {
		//encrypt helper
		return TinyEncryption.encryptData(message, key);
	}
	public byte[] decrypt(byte[] message) {
		//decrypt helper
		return TinyEncryption.decryptData(message, key);
	}

}
