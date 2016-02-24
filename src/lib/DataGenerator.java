package lib;
import java.io.*;
import java.util.Arrays;
import java.util.Random;


public class DataGenerator {
	private static Random rand = new Random();
	private static int MAX = 500;
	private static String USAGE = "java DataGenerator <output file> <number of integers>";
	
	private static int[] generateInts(int num) {
		int[] arry = new int[num];
		for (int i =0; i< num; i++) {
			arry[i] = rand.nextInt(MAX);
		}
		return arry;
	}
	
	private static void closeSilently(Closeable c) {
		try {
			c.close();
		} catch (IOException e) {
			System.err.println("Something went wrong with closing "+ c);
		}
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Invalid number of arguments.");
			System.out.println(USAGE);
			return;
		}
		
		// Get file
		File f = null;
		try {
			f = new File(args[0]);
			f.getCanonicalPath();
			f.delete();
			
		} catch(IOException e) {
			System.err.println(args[0] +" is not a valid file.");
			return;
		}
		
		// Get number if ints
		int numInts = 0;
		try {
			numInts = Integer.parseInt(args[1]);
			
		} catch (NumberFormatException e) {
			System.err.println(args[1] + " Not an integer.");
			return;
		}
		
		// Write file with array of ints
		BufferedWriter fw = null;
		int[] arry = generateInts(numInts);
		try {		
			fw = new BufferedWriter(new FileWriter(args[0]));
			for (int i=0; i<numInts;i++) {
				fw.write(arry[i]+" ");
			}
			fw.write("\n");
			System.out.println("Integers generated to " + args[0]);
		} catch (IOException e) {
			System.err.println("Error writing to file: " +e);
		} finally {
			closeSilently(fw);
		}
	}
	

}
