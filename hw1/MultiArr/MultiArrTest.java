import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        int[][] first = {{4, 2, 3}, {1, 2}, {9, 1, 1}};
        int[][] second = {{1, 0}, {0}};
        assertEquals(9, MultiArr.maxValue(first));
        assertEquals(1, MultiArr.maxValue(second));
    }

    @Test
    public void testAllRowSums() {
        int[][] first = {{4, 2, 3}, {1, 2}, {9, 1, 1}};
        int[][] second = {{1, 0}, {0}};
        int[] firstResult = {9, 3, 11};
        int[] secondResult = {1, 0};

        assertArrayEquals(firstResult, MultiArr.allRowSums(first));
        assertArrayEquals(secondResult, MultiArr.allRowSums(second));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
