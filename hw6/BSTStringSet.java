import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 * @author Jenny Mei
 */
public class BSTStringSet implements StringSet, Iterable<String>, SortedStringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        _root = null;
    }

    @Override
    public void put(String s) {
        Node n = new Node(s);
        if (_root == null) {
            _root = n;
        } else {
            _root.addNode(n);
        }
    }

    @Override
    public boolean contains(String s) {
        if (_root == null && s == null) {
            return true;
        } else if (_root == null) {
            return false;
        }
        return _root.findNode(s); // FIXME: PART A
    }

    @Override
    public List<String> asList() {
        if (_root == null) {
            return new ArrayList<>();
        } else {
            BSTIterator iter = new BSTIterator(_root);
            ArrayList<String> strings = new ArrayList<>();
            while (iter.hasNext()) {
                strings.add(iter.next());
            }
            return strings;
        }
    }

    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }

        private void addNode(Node n) {
            if (n.s.compareTo(s) < 0) {
                if (left == null) {
                    left = n;
                } else {
                    left.addNode(n);
                }
            } else if (n.s.compareTo(s) > 0) {
                if (right == null) {
                    right = n;
                } else {
                    right.addNode(n);
                }
            } else {
                s = n.s;
            }
        }

        private boolean findNode(String s) {
            if (this.s.compareTo(s) == 0) {
                return true;
            } else if (s.compareTo(this.s) < 0) {
                if (left == null) {
                    return false;
                } else {
                    return left.findNode(s);
                }
            } else {
                if (right == null) {
                    return false;
                } else {
                    return right.findNode(s);
                }
            }
        }
    }

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    private static class BSTRangeIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();
        private String min;
        private String max;

        /** A new iterator over the labels in NODE. */
        BSTRangeIterator(Node node, String l, String h) {
            min = l;
            max = h;
            addTree(node);
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                if (node.s.compareTo(min) >= 0 && node.s.compareTo(max) <= 0) {
                    _toDo.push(node);
                } else {
                    addTree(node.right);
                }
                node = node.left;
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }

    // FIXME: UNCOMMENT THE NEXT LINE FOR PART B
    @Override
    public Iterator<String> iterator(String low, String high) {
        return new BSTRangeIterator(_root, low, high);  // FIXME: PART B
    }


    /** Root node of the tree. */
    private Node _root;
}
