package test;



import static org.junit.Assert.*;
import lib.HeapSort;

/**
 * JUnit test class for sorting coffees
 */

public class HeapSortTest {

	int[] test;

	@org.junit.Before
	public void setUp() throws Exception {
		test = new int[]{7,3,2,6,3,1};
	}

	@org.junit.Test
	public void testSort() throws Exception {
		assertTrue(HeapSort.sort(test) > 0);
		assertArrayEquals(new int[]{1,2,3,3,6,7}, test);
	}
}