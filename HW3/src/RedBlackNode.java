import java.math.BigInteger;

public class RedBlackNode {

    private String key;
    private BigInteger value;
    private int color;
    private RedBlackNode p;
    private RedBlackNode lc;
    private RedBlackNode rc;
    public static final int BLACK = 0;


    /**
     * Construct a RedBlackNode with data, color, parent pointer, left child pointer and right child pointer.
     * @param key a simple value held in the tree
     * @param color either RED or BLACK
     * @param p the parent pointer
     * @param lc the pointer to the left child (will be null only for the node that represents all external nulls.
     * @param rc the pointer to the right child (will be null only for the node that represents all external nulls.
     * @worst-time O(1)
     * @best-time O(1)
     */
    public RedBlackNode(String key, BigInteger value, int color, RedBlackNode p, RedBlackNode lc, RedBlackNode rc) {
        setKey(key);
        setValue(value);
        setColor(color);
        setP(p);
        setLc(lc);
        setRc(rc);
    }

    /**
     * The toString() methods returns a string representation of the RedBlackNode.
     * @override toString in class java.lang.Object
     * @return the string representation of a RedBlackNode
     * @worst-time O(1)
     * @best-time O(1)
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String color = getColor() == BLACK ? "Black" : "Red";
        String parent = getP().getKey() == null ? "-1" : getP().getKey();
        String lc = getLc().getKey() == null ? "-1" : getLc().getKey();
        String rc = getRc().getKey() == null ? "-1" : getRc().getKey();

        sb.append("[key = ").append(getKey())
                .append(":Value = ").append(getValue())
                .append(":Color = ").append(color)
                .append(":Parent = ").append(parent)
                .append(": LC = ").append(lc)
                .append(": RC = ").append(rc);
        return sb.toString();
    }

    /**
     * The getColor() method returns RED or BLACK.
     * @return the color value (RED or BLACK)
     * @worst-time O(1)
     * @best-time O(1)
     */
    public int getColor() {
        return color;
    }

    /**
     * The getData() method returns the data in the node.
     * @return the data value in the node
     * @worst-time O(1)
     * @best-time O(1)
     */
    public String getKey() {
        return key;
    }

    public BigInteger getValue() {
        return value;
    }

    /**
     * The getLc() method returns the left child of the RedBlackNode.
     * @return the left child field
     * @worst-time O(1)
     * @best-time O(1)
     */
    public RedBlackNode getLc() {
        return lc;
    }

    /**
     * The getP() method returns the parent of the RedBlackNode.
     * @return the parent field
     * @worst-time O(1)
     * @best-time O(1)
     */
    public RedBlackNode getP() {
        return p;
    }

    /**
     * The getRc() method returns the right child of the RedBlackNode.
     * @return the right child field
     * @worst-time O(1)
     * @best-time O(1)
     */
    public RedBlackNode getRc() {
        return rc;
    }

    /**
     * The setColor() method sets the color of the RedBlackNode.
     * @param color is either RED or BLACK
     * @worst-time O(1)
     * @best-time O(1)
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * The setData() method sets the data or key of the RedBlackNode.
     * @param key is an int holding a node's data value
     * @worst-time O(1)
     * @best-time O(1)
     */
    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    /**
     * The setLc() method sets the left child of the RedBlackNode.
     * @param lc establishes a left child for this node
     * @worst-time O(1)
     * @best-time O(1)
     */
    public void setLc(RedBlackNode lc) {
        this.lc = lc;
    }

    /**
     * The setP() method sets the parent of the RedBlackNode.
     * @param p establishes a parent pointer for this node
     * @worst-time O(1)
     * @best-time O(1)
     */
    public void setP(RedBlackNode p) {
        this.p = p;
    }

    /**
     * The setRc() method sets the right child of the RedBlackNode.
     * @param rc establishes a right child for this node.
     * @worst-time O(1)
     * @best-time O(1)
     */
    public void setRc(RedBlackNode rc) {
        this.rc = rc;
    }
}
