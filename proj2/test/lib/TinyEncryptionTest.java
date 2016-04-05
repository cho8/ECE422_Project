
package lib;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.*;
import lib.TinyEncryption;

/**
 * Make sure to add the .dylib or .so library to eclipse native library path!
 * 
 */

public class TinyEncryptionTest {

	String test;
	long[] key;

	@org.junit.Before
	public void setUp() throws Exception {
		test = "Hello I'm a test string";
		key = new long[] {412153612L, 18723681723L, 19823791823L, 18236918L}; 
	}

	@org.junit.Test
	public void testEncrypt() throws Exception {
		byte[] encrBytes= TinyEncryption.encryptData(test.getBytes(), key);
		String encrTest = new String(encrBytes, "UTF-8");
		System.out.println(encrTest);
		assertFalse(encrTest.equals(test));
	}
	
	@org.junit.Test
	public void testDecrypt() throws UnsupportedEncodingException {
		String encryptMe = "Hello I'm a test string";
		byte[] encrBytes= TinyEncryption.encryptData(encryptMe.getBytes(), key);
		byte[] decrBytes = TinyEncryption.decryptData(encrBytes, key);
		String decrStr = new String(decrBytes, "UTF-8");
		System.out.println(decrStr);
		assert(test.equals(decrStr));
	}
}