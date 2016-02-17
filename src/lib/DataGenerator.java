package lib;
import java.io.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class DataGenerator {
	private static Random rand = new Random();
	private static int MAX = 100;
	
	public static int[] generateInts(int num) {
		int[] arry = new int[num];
		for (int i =0; i< num; i++) {
			arry[i] = rand.nextInt(MAX);
			System.out.println(arry[i]);
		}
		return arry;
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Invalid number of arguments.");
			System.exit(1);
		}
		
		// Get file
		File f = null;
		try {
			f = new File(args[0]);
			f.getCanonicalPath();
			f.delete();
			
		} catch(IOException e) {
			System.err.println(args[0] +" is not a valid file.");
			System.exit(1);
		}
		System.out.println("Opening file");
		
		// Get number if ints
		int numInts = 0;
		try {
			numInts = Integer.parseInt(args[1]);
			
		} catch (NumberFormatException e) {
			System.err.println(args[1] + " Not an integer.");
			System.exit(1);
		}
		System.out.println("numints " + numInts);
		
		// Write file with array of ints
		BufferedWriter fw = null;
		int[] arry = generateInts(numInts);
		try {		
			fw = new BufferedWriter(new FileWriter(args[0]));
			for (int i=0; i<numInts;i++) {
				fw.write(arry[i]+" ");
			}
		} catch (IOException e) {
			System.err.println("Error writing to file: " +e);
		} finally {
			fw.close();
		}
	}
	

}
