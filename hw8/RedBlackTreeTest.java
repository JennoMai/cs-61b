import org.junit.*;
import static org.junit.Assert.*;

public class RedBlackTreeTest {

    @Test
    public void testRotate() {
        RedBlackTree<Integer> t = new RedBlackTree();
        t.insert(3);
        assertEquals(3, (int) t.graderRoot().item);

        t.insert(2);
        t.insert(1);

        RedBlackTree.RBTreeNode node = t.graderRoot();
        System.out.println(node.item);
        System.out.println(node.left.item);
        System.out.println(node.right.item);
    }
}
