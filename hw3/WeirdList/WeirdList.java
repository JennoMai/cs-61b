import net.sf.saxon.functions.Empty;

/** A WeirdList holds a sequence of integers.
 * @author Jenny Mei
 */
public class WeirdList {
    /** The empty sequence of integers. */
    public static final WeirdList EMPTY =
        new EmptyList();

    /** A new WeirdList whose head is HEAD and tail is TAIL. */
    public WeirdList(int head, WeirdList tail) { 
        _head = head;
        _tail = tail;
    }

    /** Returns the number of elements in the sequence that
     *  starts with THIS. */
    public int length() {
        return 1 + this._tail.length();
    }

    /** Return a string containing my contents as a sequence of numerals
     *  each preceded by a blank.  Thus, if my list contains
     *  5, 4, and 2, this returns " 5 4 2". */
    @Override
    public String toString() {
        return " " + _head + _tail.toString();
    }

    /** Part 3b: Apply FUNC.apply to every element of THIS WeirdList in
     *  sequence, and return a WeirdList of the resulting values. */
    public WeirdList map(IntUnaryFunction func) {
        WeirdList newList = new WeirdList(func.apply(this._head), null);
        newList._tail = _tail.map(func);
        return newList;
    }

    private int _head;
    private WeirdList _tail;

    private static class EmptyList extends WeirdList{
        private int _head;
        private WeirdList _tail;

        public EmptyList() {
            super(0, null);
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public WeirdList map(IntUnaryFunction func) {
            return new EmptyList();
        }
    }
}