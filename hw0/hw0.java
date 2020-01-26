public class hw0 {
/** Runs tests for each function. */
  public static void main(String[] args) {
//max tests
    System.out.println(max(new int[]{1, 2, 3, 4}) + " = 4");
    System.out.println(max(new int[]{5, 0, 3, -4}) + " = 5");
    System.out.println(max(new int[]{0, 0, 0, 0}) + " = 0");
//threeSum tests
    System.out.println(threeSum(new int[]{-6, 3, 10, 200}) + " = true");
    System.out.println(threeSum(new int[]{5, 1, 0, 3, 6}) + " = true");
    System.out.println(threeSum(new int[]{-6, 2, 5}) + " = false");
//threeSumDistinct tests
    System.out.println(threeSumDistinct(new int[]{-6, 3, 10, 200}) + " = false");
    System.out.println(threeSumDistinct(new int[]{5, 1, 0, 3, 6}) + " = false");
    System.out.println(threeSumDistinct(new int[]{8, 2, -1, -1, 15}) + " = true");
  }

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

  public static boolean threeSum(int[] a) {
    for (int first : a) {
      for (int second : a) {
          for (int third : a) {
            if (first + second + third == 0) {return true;}
          }
      }
    }
    return false;
  }

  public static boolean threeSumDistinct(int[] a) {
    for (int first = 0; first < a.length; first++) {
      for (int second = 0; second < a.length; second++) {
        for (int third = 0; third < a.length; third++) {
          if (a[first] + a[second] + a[third] == 0 && first < second && second < third) {
            return true;
          }
        }
      }
    }
  return false;
  }
}
