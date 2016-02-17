package test;

import lib.NativeInsertionSort;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class NativeInsertionSortTest {

	int[] test;
	NativeInsertionSort nis;

	@Before
	public void setUp() throws Exception {
		test = new int[]{4, 1, 6, 2, 3, 5};
	}

	@Test
	public void testSort() throws Exception {

		assertTrue(NativeInsertionSort.sort(test) > 0);
		assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, test);
	}
}