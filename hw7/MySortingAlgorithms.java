import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Note that every sorting algorithm takes in an argument k. The sorting 
 * algorithm should sort the array from index 0 to k. This argument could
 * be useful for some of your sorts.
 *
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Counting Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = 0; i < k; i += 1) {
                int j;
                int x = array[i];
                for (j = i - 1; j >= 0; j -= 1) {
                    if (array[j] < x) {
                        break;
                    }
                    array[j + 1] = array[j];
                }
                array[j + 1] = x;
            }
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int least;
            int current = 0;
            while (current < k) {
                int replacedIndex = current;
                least = array[current];
                for (int i = current + 1; i < k; i += 1) {
                    if (array[i] < least) {
                        least = array[i];
                        replacedIndex = i;
                    }
                }
                if (replacedIndex != current) {
                    array[replacedIndex] = array[current];
                    array[current] = least;
                }
                current += 1;
            }
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
            if (k == 1) {
                return;
            }
            int i = 0, j = 0;
            int split = k/2;
            int[] left = Arrays.copyOfRange(array, 0, split);
            int[] right = Arrays.copyOfRange(array, split, k);
            sort(left, split);
            sort(right, k - split);
            int current = 0;
            while (i < left.length && j < right.length) {
                if (left[i] <= right[j]) {
                    array[current] = left[i];
                    i += 1;
                } else {
                    array[current] = right[j];
                    j += 1;
                }
                current += 1;
            }
            while (i < left.length) {
                array[current] = left[i];
                i += 1;
                current += 1;
            }
            while (j < right.length) {
                array[current] = right[j];
                j += 1;
                current += 1;
            }
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Counting Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class CountingSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME: to be implemented
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Counting Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation.
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            int divisor = 1;
            int max = 0;
            for (int i = 0; i < k; i += 1) {
                if (a[i] > max) {
                    max = a[i];
                }
            }
            while (Math.floorDiv(max, divisor) > 0) {
                int[] count = new int[10];
                Arrays.fill(count, 0);
                for (int i = 0; i < k; i += 1) {
                    count[Math.floorDiv(a[i], divisor) % 10] += 1;
                }
                for (int i = 1; i < 10; i += 1) {
                    count[i] += count[i - 1];
                }
                int[] result = new int[k];
                for (int i = k - 1; i >= 0; i -= 1) {
                    int digit = Math.floorDiv(a[i], divisor) % 10;
                    int endIndex = count[digit];
                    result[endIndex - 1] = a[i];
                    count[digit] -= 1;
                }

                for (int i = 0; i < k; i += 1) {
                    a[i] = result[i];
                }

                divisor *= 10;
            }
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
