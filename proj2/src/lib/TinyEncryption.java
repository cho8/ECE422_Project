package lib;

import java.util.Arrays;

public class TinyEncryption {
	public native static void encrypt(byte[] v, long[] k);
	public native static void decrypt(byte[] v, long[] k);
	
	static {
		System.loadLibrary("tinyencryption");
	}
	
	
	public static byte[] encryptData(byte[] data, long[] key) {
		byte[] padded = padData(data);
		encrypt(padded, key);
		return padded;
	}
	
	public static byte[] decryptData(byte[] data, long[] key) {
		byte[] copy = Arrays.copyOf(data,  data.length);
		decrypt(copy, key);
		return copy;
	}
	
	private static byte[] padData(byte[] data) {
		return Arrays.copyOf(data,  (int) Math.ceil((double) data.length / (Long.BYTES * 2)) * (Long.BYTES * 2));
	}

}
