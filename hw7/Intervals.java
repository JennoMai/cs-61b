import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/** HW #7, Sorting ranges.
 *  @author Jenny Mei
  */
public class Intervals {
    /** Assuming that INTERVALS contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns the
     *  total length covered by the union of the intervals. */
    public static int coveredLength(List<int[]> intervals) {
        // REPLACE WITH APPROPRIATE STATEMENTS.
        int k = intervals.size();
        int[][] sorted = new int[k][];

        int divisor = 1;
        int max = 0;
        for (int i = 0; i < k; i += 1) {
            sorted[i] = intervals.get(i);
            if (intervals.get(i)[0] > max) {
                max = intervals.get(i)[0];
            }
        }
        while (Math.floorDiv(max, divisor) > 0) {
            int[] count = new int[10];
            Arrays.fill(count, 0);
            for (int i = 0; i < k; i += 1) {
                count[Math.floorDiv(sorted[i][0], divisor) % 10] += 1;
            }
            for (int i = 1; i < 10; i += 1) {
                count[i] += count[i - 1];
            }
            int[][] result = new int[k][];
            for (int i = k - 1; i >= 0; i -= 1) {
                int digit = Math.floorDiv(sorted[i][0], divisor) % 10;
                int endIndex = count[digit];
                result[endIndex - 1] = sorted[i];
                count[digit] -= 1;
            }

            for (int i = 0; i < k; i += 1) {
                sorted[i] = result[i];
            }

            divisor *= 10;
        }

        int length = sorted[0][1] - sorted[0][0];
        int largest = sorted[0][1];
        for (int[] interval : sorted) {
            if (interval[0] > largest) {
                largest = interval[1];
                length += largest - interval[0];
            } else if (interval[1] > largest) {
                length += interval[1] - largest;
                largest = interval[1];
            }
        }

        return length;
    }

    /** Test intervals. */
    static final int[][] INTERVALS = {
        {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
    };
    /** Covered length of INTERVALS. */
    static final int CORRECT = 23;

    /** Performs a basic functionality test on the coveredLength method. */
    @Test
    public void basicTest() {
        assertEquals(CORRECT, coveredLength(Arrays.asList(INTERVALS)));
    }

    /** Runs provided JUnit test. ARGS is ignored. */
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Intervals.class));
    }

}
