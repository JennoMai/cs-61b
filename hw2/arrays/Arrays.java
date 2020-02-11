package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Jenny Mei
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int[] result = new int[A.length + B.length];
        for (int a = 0; a < A.length; a += 1) {
            result[a] = A[a];
        }
        for (int b = 0; b < B.length; b += 1) {
            result[A.length + b] = B[b];
        }
        return result;
    }

    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        int[] result = new int[A.length - len];
        int current = 0;
        while (start != 0) {
            result[current] = A[current];
            current += 1;
            start -= 1;
        }
        int stop = current;
        while (len != 0) {
            current += 1;
            len -= 1;
        }
        while (current < A.length) {
            result[stop] = A[current];
            current += 1;
            stop += 1;
        }
        return result;
    }

    /* C3. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        if (A.length == 0) {
            return new int[][] {};
        }

        int increases = 1;
        for (int i = 0; i < A.length - 1; i += 1) {
            if (A[i] >= A[i + 1]) {
                increases += 1;
            }
        }

        int[][] result = new int[increases][];
        int[] entry = {};
        int current = 0;
        for (int i = 0; i <= A.length; i += 1) {
            if (i == 0) {
                entry = new int[] {A[0]};
            } else if (i == A.length) {
                result[current] = entry;
            } else if (A[i] <= A[i - 1]) {
                result[current] = entry;
                current += 1;
                entry = new int[] {A[i]};
            } else {
                entry = catenate(entry, new int[] {A[i]});
            }
        }

        return result;
    }
}
