public class RedBlackTree {

    /**
     * Constant Field Values.
     */
    public static final int BLACK = 0;
    public static final int RED = 1;
    public static final RedBlackNode nulls = new RedBlackNode(null, BLACK, null, null, null);
    public RedBlackNode root;
    private int comparison = 0;
    private int size = 0;

    /**
     *  This constructor creates an empty RedBlackTree.
     *  It creates a RedBlackNode that represents null.
     *  It sets the internal variable tree to point to this
     *  node. This node that an empty tree points to will be
     *  used as a sentinel node. That is, all pointers in the
     *  tree that would normally contain the value null, will instead
     *  point to this sentinel node.
     *  @worst-time O(1)
     *  @best-time O(1)
     */
    public RedBlackTree() {
        root = new RedBlackNode(null, BLACK, nulls, nulls, nulls);
        comparison = 0;
        size = 0;
    }

    /**
     * @return number of values inserted into the tree.
     * @worst-time O(1)
     * @best-time O(1)
     */
    public int getSize() {
        return size;
    }

    /**
     * Performs an inorder traversal of the tree.
     * The inOrderTraversal(RedBlackNode) method is recursive and displays the content of the tree.
     * It makes calls on System.out.println().
     * This method would normally be private.
     * @param t the root of the tree on the first call.
     * @worst-time O(N)
     * @best-time O(N)
     */
    public void inOrderTraversal(RedBlackNode t) {
        if (t == nulls) {
            return;
        }
        // go left
        inOrderTraversal(t.getLc());

        // deal with the root
        System.out.println(t.toString());

        // go right
        inOrderTraversal(t.getRc());
    }

    /**
     * The no argument inOrderTraversal() method calls the recursive inOrderTraversal(RedBlackNode) - passing the root.
     * @worst-time O(N)
     * @best-time O(N)
     */
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    /**
     * Perform a reverseOrder traversal of the tree.
     * The reverseOrderTraversal(RedBlackNode) method is recursive and displays content of the tree in reverse order.
     * This method would normally be private.
     * @param t the root of the tree on the first call.
     * @worst-time O(N)
     * @best-time O(N)
     */
    public void reverseOrderTraversal(RedBlackNode t) {
        if (t == nulls) {
            return;
        }

        // go right
        inOrderTraversal(t.getRc());

        // deal with the root
        System.out.println(t.toString());

        // go left
        inOrderTraversal(t.getLc());
    }

    /**
     * The no argument reverseOrderTraversal() method calls the recursive reverseOrderTraversal(RedBlackNode) - passing the root.
     * @worst-time O(N)
     * @best-time O(N)
     */
    public void reverseOrderTraversal() {
        reverseOrderTraversal(root);
    }

    /**
     * The insert() method places a data item into the tree.
     * @param value is an integer to be inserted
     * @pre-condition memory is available for insertion
     * @worst-time O(logN)
     * @best-time O(1)
     */
    public void insert(String value) {
        size++;

        if (root.getData() == null) {
            root.setData(value);
            return;
        }

        // y = nil[T]
        RedBlackNode y = nulls;

        // x = root[T]
        RedBlackNode x = root;

        // while x != nil[T]
        while (x != nulls) {
            // y = x
            y = x;

            // if key[z] < key[x] then
            if (value.compareTo(x.getData()) < 0) {

                // x = left[x]
                x = x.getLc();
            } else {

                // x = right[x]
                x = x.getRc();
            }
        }

        // p[z] = y
        // color[z] = RED
        // left[z] = nil[T]
        // right[z] = nil[T]
        RedBlackNode newNode = new RedBlackNode(value, RED, y, nulls, nulls);

        // if key[z] < key[y] then
        if (value.compareTo(y.getData()) < 0) {
            // left[y] = z
            y.setLc(new RedBlackNode(value, RED, y, nulls, nulls));
            RBInsertFixup(y.getLc());
        } else {
            // right[y] = z
            y.setRc(new RedBlackNode(value, RED, y, nulls, nulls));
            RBInsertFixup(y.getRc());
        }
        // RB-Insert-fixup(T,z)

    }

    /**
     * leftRotate() performs a single left rotation.
     * This would normally be a private method.
     * @param x node
     * @worst-time O(1)
     * @best-time O(1)
     */
    public void leftRotate(RedBlackNode x) {
        // y = right[x]
        RedBlackNode y = x.getRc();

        // right[x] = left[y]
        x.setRc(y.getLc());

        // p[left[y]] = x
        y.getLc().setP(x);

        // p[y] = p[x]
        y.setP(x.getP());

        // if p[x] == nil[T] then root[T] = y
        if (x.getP() == nulls) {
            root = y;
        } else {

            // if x == left[p[x]] then left[p[x]] = y
            if (x == x.getP().getLc()) {
                x.getP().setLc(y);
            } else {

                // right[p[x]] = y
                x.getP().setRc(y);
            }
        }

        // left[y] = x
        y.setLc(x);

        // p[x] = y
        x.setP(y);
    }

    /**
     * rightRotate() performs a single right rotation.
     * This would normally be a private method.
     * @param x tree node
     * @worst-time O(1)
     * @best-time O(1)
     */
    public void rightRotate(RedBlackNode x) {
        // y now points to node to left of x
        RedBlackNode y = x.getLc();

        // y's right subtree becomes x's left subtree
        x.setLc(y.getRc());

        // right subtree of y gets a new parent
        y.getRc().setP(x);

        // y's parent is now x's parent
        y.setP(x.getP());

        // if x is at root then y becomes new root
        if (x.getP() == nulls) {
            root = y;
        } else {
            // if x == left[p[x]] then left[p[x]] = y
            if (x == x.getP().getLc()) {
                x.getP().setLc(y);
            } else {
                // adjust x's parent's right child
                // right[p[x]] = y
                x.getP().setRc(y);
            }
        }

        // the right child of y is now x
        y.setRc(x);

        // the parent of x is now y
        x.setP(y);
    }

    /**
     * Fixing up the tree so that Red Black Properties are preserved.
     * This would normally be a private method.
     * @param z is the new node
     * @worst-time O(logN)
     * @best-time O(1)
     */
    public void RBInsertFixup(RedBlackNode z) {

        while (z.getP().getColor() == RED) {
            if (z.getP() == z.getP().getP().getLc()) {
                // z's parent is a left child.
                RedBlackNode y = z.getP().getP().getRc();
                if (y.getColor() == RED) {

                    // Case 1: z's uncle y is red.
                    z.getP().setColor(BLACK);
                    y.setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    z = z.getP().getP();

                } else {
                    if (z == z.getP().getRc()) {
                        // Case 2: z's uncle y is black and z is a right child.
                        z = z.getP();
                        leftRotate(z);
                    }

                    // Case 3: z's uncle y is black and z is a left child.
                    z.getP().setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    rightRotate(z.getP().getP());
                }
            } else {
                // z's parent is a right child. Do the same as when z's
                // parent is a left child, but exchange "left" and "right."
                // z's parent is a left child.
                RedBlackNode y = z.getP().getP().getLc();
                if (y.getColor() == RED) {

                    // Case 1: z's uncle y is red.
                    z.getP().setColor(BLACK);
                    y.setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    z = z.getP().getP();

                } else {
                    if (z == z.getP().getLc()) {
                        // Case 2: z's uncle y is black and z is a left child.
                        z = z.getP();
                        rightRotate(z);
                    }

                    // Case 3: z's uncle y is black and z is a right child.
                    z.getP().setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    leftRotate(z.getP().getP());
                }

            }

            // The root is always black.
            root.setColor(BLACK);
        }
    }

    /**
     * The boolean contains() returns true if the String v is in the RedBlackTree and false otherwise.
     * It counts each comparison it makes in the variable recentCompares.
     * @param v the value to search for
     * @return true if v is in the tree, false otherwise
     * @worst-time O(logN)
     * @best-time O(1)
     */
    public boolean contains(String v) {
        comparison = 0;
        RedBlackNode cur = root;
        while (cur != nulls) {
            comparison++;
            if (v.equals(cur.getData())) {
                return true;
            } else if (v.compareTo(cur.getData()) < 0) {
                cur = cur.getLc();
            } else {
                cur = cur.getRc();
            }
        }
        return false;
    }

    /**
     * @return number of comparisons made in last call on the contains method.
     * @worst-time O(1)
     * @best-time O(1)
     */
    public int getRecentCompares() {
        return comparison;
    }

    /**
     * The method closeBy(v) returns a value close to v in the tree.
     * If v is found in the tree it returns v.
     * @param v the value to search close by for.
     * @return the closest string
     * @worst-time O(logN)
     * @best-time O(1)
     */
    public String closeBy(String v) {
        RedBlackNode prev = root;
        RedBlackNode cur = root;
        while (cur != nulls) {
            if (v.compareTo(cur.getData()) < 0) {
                prev = cur;
                cur = cur.getLc();
            } else if (v.compareTo(cur.getData()) > 0) {
                prev = cur;
                cur = cur.getRc();
            } else {
                return v;
            }
        }
        return prev.getData();
    }

    /**
     * This a recursive routine that is used to compute the height of the red black tree.
     * It is called by the height() method.
     * The height() method passes the root of the tree to this method.
     * This method would normally be private.
     * @param t a pointer to a node in the tree.
     * @return the height of node t
     * @worst-time O(logN)
     * @best-time O(logN)
     */
    public int height(RedBlackNode t) {
        if (t == nulls) {
            return 0;
        }
        int left = height(t.getLc());
        int right = height(t.getRc());
        return 1 + Math.max(left, right);
    }

    /**
     * This method calls the recursive method.
     * @return the height of the red black tree.
     * @worst-time O(logN)
     * @best-time O(logN)
     */
    public int height() {
        return height(root) == 0 ? 0 : height(root) - 1;
    }

    /**
     * This method displays the RedBlackTree in level order.
     * It first displays the root.
     * Then it displays all children of the root.
     * Then it displays all nodes on level 3 and so on.
     * It is not recursive.
     * It uses a queue.
     * @worst-time O(N)
     * @best-time O(N)
     */
    public void levelOrderTraversal() {
        Queue queue = new Queue();
        queue.enQueue(root);
        while (!queue.isEmpty()) {
            RedBlackNode t = (RedBlackNode) queue.deQueue();
            System.out.println(t.toString());
            if (t.getLc() != nulls) {
                queue.enQueue(t.getLc());
            }
            if (t.getRc() != nulls) {
                queue.enQueue(t.getRc());
            }
        }
    }

    /**
     * Calculate the diameter
     * @param root tree node
     * @return the diameter
     * @worst-time O(n^2)@best-time O(logN)
     */
    public int diameter(RedBlackNode root) {
        /* base case if tree is empty */
        if (root.getData() == null)
            return 0;

        /* get the height of left and right sub trees */
        int height = height(root);

        /* get the diameter of left and right subtrees */
        int ldiameter = diameter(root.getLc());
        int rdiameter = diameter(root.getRc());

        /*
         Return max of following three:
          1) Diameter of left subtree
          2) Diameter of right subtree
          3) Height of left subtree + height of right subtree + 1
         */
        return Math.max(height, Math.max(ldiameter, rdiameter));
    }

    /**
     * Test the RedBlack tree.
     *  For a quick test of your solution, run the following main routine.
     *  You should get output very similar to mine.
     *
     *  RBT in order
     *     [data = 1:Color = Black:Parent = 2: LC = -1: RC = -1]
     *     [data = 2:Color = Black:Parent = -1: LC = 1: RC = 4]
     *     [data = 3:Color = Red:Parent = 4: LC = -1: RC = -1]
     *     [data = 4:Color = Black:Parent = 2: LC = 3: RC = 5]
     *     [data = 5:Color = Red:Parent = 4: LC = -1: RC = -1]
     *     RBT level order
     *     [data = 2:Color = Black:Parent = -1: LC = 1: RC = 4]
     *     [data = 1:Color = Black:Parent = 2: LC = -1: RC = -1]
     *     [data = 4:Color = Black:Parent = 2: LC = 3: RC = 5]
     *     [data = 3:Color = Red:Parent = 4: LC = -1: RC = -1]
     *     [data = 5:Color = Red:Parent = 4: LC = -1: RC = -1]
     *     Found 3
     *     The height is 2
     *
     * @param args no command line arguments
     */
    public static void main(java.lang.String[] args) {
        RedBlackTree rbt = new RedBlackTree();

        for (int j = 1; j <= 5; j++) {
            rbt.insert("" + j);
        }

        // after 1..5 are inserted
        System.out.println("RBT in order");
        rbt.inOrderTraversal();
        System.out.println("RBT level order");
        rbt.levelOrderTraversal();

        // is 3 in the tree
        if (rbt.contains("" + 3)) {
            System.out.println("Found 3");
        } else {
            System.out.println("No 3 found");
        }

        // display the height
        System.out.println("The height is " + rbt.height());
    }
}
