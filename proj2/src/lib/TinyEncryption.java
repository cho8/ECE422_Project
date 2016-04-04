package lib;

import java.util.Arrays;

public class TinyEncryption {
	public native static void encrypt(byte[] v, long[] k);
	public native static void decrypt(byte[] v, long[] k);
	
	static {
		System.loadLibrary("tinyencrypt");
	}
	
	private long[] key;
	
	public TinyEncryption(long[] key) {
		assert key.length == 4;
		this.key = key;
	}
	
	public byte[] encrypt(byte[] data) {
		byte[] padded = padData(data);
		encrypt(padded, key);
		return padded;
	}
	
	public byte[] decrypt(byte[] data) {
		byte[] copy = Arrays.copyOf(data,  data.length);
		decrypt(copy, key);
		return copy;
	}
	
	private byte[] padData(byte[] data) {
		return Arrays.copyOf(data,  (int) Math.ceil((double) data.length / (Long.BYTES * 2)) * (Long.BYTES * 2));
	}

}
