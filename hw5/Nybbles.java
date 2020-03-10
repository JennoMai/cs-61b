/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author
 */
public class Nybbles {

    /** Maximum positive value of a Nybble. */
    public static final int MAX_VALUE = 7;

    /** Return an array of size N. 
    * DON'T CHANGE THIS.*/
    public Nybbles(int N) {
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else {
            int num = k / 8;
            int nybble = k % 8;
            System.out.println(Integer.toBinaryString(_data[num]));
            int left = _data[num] << (4 * (7 - nybble));
            System.out.println(Integer.toBinaryString(left));
            int right = left >> 28;
            System.out.println(Integer.toBinaryString(right));
            return right;
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < (-MAX_VALUE - 1) || val > MAX_VALUE) {
            throw new IllegalArgumentException();
        } else {
            int num = k / 8;
            int nybble = k % 8;
            if (val >= 0) {
                _data[num] = _data[num] + val * (int) Math.pow(16, nybble); // REPLACE WITH SOLUTION
            } else {
                int newnum = (val + 1) * (int) Math.pow(16, nybble) - 1;
                _data[num] = _data[num] | 15 * (int) Math.pow(16, nybble);
                _data[num] = _data[num] & newnum;
            }
        }
    }

    /** DON'T CHANGE OR ADD TO THESE.*/
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}
