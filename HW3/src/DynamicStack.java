/**
 * The stack will hold Java objects. Thus, it will be able to contain such Java objects as Strings and BigIntegers.
 */
public class DynamicStack {
    private Object[] array;
    private int top;

    /**
     * Constructor.
     * @best-time Theta(1)
     * @worst-time Theta(1)
     */
    public DynamicStack() {

        // The array will begin with a size of 6.
        array = new Object[6];

        // DynamicStack is implemented in an array with a top index initially set to -1.
        top = -1;
    }

    /**
     * Each push operation will add one to the top index and then add a new element at that location.
     * @param o add object reference
     * @best-time Theta(1)
     * @worst-time Theta(N)
     */
    public void push(Object o) {
        /*
         * If the array is full and a push operation is executed,
         * create a new array of twice the size as the old
         * and copy the elements within the old array over to the new array.
         */
        if (top == array.length - 1) {
            Object[] newArray = new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[++top] = o;
    }

    /**
     * Each pop operation will return the value pointed to by the top pointer and it will decrease the top pointer by 1.
     * @return Object reference
     * @best-time Theta(1)
     * @worst-time Theta(1)
     */
    public Object pop() {
        return array[top--];
    }

    /**
     * @return returns true if and only if the top index is -1.
     * @best-time Theta(1)
     * @worst-time Theta(1)
     */
    public boolean isEmpty() {
        return top == -1;
    }

    public int getSize() {
        return top + 1;
    }

    /**
     * main routine that tests DynamicStack.
     * @param args have not used
     */
    public static void main(String[] args) {
        DynamicStack stack = new DynamicStack();

        System.out.print("Push: ");
        // a loop that pushes 1000 values to the stack.
        for (int i = 0; i < 1000; i++) {
            stack.push(i);
            System.out.print(i + " ");
        }

        System.out.println("Pop: ");
        // Another loop will pop and display all 1000 values pushed.
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
    }
}
