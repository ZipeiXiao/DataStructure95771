// File: ObjectNode.java from the package edu.colorado.nodes
// Complete documentation is available from the ObjectNode link in:
//   http://www.cs.colorado.edu/~main/docs

package edu.colorado.nodes;

/******************************************************************************
 * A ObjectNode provides a node for a linked list with
 * Object data in each node.
 *
 * @note
 *   Lists of nodes can be made of any length, limited only by the amount of
 *   free memory in the heap. But beyond Integer.MAX_VALUE (2,147,483,647),
 *   the answer from listLength is incorrect because of arithmetic
 *   overflow.
 *
 * @see
 *   <A HREF="../../../../edu/colorado/nodes/ObjectNode.java">
 *   Java Source Code for this class
 *   (www.cs.colorado.edu/~main/edu/colorado/nodes/ObjectNode.java) </A>
 *
 * @author Michael Main
 *   <A HREF="mailto:main@colorado.edu"> (main@colorado.edu) </A>
 *
 * @version Feb 10, 2016
 ******************************************************************************/

public class ObjectNode {
    // Invariant of the edu.colorado.nodes.ObjectNode class:
    //   1. The node's Object data is in the instance variable data.
    //   2. For the final node of a list, the link part is null.
    //      Otherwise, the link part is a reference to the
    //      next node of the list.
    private Object data;
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
    public ObjectNode(Object initialData, ObjectNode initialLink) {
        data = initialData;
        link = initialLink;
    }


    /**
     * Modification method to add a new node after this node.
     * @param item
     *   the data to place in the new node
     * @postcondition
     *   A new node has been created and placed after this node.
     *   The data for the new node is item. Any other nodes
     *   that used to be after this node are now after the new node.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for a new
     *   edu.colorado.nodes.ObjectNode.
     * @time O(1)
     **/
    public void addNodeAfter(Object item) {
        link = new ObjectNode(item, link);
    }


    /**
     * Accessor method to get the data from this node.
     * @return
     *   the data from this node
     * @time O(1)
     **/
    public Object getData( ) {
        return data;
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
     * Copy a list.
     * @param source
     *   the head of a linked list that will be copied (which may be
     *   an empty list in where source is null)
     * @pre-conditions
     *    Source should not contains a list, i.e., list of a list. Can only copy 1D list not 2D.
     * @return
     *   The method has made a copy of the linked list starting at
     *   source. The return value is the head reference for the
     *   copy.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     * @time O(n)
     **/
    public static ObjectNode listCopy(ObjectNode source) {
        ObjectNode copyHead;
        ObjectNode copyTail;

        // Handle the special case of the empty list.
        if (source == null)
            return null;

        // Make the first node for the newly created list.
        copyHead = new ObjectNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null) {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head reference for the new list.
        return copyHead;
    }

    /**
     * A recursive method behaves the same way as Main’s listCopy() method
     * but will be recursive.
     * @param source
     *      the head of a linked list that will be copied (which may be
     *      an empty list in where source is null)
     * @return
     *      The method has made a copy of the linked list starting at
     *      source. The return value is the head reference for the
     *      copy.
     * @exception OutOfMemoryError
     *      Indicates that there is insufficient memory for the new list.
     * @time O(n)
     */
    public static ObjectNode listCopy_rec(ObjectNode source) {
        if (source == null) {
            return null;
        }
        ObjectNode newNode = new ObjectNode(source.data, listCopy_rec(source.link));
        return newNode;
    }


    /**
     * Copy a list, returning both a head and tail reference for the copy.
     * @param source
     *   the head of a linked list that will be copied (which may be
     *   an empty list in where source is null)
     * @pre-conditions
     *   Source should not contains a list, i.e., list of a list. Can only copy 1D list not 2D.
     * @return
     *   The method has made a copy of the linked list starting at
     *   source.  The return value is an
     *   array where the [0] element is a head reference for the copy and the [1]
     *   element is a tail reference for the copy.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     * @time O(n)
     **/
    public static ObjectNode[ ] listCopyWithTail(ObjectNode source) {
        ObjectNode copyHead;
        ObjectNode copyTail;
        ObjectNode[ ] answer = new ObjectNode[2];

        // Handle the special case of the empty list.
        if (source == null)
            return answer; // The answer has two null references .

        // Make the first node for the newly created list.
        copyHead = new ObjectNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null) {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references.
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    }


    /**
     * Compute the number of nodes in a linked list.
     * @param head
     *   the head reference for a linked list (which may be an empty list
     *   with a null head)
     * @return
     *   the number of nodes in the list with the given head
     * @note
     *   A wrong answer occurs for lists longer than Int.MAX_VALUE.
     * @time O(n)
     **/
    public static int listLength(ObjectNode head) {
        ObjectNode cursor;
        int answer;

        answer = 0;
        for (cursor = head; cursor != null; cursor = cursor.link)
            answer++;

        return answer;
    }

    /**
     * A recursive method that behaves the same way as Main’s listLength()
     * method but will be recursive.
     *  @param head
     *      the head reference for a linked list (which may be an empty list
     *      with a null head)
     * @return
     *      the number of nodes in the list with the given head
     * @note
     *      A wrong answer occurs for lists longer than Int.MAX_VALUE.
     * @time O(n)
     */
    public static int listLength_rec(ObjectNode head) {
        if (head == null) {
            return 0;
        }
        return 1 + listLength_rec(head.link);
    }


    /**
     * Copy part of a list, providing a head and tail reference for the new copy.
     * @precondition
     *   start and end are non-null references to nodes
     *   on the same linked list,
     *   with the start node at or before the end node.
     * @return
     *   The method has made a copy of the part of a linked list, from the
     *   specified start node to the specified end node. The return value is an
     *   array where the [0] component is a head reference for the copy and the
     *   [1] component is a tail reference for the copy.
     * @param start
     *   first node to copy
     * @param end
     *   final node to copy
     * @exception IllegalArgumentException
     *   Indicates that start and end are not references
     *   to nodes on the same list.
     * @exception NullPointerException
     *   Indicates that start is null.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     * @time O(n)
     **/
    public static ObjectNode[ ] listPart(ObjectNode start, ObjectNode end) {
        ObjectNode copyHead;
        ObjectNode copyTail;
        ObjectNode cursor;
        ObjectNode[ ] answer = new ObjectNode[2];

        // Make the first node for the newly created list. Notice that this will
        // cause a NullPointerException if start is null.
        copyHead = new ObjectNode(start.data, null);
        copyTail = copyHead;
        cursor = start;

        // Make the rest of the nodes for the newly created list.
        while (cursor != end) {
            cursor = cursor.link;
            if (cursor == null)
                throw new IllegalArgumentException
                        ("end node was not found on the list");
            copyTail.addNodeAfter(cursor.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
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


    /**
     * Search for a particular piece of data in a linked list.
     * @param head
     *   the head reference for a linked list (which may be an empty list in
     *   which case the head is null)
     * @param target
     *   a piece of data to search for
     * @return
     *   The return value is a reference to the first node that contains the
     *   specified target. If there is no such node, the null reference is
     *   returned.
     * @time O(n)
     **/
    public static ObjectNode listSearch(ObjectNode head, Object target) {
        ObjectNode cursor;

        for (cursor = head; cursor != null; cursor = cursor.link)
            if (target == cursor.data)
                return cursor;

        return null;
    }

    public String toString() {
        if (this == null) {
            return null;
        }
        Object data = getData();
        StringBuilder sb = new StringBuilder();
        sb.append(data);
        ObjectNode link = getLink();
        while (link != null) {
            data = link.getData();
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
    public void removeNodeAfter( ) {
        link = link.link;
    }


    /**
     * Modification method to set the data in this node.
     * @param newData
     *   the new data to place in this node
     * @postcondition
     *   The data of this node has been set to newData.
     * @time O(1)
     **/
    public void setData(Object newData) {
        data = newData;
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

    /**
     * Provide a main routine (a driver) for this class.
     * @param args terminal input
     */
    public static void main(String[] args) {
        /*
         * a. Create a list containing 26 nodes. Each node will contain
         * the letters of the English language in order and
         * capitalized. The list will hold "A" -> "B" -> "C" -> … ->
         * "Z" --||
         */
        ObjectNode list = new ObjectNode('A', null);
        ObjectNode cur = list;
        for (int i = 25; i >= 1; i--) {
            ObjectNode next = new ObjectNode((char) ('A' + i), null);
            cur.addNodeAfter(next);
        }

        /*
         * b. Call toString() on the front node of the list to display
         * the list data. The output will be ABCD…Z.
         */
        System.out.println(list.toString());

        /*
         * c. Print the size of this list twice. Once with listLength()
         * and again with listLength_rec().
         */
        System.out.println(listLength(list));
        System.out.println(listLength_rec(list));

        /*
         * d. Make a copy of the list into a new list, use listCopy(),
         * with the front node of the new list being pointed to by
         * an ObjectNode named k.
         */
        ObjectNode k = listCopy(list);

        /*
         * e. On k, call its toString() method. The output will be
         * ABCD…Z.
         */
        System.out.println(k.toString());

        /*
         * f. Print the size of the list k twice. Once with
         * listLength() and again with listLength_rec.
         */
        System.out.println(listLength(k));
        System.out.println(listLength_rec(k));

        /*
         * g. Make a copy of the list into a new list, use
         * listCopy_rec(), with the front node of the new list being
         * pointed to by an ObjectNode named k2.
         */
        ObjectNode k2 = listCopy_rec(list);

        /*
         * h. On k2, call its toString() method. The output will be
         * ABCD…Z.
         */
        System.out.println(k2.toString());

        /*
         * i. Print the size of the list k2 twice. Once with
         * listLength() and again with listLength_rec.
         */
        System.out.println(listLength(k2));
        System.out.println(listLength_rec(k2));
    }
}
