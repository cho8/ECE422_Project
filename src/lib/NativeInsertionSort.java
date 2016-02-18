package lib;

public class NativeInsertionSort {

	public native static int sort(int[] array);
	
	static {
		System.loadLibrary("insertionsort");
	}
	
//	public static void main(String[] args) {
//		int[] test = new int[]{4, 1, 6, 2, 3, 5};
//		
//		int mem = NativeInsertionSort.sort(test);
//		System.out.println(mem);
//		
//	}
}
