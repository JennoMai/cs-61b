import java.util.ArrayList;

/** A Generic heap class. Unlike Java's priority queue, this heap doesn't just
 * store Comparable objects. Instead, it can store any type of object
 * (represented by type T) and an associated priority value.
 * @author Jenny Mei */
public class ArrayHeap<T> {

    /* DO NOT CHANGE THESE METHODS. */

    /** An ArrayList that stores the nodes in this binary heap. */
    private ArrayList<Node> contents;

    /** A constructor that initializes an empty ArrayHeap. */
    public ArrayHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /** Returns the number of elements in the priority queue. */
    public int size() {
        return contents.size() - 1;
    }

    /** Returns the node at index INDEX. */
    private Node getNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /** Sets the node at INDEX to N */
    private void setNode(int index, Node n) {
        // In the case that the ArrayList is not big enough
        // add null elements until it is the right size
        while (index + 1 > contents.size()) {
            contents.add(null);
        }
        contents.set(index, n);
    }

    /** Returns and removes the node located at INDEX. */
    private Node removeNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.remove(index);
        }
    }

    /** Swap the nodes at the two indices. */
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        this.contents.set(index1, node2);
        this.contents.set(index2, node1);
    }

    /** Prints out the heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /** Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getNode(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getNode(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getNode(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getNode(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /** A Node class that stores items and their associated priorities. */
    public class Node {
        private T _item;
        private double _priority;

        private Node(T item, double priority) {
            this._item = item;
            this._priority = priority;
        }

        public T item() {
            return this._item;
        }

        public double priority() {
            return this._priority;
        }

        public void setPriority(double priority) {
            this._priority = priority;
        }

        @Override
        public String toString() {
            return this._item.toString() + ", " + this._priority;
        }
    }

    /* FILL IN THE METHODS BELOW. */

    /** Returns the index of the left child of the node at i. */
    private int getLeftOf(int i) {
        return 2*i;
    }

    /** Returns the index of the right child of the node at i. */
    private int getRightOf(int i) {
        return 2*i + 1;
    }

    /** Returns the index of the node that is the parent of the
     *  node at i. */
    private int getParentOf(int i) {
        return (int) Math.floor(i/2);
    }

    /** Returns the index of the node with smaller priority. If one
     * node is null, then returns the index of the non-null node.
     * Precondition: at least one of the nodes is not null. */
    private int min(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        if (node1 == null) {
            return index2;
        } else if (node2 == null) {
            return index1;
        } else if (node1._priority < node2._priority) {
            return index1;
        } else {
            return index2;
        }
    }

    /** Returns the item with the smallest priority value, but does
     * not remove it from the heap. If multiple items have the minimum
     * priority value, returns any of them. Returns null if heap is
     * empty. */
    public T peek() {
        for (int i = 1; i < contents.size(); i += 1) {
            if (contents.get(i) != null) {
                return contents.get(i)._item;
            }
        }
        return null;
    }

    /** Bubbles up the node currently at the given index until no longer
     *  needed. */
    private void bubbleUp(int index) {
        Node current = getNode(index);
        int currentI = index;
        int parentI = getParentOf(currentI);
        Node parent = getNode(parentI);
        while (parent != null && current._priority < parent._priority) {
            swap(currentI, parentI);

            currentI = parentI;
            parentI = getParentOf(currentI);
            parent = getNode(parentI);
        }
    }

    /** Bubbles down the node currently at the given index until no longer
     *  needed. */
    private void bubbleDown(int index) {
        Node current = getNode(index);
        int currentI = index;
        int leftI = getLeftOf(currentI);
        int rightI = getRightOf(currentI);
        Node leftNode = getNode(leftI);
        Node rightNode = getNode(rightI);

        while (leftNode != null || rightNode != null) {
            int newI;
            if (leftNode != null && rightNode != null && current._priority > leftNode._priority && current._priority > rightNode._priority) {
                newI = min(leftI, rightI);
            } else if (leftNode != null && current._priority > leftNode._priority) {
                newI = leftI;
            } else if (rightNode != null && current._priority > rightNode._priority) {
                newI = rightI;
            } else {
                break;
            }
            swap(newI, currentI);

            currentI = newI;
            leftI = getLeftOf(currentI);
            rightI = getRightOf(currentI);
            leftNode = getNode(leftI);
            rightNode = getNode(rightI);
        }
    }

    /** Inserts an item with the given priority value. Assume that item is
     * not already in the heap. Same as enqueue, or offer. */
    public void insert(T item, double priority) {
        int index = 1;
        for (int i = 1; i <= contents.size(); i += 1) {
            if (i == contents.size()) {
                contents.add(new Node(item, priority));
                index = i;
                break;
            } else if (contents.get(i) == null) {
                contents.set(i, new Node(item, priority));
                index = i;
            }
        }
        bubbleUp(index);
    }

    /** Returns the element with the smallest priority value, and removes
     * it from the heap. If multiple items have the minimum priority value,
     * removes any of them. Returns null if the heap is empty. Same as
     * dequeue, or poll. */
    public T removeMin() {
        if (contents.size() <= 1) {
            return null;
        }

        int temp = 1;
        T value = getNode(temp)._item;
        while (getNode(getRightOf(temp)) != null) {
            temp = getRightOf(temp);
        }
        swap(1, temp);
        removeNode(temp);

        bubbleDown(1);
        return value;
    }

    /** Changes the node in this heap with the given item to have the given
     * priority. You can assume the heap will not have two nodes with the
     * same item. Does nothing if the item is not in the heap. Check for
     * item equality with .equals(), not == */
    public void changePriority(T item, double priority) {
        for (int i = 1; i < contents.size(); i += 1) {
            if (getNode(i)._item.equals(item)) {
                getNode(i).setPriority(priority);
                bubbleUp(i);
                bubbleDown(i);
            }
        }
    }
}
