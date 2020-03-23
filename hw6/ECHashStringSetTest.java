import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author Jenny Mei
 */
public class ECHashStringSetTest  {
    // FIXME: Add your own tests for your ECHashStringSetTest

    @Test
    public void testECHash() {
        StringUtils.setSeed(12);
        ArrayList<String> allStrings = new ArrayList<>();

        ECHashStringSet set = new ECHashStringSet();
        for (int i = 0; i < 300; i += 1) {
            String newString = StringUtils.randomString(4);
            set.put(newString);
            allStrings.add(newString);
        }
        for (String s : allStrings) {
            assertTrue(set.contains(s));
        }
    }
}
