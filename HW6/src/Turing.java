import java.util.Arrays;

public class Turing {

    /**
     * Q, a finite set of states.
     * For this program Q = {0,1,2,…n-1} and is selected by the client programmer.
     */
    private State[] states;
    /**
     * define an array of size 100
     */
    private char[] tape = new char[100];
    private int head;
    private int size;

    /**
     * Constructor.
     * @param number number of state to hold
     */
    public Turing(int number) {
        states = new State[number];

        // B, a symbol of , is the blank
        Arrays.fill(tape, 'B');

        // set the initial read/write head to 0 (the leftmost position on the Turing machine tape)
        head = 0;
        size = 0;
    }

    /**
     * Add a state to the turing machine.
     * @param s state to add
     */
    public void addState(State s) {
        states[size++] = s;
    }

    /**
     * Running Turing machine.
     * @param input input tape
     * @return output tape
     * @throws Exception input tape is too long
     */
    public String execute(String input) throws Exception {
        if (input.length() > 100) {
            throw new Exception("tape only has size 100");
        }

        System.arraycopy(input.toCharArray(), 0, tape, 0, input.length());

        // It stops, by entering the halt state, when it encounters a B in the input.
        if (tape[0] == 'B') {
            return tape.toString();
        }

        int curState = 0;

        // We will adopt the convention that an n state machine will always use state n-1 as the halting state.
        while (curState != states.length - 1) {

            char oldChar = tape[head];
            System.out.println("oldChar: "+ oldChar);

            // get the current transition
            Transition curTrans = states[curState].transitions.get(tape[head]);

            // get the state of current transition
            curState = curTrans.getState();

            System.out.println("the new state of current transition: q" + curState);
            System.out.println("write new character: " + curTrans.getNewSymbol());

            // write new character
            tape[head] = curTrans.getNewSymbol();

            // move the head
            int step = curTrans.getStep();
            head += step;

            System.out.println(step == 1 ? "RIGHT\n" : "left\n");
        }
        return String.valueOf(tape);
    }
}
