package lib;


public class NativeInsertionSort {

	public native static int sort(int[] array);
	
	static {
		System.loadLibrary("insertionsort");
	}
}
