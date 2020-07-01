import java.util.HashMap;

public class State {

    /**
     * State ID
     */
    private int id;
    /**
     * Transitions of state
     */
    public HashMap<Character, Transition> transitions = new HashMap<>();

    /**
     * Constructor.
     * @param id state id
     */
    public State(int id) {
        this.id = id;
    }

    /**
     * Add functions to state
     * @param transition transition to add
     */
    public void addTransition(Transition transition) {
        char key = transition.getOldSymbol();
        transitions.put(key, transition);
    }
}
