/** A collection of bit twiddling exercises.
 *  @author Jenny Mei
 */

public class BitExercise {
    
    /** Fill in the function below so that it returns 
    * the value of the argument x with all but its last 
    * (least significant) 1-bit set to 0.
    * For example, 100 in binary is 0b1100100, so lastBit(100)
    * should return 4, which in binary is 0b100.
    */
    public static int lastBit(int x) {
        System.out.println("Input is: " + Integer.toBinaryString(x));
        int complement = ~x + 0b1;
        System.out.println("Complement is: " + Integer.toBinaryString(complement));
        int result = complement & x;
        System.out.println("Result is: " + Integer.toBinaryString(result));
        System.out.println("----------------------------------------------");
        return result;
        }

    /** Fill in the function below so that it returns 
    * True iff x is a power of two, otherwise False.
    * For example: 2, 32, and 8192 are powers of two.
    */
    public static boolean powerOfTwo(int x) {
        return x > 0 && lastBit(x) == x; //TODO: Your code here
    }
    
    /** Fill in the function below so that it returns 
    * the absolute value of x WITHOUT USING ANY IF 
    * STATEMENTS OR CALLS TO MATH.
    * For example, absolute(1) should return 1 and 
    * absolute(-1) should return 1.
    */
    public static int absolute(int x) { return 0;
    } 
}