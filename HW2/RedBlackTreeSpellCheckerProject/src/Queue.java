/**
 * The Queue is a first in first out data structure.
 * This Queue holds Java Object references.
 * It grows dynamically as long as memory is available.
 * For non-classroom use, these implementation details would not be exposed.
 */
public class Queue {

    private int manyItems = 0;
    private int front = 0;
    private int rear = 0;
    private int capacity = 5;

    /**
     * The queue class will begin with an array of size 5.
     */
    private static final int initialCapacity = 5;
    private Object[] array;

    /**
     * Create an empty queue that lives in a small array.
     * @worst-time O(1)
     * @best-time O(1)
     */
    Queue() {
        array = new Object[initialCapacity];
    }

    /**
     * Boolean method returns true on empty queue, false otherwise.
     * Pre: None
     * @return Returns true if queue is empty.
     * @worst-time O(1)
     * @best-time O(1)
     */
    public boolean isEmpty() {
        return manyItems == 0;
    }

    /**
     * Boolean method returns true if queue is currently at capacity, false otherwise.
     * If full returns true and additional enqueue calls are made, the queue will expand in size.
     * Pre: None
     * @return Returns true if queue is at current capacity.
     * @worst-time O(1)
     * @best-time O(1)
     */
    public boolean isFull() {
        return manyItems == capacity;
    }

    /**
     * Object method removes and returns reference in front of queue.
     * Pre-condition: queue not empty
     * @return object in front of queue.
     * @worst-time O(1)
     * @best-time O(1)
     */
    public Object deQueue() {
        Object answer = array[front];
        front = (front + 1) % capacity;
        manyItems--;
        return answer;
    }

    /**
     * Add an object reference to the rear of the queue.
     * Pre-condition Memory is available for doubling queue capacity when full.
     * Post-condition: queue now contains x in the rear.
     * @param x is an object to be added to the rear of the queue.
     * @worst-time O(1)
     * @best-time O(1)
     */
    public void enQueue(Object x) {
        if (manyItems == 0) {
            front = rear = 0;
        } else if (isFull()) {
            Object[] newArray = new Object[capacity * 2];
            for (int i = 0; i < capacity; i++) {
                newArray[i] = array[(i + front) % capacity];
            }
            array = newArray;
            front = 0;
            rear = capacity;
            capacity *= 2;
        } else {
            rear = (rear + 1) % capacity;
        }
        array[rear] = x;
        manyItems++;
    }

    /**
     * Method getFront returns the front of the queue without removing it.
     * Pre-condition: queue not empty
     * @return the queue front without removal.
     * @worst-time O(1)
     * @best-time O(1)
     */
    public Object getFront() {
        return array[front];
    }

    /**
     * The toString method returns a String representation of the current queue contents.
     * @override toString in class java.lang.Object
     * @return a string representation of the queue.
     *          It shows the front of the queue first.
     *          It then shows the second and third and so on.
     * @worst-time O(N)
     * @best-time O(N)
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < manyItems; i++) {
            sb.append(array[(i + front) % capacity].toString()).append(" ");
        }
        return sb.toString();
    }

    /**
     * main is for testing the queue routines.
     * @param args Command line parameters are not used.
     */
    public static void main(String[] args) {
        Queue queue = new Queue();

        System.out.print("Initial capacity of the queue is: ");
        System.out.println(queue.capacity);

        System.out.print("Is empty?: ");
        System.out.println(queue.isEmpty());

        for (int i = 0; i < 11; i++) {
            System.out.println("Add " + i);
            queue.enQueue("" + i);
        }

        System.out.print("Now, the capacity of the queue is: ");
        System.out.println(queue.capacity);

        System.out.print("Number of reference in queue is: ");
        System.out.println(queue.manyItems);

        for (int i = 0; i < 5; i++) {
            System.out.print("Dequeue: ");
            System.out.println(queue.deQueue());
        }

        System.out.print("Number of reference in queue is: ");
        System.out.println(queue.manyItems);

        System.out.println("Display the reference in the queue: ");
        System.out.println(queue.toString());
    }
}
