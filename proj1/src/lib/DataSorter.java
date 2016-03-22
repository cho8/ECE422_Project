package lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;


public class DataSorter {

	private static double heapErrProb;
	private static double insertErrProb;
	private static int timelimit;

	private static Random rand = new Random();
	private static String USAGE = "java DataSorter <input file> <output file> <heapsort error probability> <insertionsort error probability> <time limit in ms>";

	public DataSorter(double heapErr, double insErr, int time) {
		timelimit = time;
		heapErrProb = heapErr;
		insertErrProb = insErr;
	}

	public class SortFailureException extends RuntimeException {
		public SortFailureException(String message) { 
			super(message); 
		}
	}

	private static boolean adjudicateSort(int[] arry) {
		for(int i=0; i<arry.length-1; i++) {
			if (arry[i] > arry[i+1]) return false;
		}
		return true;
	}

	private static class SortThread extends Thread {
		int mem = 0;
		public void run(){
			try{
				for (;;);
			} catch (ThreadDeath td){
				System.out.println("I'm dead!!!");
				throw new ThreadDeath();
			}
		}
		public int getMem() {
			return mem;
		}		
	}

	private static boolean executeHeapSort(int[] arr) {
    		final int[] arry = arr;
		SortThread heapThread = new SortThread(){
			public void run(){
				mem = HeapSort.sort(arry);
			}    
		};
		Timer t = new Timer();
		WatchDog w = new WatchDog(heapThread);
		t.schedule(w, timelimit);
		heapThread.start();
		try {
			heapThread.join();
      t.cancel();
			int mem = heapThread.getMem();

			double HAZARD = heapErrProb * mem;
			double rn = rand.nextDouble();
			
			if ( 0.5 <= rn && rn <= (0.5+HAZARD)) {
				System.err.println("Heap sort failed due to memory operation failure.");
				t.cancel();
				return false;
			}

			return adjudicateSort(arry);
		} catch (InterruptedException e){
			System.err.println("Interrupted heapsort: "+ e);
		} catch (RuntimeException e)  {
			System.err.println("Something weird in executeHeapSort: "+ e)
    } catch (ThreadDeath e) {
      System.err.println("DeadThread");
    }
    return false;
	}

	private static boolean executeInsertionSort(int[] arr) {
    		final int[] arry = arr;
		SortThread insertionThread = new SortThread(){
			public void run(){
				mem = NativeInsertionSort.sort(arry);
			}    
		};
		Timer t = new Timer();
		WatchDog w = new WatchDog(insertionThread);
		t.schedule(w, timelimit);
		insertionThread.start();
		try {
			insertionThread.join();
			t.cancel();
			int mem = insertionThread.getMem();

			double HAZARD = heapErrProb * mem;
			double rn = rand.nextDouble();
			if ( 0.5 <= rn && rn <= (0.5+HAZARD)) {
				System.err.println("Insertion sort failed due to memory operation failure.");
				t.cancel();
				return false;
			}

			return adjudicateSort(arry);
		} catch (InterruptedException e){
			System.err.println("Interrupted insertionsort: "+ e);
		} catch (RuntimeException e)  {
			System.err.println("Something weird in executeInsertionSort: "+ e);
    } catch (ThreadDeath e) {
      System.err.println("DeadThread");
      return false;
	}

	private int[] executeSort(int[] arry)  {
		int[] hpcpy = new int[arry.length];
		System.arraycopy(arry, 0, hpcpy, 0, arry.length);
		int[] inscpy = new int[arry.length];
		System.arraycopy(arry, 0, inscpy, 0, arry.length);
		if(executeHeapSort(hpcpy)) {
			return hpcpy;
		}
		System.err.println("Primary variant failed. Trying backup variant.");
		if (executeInsertionSort(inscpy)){
			return inscpy;
		} 
		throw new SortFailureException("All variants failed.");
	}

	private static void closeSilently(Closeable c) {
		try {
			c.close();
		} catch (IOException e) {
			System.err.println("Something went wrong with closing "+ c);
		}
	}
	public static void main(String[] args) {
		// <input> <output> <errHeap> <errIns> <timelimit>
		if (args.length != 5) {
			System.out.println("Wrong number of args.");
			System.out.println(USAGE);
			System.exit(0);
		}

		// input file
		try {
			File inf = new File(args[0]);
			inf.getCanonicalPath();
		} catch (IOException e) {
			System.err.println("Invalid filename " + args[0] + " :" + e);
			System.exit(0);
		}

		// read input
		BufferedReader fr = null;
		int[] arry = null;
		try {		
			fr = new BufferedReader(new FileReader(args[0]));
			String[] s = fr.readLine().split(" ");
			arry = new int[s.length];
			for (int i=0; i<s.length; i++) {
				arry[i] = Integer.parseInt(s[i]);
			}
		} catch (IOException e) {
			System.err.println("Error in input file: " + e);
			System.exit(0);
		} finally {
			closeSilently(fr);
		}

		double heapErrProb= 0.0,insertErrProb = 0.0;
		// parse error doubles
		try {
			heapErrProb = Double.parseDouble(args[2]);
		} catch (NumberFormatException e) {
			System.err.println(args[2] +" is an invalid double.");
			System.exit(0);
		}
		try {
			insertErrProb = Double.parseDouble(args[3]);
		} catch (NumberFormatException e) {
			System.err.println(args[3] +" is an invalid double.");
			System.exit(0);
		}

		// parse timelimit
		int timelimit=0;
		try {
			timelimit = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			System.err.println(args[4] +" is an invalid integer.");
			System.exit(0);
		}
		// output file
		try {
			File f = new File(args[1]);
			f.getCanonicalPath();
			f.delete();
		} catch (IOException e) {
			System.err.println(args[1] +" is an invalid file.");
			System.exit(0);
		}
		// sorting
		BufferedWriter fw = null;
		DataSorter ds = new DataSorter(heapErrProb, insertErrProb, timelimit);
		try {		
			fw = new BufferedWriter(new FileWriter(args[1]));
			arry = ds.executeSort(arry);
			for (int i=0; i< arry.length;i++) {
				fw.write(arry[i]+" ");
			}
			fw.write("\n");
		} catch (IOException e) {
			System.err.println("Error writing to file: " +e);
		} catch (SortFailureException e) {
			System.err.println("Everything exploded: " +e);
		} finally {
			closeSilently(fw);
		}
		System.exit(0);
	}

}


