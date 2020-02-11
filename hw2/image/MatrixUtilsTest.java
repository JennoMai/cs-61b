package image;

import arrays.Utils;
import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for MatrixUtils.
 *  @author Jenny Mei
 */

public class MatrixUtilsTest {
    @Test
    public void accumulateVerticalTest() {
        double[][] m1 = {{1000000, 1000000, 1000000, 1000000},
                         {1000000, 75990, 30003, 1000000},
                         {1000000, 30002, 103046, 1000000},
                         {1000000, 29515, 38273, 1000000},
                         {1000000, 73403, 35399, 1000000},
                         {1000000, 1000000, 1000000, 1000000}};

        double[][] am1 = {{1000000, 1000000, 1000000, 1000000},
                          {2000000, 1075990, 1030003, 2000000},
                          {2075990, 1060005, 1133049, 2030003},
                          {2060005, 1089520, 1098278, 2133049},
                          {2089520, 1162923, 1124919, 2098278},
                          {2162923, 2124919, 2124919, 2124919}};

        for (int row = 0; row < m1.length; row += 1) {
            assertArrayEquals(MatrixUtils.accumulateVertical(m1)[row], am1[row], 0.1);
        }
    }

    @Test
    public void accumulateTest() {
        double[][] m1 = {{1000000, 1000000, 1000000, 1000000},
                         {1000000, 75990, 30003, 1000000},
                         {1000000, 30002, 103046, 1000000},
                         {1000000, 29515, 38273, 1000000},
                         {1000000, 73403, 35399, 1000000},
                         {1000000, 1000000, 1000000, 1000000}};

        double[][] am1 = {{1000000, 2000000, 2075990, 2060005},
                          {1000000, 1075990, 1060005, 2060005},
                          {1000000, 1030002, 1132561, 2060005},
                          {1000000, 1029515, 1067788, 2064914},
                          {1000000, 1073403, 1064914, 2064914},
                          {1000000, 2000000, 2073403, 2064914}};

        for (int row = 0; row < m1.length; row += 1) {
            assertArrayEquals(MatrixUtils.accumulate(m1, MatrixUtils.Orientation.HORIZONTAL)[row], am1[row], 0.1);
        }
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}
