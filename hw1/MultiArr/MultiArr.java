/** Multidimensional array 
 *  @author Zoe Plaxco
 */

public class MultiArr {

    /**
    {{“hello”,"you",”world”} ,{“how”,”are”,”you”}} prints:
    Rows: 2
    Columns: 3
    
    {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
    Rows: 4
    Columns: 4
    */
    public static void printRowAndCol(int[][] arr) {
        int rows = arr.length;
        int cols = 0;

        for (int[] row : arr) {
            if (row.length > cols) {
                cols = row.length;
            }
        }

        System.out.println("Rows: " + rows);
        System.out.println("Columns: " + cols);
    } 

    /**
    @param arr: 2d array
    @return maximal value present anywhere in the 2d array
    */
    public static int maxValue(int[][] arr) {
        int max = 0;
        for (int[] row : arr) {
            for (int value : row) {
                if (value > max) {
                    max = value;
                }
            }
        }
        return max;
    }

    /**Return an array where each element is the sum of the 
    corresponding row of the 2d array*/
    public static int[] allRowSums(int[][] arr) {
        int[] sum = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int n : arr[i]) {
                sum[i] += n;
            }
        }
        return sum;
    }
}