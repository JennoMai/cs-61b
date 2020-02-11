package lists;

/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */

/** List problem.
 *  @author Jenny Mei
 */
class Lists {

    /* B. */
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascending sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list
     *            ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntListList naturalRuns(IntList L) {
        IntListList result = new IntListList(L, null);
        IntListList adder = result;

        while (L != null) {
            int currentNum = L.head;
            if (L.tail != null && currentNum >= L.tail.head) {
                IntList temp = L;
                L = L.tail;
                temp.tail = null;

                adder.tail = new IntListList(L, null);
                adder = adder.tail;

            } else {
                L = L.tail;
            }
        }
        return result;
    }
}
