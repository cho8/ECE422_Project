package lib;


public class DataSorter {
	int timelimit;
	double errProbHeap;
	double errProbInsert;
	
	public DataSorter(int time, double heapErr, double insertErr) {
		timelimit = time;
		errProbHeap = heapErr;
		errProbInsert = insertErr;
	}
	
	private boolean SortAdjudicator(int[] arry) {
		for(int i=0; i<arry.length; i++) {
			if (arry[i] > arry[i+1]) return false;
		}
		return true;
	}
	
	private boolean executeHeapSort(int[] arry) {
		boolean success;
		int[] cpy = new int[arry.length];
		System.arraycopy(arry, 0, cpy, 0, arry.length);
		
		
		
		return true;
		
	}
}


