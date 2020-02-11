package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for the Arrays class.
 *  @author Jenny Mei
 */

public class ArraysTest {
    @Test
    public void catenateTest() {
        int[] l1 = {1, 2, 3, 4, 5};
        int[] l2 = {6, 7, 8, 9, 10};
        int[] l3 = {};
        int[] l4 = {1};

        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, Arrays.catenate(l1, l2));
        assertArrayEquals(new int[] {1}, Arrays.catenate(l3, l4));
        assertArrayEquals(new int[] {1, 6, 7, 8, 9, 10}, Arrays.catenate(l4, l2));
    }

    @Test
    public void removeTest() {
        int[] l1 = {1, 2, 3, 4, 5, 6};
        int[] l2 = {};
        int[] l3 = {6, 1};

        assertArrayEquals(new int[] {1, 2, 5, 6}, Arrays.remove(l1, 2, 2));
        assertArrayEquals(new int[] {}, Arrays.remove(l2, 0, 0));
        assertArrayEquals(new int[] {}, Arrays.remove(l3, 0, 2));
    }

    @Test
    public void naturalRunsTest() {
        int[] l1 = {3, 4, 6, 1, 5, 5, 2, 7, 8, 9};
        int[] l2 = {};
        int[] l3 = {5, 4, 3, 2, 1};

        int[][] ll1 = {{3, 4, 6}, {1, 5}, {5}, {2, 7, 8, 9}};
        int[][] ll2 = {{}};
        int[][] ll3 = {{5}, {4}, {3}, {2}, {1}};

        assertTrue(Utils.equals(ll1, Arrays.naturalRuns(l1)));
        assertTrue(Utils.equals(ll2, Arrays.naturalRuns(l2)));
        assertTrue(Utils.equals(ll3, Arrays.naturalRuns(l3)));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
