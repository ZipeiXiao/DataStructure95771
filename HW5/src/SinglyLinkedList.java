/**
 * SinglyLinkedList has a head and tail pointer.
 * Head and tail are both ObjectNodes.
 */
public class SinglyLinkedList <K, V> {
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
    public ObjectNode next() {
        if (hasNext()) {
            ObjectNode ans = iterator;
            iterator = iterator.getLink();
            return ans;
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
    public void addAtFrontNode(K k, V c) {
        ObjectNode node = new ObjectNode(k, c, head);
        head = node;
        countNodes++;
    }

    /**
     * Add a node containing the Object c to the end of the linked list.
     * No searching of the list is required.
     * The tail pointer is used to access the last node in O(1) time.
     * @param c a single Object
     */
    public void addAtEndNode(K key, V c) {
        ObjectNode node = new ObjectNode(key, c, null);
        if (head == null) {
            head = node;
        } else {
            ObjectNode cur = head;
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
        if (i < 0 || i >= countNodes) {
            return null;
        }
        ObjectNode cur = head;
        for (int count = 0; count < i; count++) {
            cur = cur.getLink();
        }
        return cur.getValue();
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
        return cur.getValue();
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
            sb.append(cur.getValue());
            cur = cur.getLink();
        }
        return sb.toString();
    }

    /**
     * Search for a particular piece of data in a linked list.
     * @return
     *   The return value is a reference to the first node that contains the
     *   specified target. If there is no such node, the null reference is
     *   returned.
     **/
    public ObjectNode listSearch(K key) {
        ObjectNode cursor;

        for (cursor = head; cursor != null; cursor = cursor.getLink())
            if (key.equals(cursor.getKey()))
                return cursor;

        return null;
    }
}
