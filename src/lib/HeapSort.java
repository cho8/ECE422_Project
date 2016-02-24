package lib;





public class HeapSort {
	private static int[] arry;
	private static int len;
	private static int largest;

	private static int buildHeap(int[] arry){
		len = arry.length-1;
		int mem=1;
		for (int i =len/2; i>= 0; i--){
			mem +=1;
			mem += maxHeap(arry, i);
		}
		return mem;
	}

	private static int maxHeap(int[] arry, int parent){
		int l_child = 2*parent;
		int r_child = 2*parent+1;
		int mem = 4;
		
		if (l_child <= len && arry[l_child] > arry[parent]) {
			largest = l_child;
		} else {
			largest = parent;	
		}
		mem+=7;
		if (r_child <= len && arry[r_child] > arry[largest]) {
			largest = r_child;
			mem+=7;
		}
		if (largest != parent) {
			mem += swap(parent, largest);
			mem += maxHeap(arry, largest); // fix the maxHeap branch at largest
		}
		return mem;
	}

	private static int swap(int a, int b){
		int t = arry[a];
		arry[a] = arry[b];
		arry[b] = t;
		return 10;
	}
	
	public static int sort(int[] input) {
		int mem = 0;
		arry = input;
		mem+=1;
		mem += buildHeap(arry);
		for (int i =len; i>0; i--) {
			mem+=1;
			mem += swap(0,i);
			len = len-1;
			mem+=1;
			mem += maxHeap(arry, 0);
		}
		return mem;
	}

}
