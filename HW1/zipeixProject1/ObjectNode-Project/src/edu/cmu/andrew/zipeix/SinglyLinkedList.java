package edu.cmu.andrew.zipeix;

import edu.colorado.nodes.ObjectNode;

import java.util.Iterator;

/**
 * SinglyLinkedList has a head and tail pointer.
 * Head and tail are both ObjectNodes.
 */
public class SinglyLinkedList {
    /**
     * The head pointer is null or points to the first element on the list.
     */
    private ObjectNode head;
    /**
     * The tail pointer is null or points to the last node on the list.
     */
    private ObjectNode tail;
    /**
     * An integer countNodes is maintained to keep count of the number of nodes added to the list.
     * This provided an O(1) count to the caller.
     */
    private int countNodes;

    private ObjectNode iterator;

    /**
     * Constructs a new SinglyLinkedList object.
     */
    public SinglyLinkedList() {
        head = null;
        tail = null;
        iterator = head;
    }

    /**
     * reset the iterator to the beginning of the list.
     * That is, set a reference to the head of the list.
     */
    public void reset() {
        iterator = head;
    }

    /**
     * return the Object pointed to by the iterator and increment the iterator to the next node in the list.
     * This reference becomes null if the object returned is the last node on the list.
     * @return
     */
    public Object next() {
        if (hasNext()) {
            Object data = iterator.getData();
            iterator = iterator.getLink();
            return data;
        }
        return null;
    }

    /**
     * @return true if the iterator is not null
     */
    public boolean hasNext() {
        return iterator != null;
    }

    /**
     * Add a node containing the Object c to the head of the linked list.
     * @param c
     *      a single Object This method increments a count of the number of nodes on the list.
     *      The count is returned by countNodes.
     */
    public void addAtFrontNode(Object c) {
        ObjectNode node = new ObjectNode(c, head);
        head = node;
        countNodes++;
    }

    /**
     * Add a node containing the Object c to the end of the linked list.
     * No searching of the list is required.
     * The tail pointer is used to access the last node in O(1) time.
     * @param c a single Object
     */
    public void addAtEndNode(Object c) {
        ObjectNode node = new ObjectNode(c, null);
        if (head == null) {
            head = node;
        } else {
            ObjectNode cur = head.getLink();
            while (cur.getLink() != null) {
                cur = cur.getLink();
            }
            cur.setLink(node);
        }
        tail = node;
        countNodes++;
    }

    /**
     * Counts the number of nodes in the list.
     * @return
     *      The number of nodes in the list between head and tail inclusive.
     *      No list traversal is performed. Simply return the value of countNodes.
     */
    public int countNodes() {
        return countNodes;
    }

    /**
     * Returns a reference (0 based) to the object with list index i.
     * @param i index number
     * @return
     *      reference to an object with list index i.
     *      The first object in the list is at position 0.
     */
    public Object getObjectAt(int i) {
        if (i < 0) {
            return null;
        }
        ObjectNode cur = head;
        for (int count = 0; count < i; count++) {
            cur = cur.getLink();
        }
        return cur.getData();
    }

    /**
     * Returns the data in the tail of the list.
     * @return the data in the tail of the list
     */
    public Object getLast() {
        if (head == null) {
            return null;
        }
        ObjectNode cur = head;
        while (cur.getLink() != null) {
            cur = cur.getLink();
        }
        return cur.getData();
    }

    /**
     * Returns the list as a String.
     * @return a String containing the Objects in the list
     */
    public String toString() {
        if (head == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        ObjectNode cur = head;
        while (cur != null) {
            sb.append(cur.getData());
            cur = cur.getLink();
        }
        return sb.toString();
    }

    /**
     * Test Driver: Testing with BigInteger data and a list of lists.
     * @param args unused
     */
    public static void main(String[] args) {
        SinglyLinkedList s = new SinglyLinkedList();
        s.addAtEndNode("addAtEndNode");
        s.addAtFrontNode("java.lang.Object");
        s.addAtEndNode("test");
        s.addAtFrontNode("node");

        System.out.println(s.countNodes());
        System.out.println("******************");
        System.out.println(s.getObjectAt(2));
        System.out.println("******************");
        System.out.println(s.getLast());
        System.out.println("******************");
        System.out.println(s.toString());
        System.out.println("******************");

        s.reset();
        while(s.hasNext()) {
            System.out.println(s.next());
        }
    }
}
