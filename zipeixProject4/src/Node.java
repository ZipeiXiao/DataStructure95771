import java.util.ArrayList;
import java.util.List;

public class Node {

    private double key;
    private int index;
    private List<Node> children;

    Node(double k, int i) {
        key = k;
        index = i;
        children = new ArrayList<>();
    }

    public double getKey() {
        return key;
    }

    public void setKey(double key) {
        this.key = key;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Node> getChildren() {
        return children;
    }
}
