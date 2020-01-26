public class hw0 {
/** The max of an array of integers. */
  public static int max(int[] a) {
    int max = a[0];
    for (int number : a) {
      if (number > max) {
        max = number;
      }
    }
    return max;
  }
}
