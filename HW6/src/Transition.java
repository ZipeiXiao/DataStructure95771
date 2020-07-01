public class Transition {

    /**
     * Move to right.
     */
    public static final int RIGHT = 1;
    /**
     * Move to left.
     */
    public static final int LEFT = -1;

    private char oldSymbol;
    private char newSymbol;
    private int step;
    private int state;

    /**
     * Constructor
     * @param oldSymbol old character
     * @param newSymbol new character
     * @param step movement steps
     * @param state state
     */
    public Transition(char oldSymbol, char newSymbol, int step, int state) {
        this.oldSymbol = oldSymbol;
        this.newSymbol = newSymbol;
        this.step = step;
        this.state = state;
    }

    /**
     * Getter of old character
     * @return old character
     */
    public char getOldSymbol() {
        return oldSymbol;
    }

    /**
     * Getter of new character
     * @return new character
     */
    public char getNewSymbol() {
        return newSymbol;
    }

    /**
     * Getter of step
     * @return movement
     */
    public int getStep() {
        return step;
    }

    /**
     * Getter of state
     * @return state
     */
    public int getState() {
        return state;
    }
}
