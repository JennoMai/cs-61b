package lists;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/** Tests for the Lists class.
 *
 *  @author Jenny Mei
 */

public class ListsTest {
    @Test
    public void naturalRunsTest() {
        IntList l1 = IntList.list(1, 5, 3, 2, 6, 12, 11, 3, 6);
        IntList l2 = IntList.list(2, 3, 4, 5, 6, 7, 1);
        IntList l3 = new IntList();
        IntListList ll1 = IntListList.list(new int[][] {{1, 5}, {3}, {2, 6, 12}, {11}, {3, 6}});
        IntListList ll2 = IntListList.list(new int[][] {{2, 3, 4, 5, 6, 7}, {1}});
        IntListList ll3 = new IntListList(new IntList(0, null), null);

        assertTrue(ll1.equals(Lists.naturalRuns(l1)));
        assertTrue(ll2.equals(Lists.naturalRuns(l2)));
        assertTrue(ll3.equals(Lists.naturalRuns(l3)));
    }
    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
