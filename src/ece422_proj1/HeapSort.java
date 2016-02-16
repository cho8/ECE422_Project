package ece422_proj1;


public class HeapSort {
	private static int[] arry;
	private static int len;
	private static int largest;

	public static int buildHeap(int[] arry){
		len = arry.length-1;
		int mem=1;
		for (int i =len/2; i>= 0; i--){
			mem +=1;
			mem += maxHeap(arry, i);
		}
		return mem;
	}

	public static int maxHeap(int[] arry, int parent){
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

	public static int swap(int a, int b){
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

	public static void main(String[] args) {
		int[] test_arry = {1,5,2,6,3,7};
		int mem = sort(test_arry);
		for (int i=0; i<test_arry.length; i++){
			System.out.print(test_arry[i] + " ");
		}
		System.out.println("\n" + mem);
	}
}
