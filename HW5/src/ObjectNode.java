public class ObjectNode<K, V> {
    // Invariant of the edu.colorado.nodes.ObjectNode class:
    //   1. The node's Object data is in the instance variable data.
    //   2. For the final node of a list, the link part is null.
    //      Otherwise, the link part is a reference to the
    //      next node of the list.
    private K key;
    private V value;
    private ObjectNode link;


    /**
     * Initialize a node with a specified initial data and link to the next
     * node. Note that the initialLink may be the null reference,
     * which indicates that the new node has nothing after it.
     * @param initialData
     *   the initial data of this new node
     * @param initialLink
     *   a reference to the node after this new node--this reference may be null
     *   to indicate that there is no node after this new node.
     * @postcondition
     *   This node contains the specified data and link to the next node.
     * @time O(1)
     **/
    public ObjectNode(K k, V initialData, ObjectNode initialLink) {
        key = k;
        value = initialData;
        link = initialLink;
    }


    /**
     * Modification method to add a new node after this node.
     *   the data to place in the new node
     **/
    public void addNodeAfter(K key, V value) {
        link = new ObjectNode(key, value, link);
    }

    public K getKey() {
        return key;
    }

    /**
     * Accessor method to get the data from this node.
     * @return
     *   the data from this node
     **/
    public V getValue() {
        return value;
    }


    /**
     * Accessor method to get a reference to the next node after this node.
     * @return
     *   a reference to the node after this node (or the null reference if there
     *   is nothing after this node)
     * @time O(1)
     **/
    public ObjectNode getLink( ) {
        return link;
    }

    /**
     * Find a node at a specified position in a linked list.
     * @param head
     *   the head reference for a linked list (which may be an empty list in
     *   which case the head is null)
     * @param position
     *   a node number
     * @precondition
     *   position &gt; 0.
     * @return
     *   The return value is a reference to the node at the specified position in
     *   the list. (The head node is position 1, the next node is position 2, and
     *   so on.) If there is no such position (because the list is too short),
     *   then the null reference is returned.
     * @exception IllegalArgumentException
     *   Indicates that position is not positive.
     * @time O(n)
     **/
    public static ObjectNode listPosition(ObjectNode head, int position) {
        ObjectNode cursor;
        int i;

        if (position < 0)
            throw new IllegalArgumentException("position is not positive");

        cursor = head;
        for (i = 0; (i < position) && (cursor != null); i++)
            cursor = cursor.link;

        return cursor;
    }

    public String toString() {
        if (this == null) {
            return null;
        }
        Object data = getValue();
        StringBuilder sb = new StringBuilder();
        sb.append(data);
        ObjectNode link = getLink();
        while (link != null) {
            data = link.getValue();
            link = link.link;
            sb.append(data);
        }
        return sb.toString();
    }


    /**
     * Modification method to remove the node after this node.
     * @precondition
     *   This node must not be the tail node of the list.
     * @postcondition
     *   The node after this node has been removed from the linked list.
     *   If there were further nodes after that one, they are still
     *   present on the list.
     * @exception NullPointerException
     *   Indicates that this was the tail node of the list, so there is nothing
     *   after it to remove.
     * @time O(1)
     **/
    public void removeNodeAfter() {
        link = link.link;
    }

    public void setKey(K newKey) {
        key = newKey;
    }

    /**
     * Modification method to set the data in this node.
     * @param newData
     *   the new data to place in this node
     * @postcondition
     *   The data of this node has been set to newData.
     * @time O(1)
     **/
    public void setValue(V newData) {
        value = newData;
    }


    /**
     * Modification method to set the link to the next node after this node.
     * @param newLink
     *   a reference to the node that should appear after this node in the linked
     *   list (or the null reference if there is no node after this node)
     * @postcondition
     *   The link to the node after this node has been set to newLink.
     *   Any other node (that used to be in this link) is no longer connected to
     *   this node.
     * @time O(1)
     **/
    public void setLink(ObjectNode newLink) {
        link = newLink;
    }
}
