public class MinHeap {

    private Node[] heapArray;
    private int currentSize;

    public MinHeap(int capacity) {
        heapArray = new Node[capacity];

        // foreach u in Q
        for (int i = 0; i < capacity; i++) {

            // do: key[u] = ? (initialize to ‘infinity’)
            heapArray[i] = new Node(Double.MAX_VALUE, i);
        }

        // key[r] = 0
        heapArray[0].setKey(0);
        currentSize = capacity;
    }

    /**
     * Removes the highest priority key (minimum for min heap)
     * @return removed node. Exception if empty.
     */
    public Node deleteMin() {
        if (currentSize == 0) {
            return null;
        }
        Node root = heapArray[0];
        currentSize--;

        // root <- last one
        heapArray[0] = heapArray[currentSize];
        heapArray[currentSize] = null;
        percolateDown(0);
        return root;
    }

    private void percolateDown(int index) {
        Node top = heapArray[index];

        // larger child's index
        int smallerChild;

        // while there is as at least left child
        while (index < (currentSize / 2)) {
            int leftChild = index * 2 + 1;
            int rightChild = leftChild + 1;

            // find which one is larger child
            if (rightChild < currentSize && heapArray[leftChild].getKey() > heapArray[rightChild].getKey()) {
                smallerChild = rightChild;
            } else {
                smallerChild = leftChild;
            }

            // no need to go down any more
            if (heapArray[smallerChild].getKey() >= top.getKey()) {
                break;
            }

            // move the nodes up
            heapArray[index] = heapArray[smallerChild];

            // index goes down toward smaller child
            index = smallerChild;
        }

        // put top key into proper location to restore the heap
        heapArray[index] = top;
    }

    public Node[] getHeapArray() {
        return heapArray;
    }

    public int getCurrentSize() {
        return currentSize;
    }
}
