import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author Jenny Mei
 */
public class BSTStringSetTest  {

    @Test
    public void testBST() {
        StringUtils.setSeed(2);
        ArrayList<String> allStrings = new ArrayList<>();

        BSTStringSet set = new BSTStringSet();
        for (int i = 0; i < 15; i += 1) {
            String newString = StringUtils.randomString(4);
            set.put(newString);
            allStrings.add(newString);
        }
        for (String s : allStrings) {
            assertTrue(set.contains(s));
        }

        List<String> stringList = set.asList();
        for (int i = 1; i < stringList.size(); i += 1) {
            String curr = stringList.get(i);
            String prev = stringList.get(i - 1);
            assertTrue(curr.compareTo(prev) > 0);
        }
    }
}
