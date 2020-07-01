public class TuringDecider {
    public static void main(String[] args) throws Exception {
        Turing machine1 = new Turing(8);

        // Initial State
        State s0 = new State(0);

        // Have found the leftmost 0
        State s1 = new State(1);

        // Finding the next leftmost 1
        State s2 = new State(2);

        // Have found the leftmost 1
        State s3 = new State(3);

        State s4 = new State(4);

        /**
         * Begin replacing character besides the first one by Blank
         */
        // Accept state
        State s5 = new State(5);

        // Reject state
        State s6 = new State(6);

        // For formatting the output
        State s7 = new State(7);

        /**
         *              INPUT SYMBOL
         *  
         * STATE	0	        1	        X	        Y	        B           H
         * q0       -           -           -           -           (q7,0,R)    (q1,H,R)
         * q1       (q2,X,R)	(q6,B,R)	-	        (q4,Y,R)	-           -
         * q2		(q2,0,R)	(q3,Y,L)	-	        (q2,Y,R)	-           -
         * q3		(q3,0,L)	-	        (q1,X,R)	(q3,Y,L)	-           -
         * q4		(q6,0,R)	(q6,1,R)	-	        (q4,Y,R)	(q5,B,L)    -
         * q5		-	        -	        (q5,B,L)	(q5,B,L)	-           (q7,1,R)
         * q6       (q6,B,R)    (q6,B,R)    (q6,B,L)	(q6,B,L)    (q6,B,L)    (q7,0,R)
         * q7       -           -           -           -           -           -
         */
        s0.addTransition(new Transition('B', '0', Transition.RIGHT, 7));
        s0.addTransition(new Transition('H', 'H', Transition.RIGHT, 1));

        s1.addTransition(new Transition('0', 'X', Transition.RIGHT, 2));
        s1.addTransition(new Transition('1', 'B', Transition.RIGHT, 6));
        s1.addTransition(new Transition('Y', 'Y', Transition.RIGHT, 4));

        s2.addTransition(new Transition('0', '0', Transition.RIGHT, 2));
        s2.addTransition(new Transition('1', 'Y', Transition.LEFT, 3));
        s2.addTransition(new Transition('Y', 'Y', Transition.RIGHT, 2));

        s3.addTransition(new Transition('0', '0', Transition.LEFT, 3));
        s3.addTransition(new Transition('X', 'X', Transition.RIGHT, 1));
        s3.addTransition(new Transition('Y', 'Y', Transition.LEFT, 3));

        s4.addTransition(new Transition('0', '0', Transition.RIGHT, 6));
        s4.addTransition(new Transition('1', '1', Transition.RIGHT, 6));
        s4.addTransition(new Transition('Y', 'Y', Transition.RIGHT, 4));
        s4.addTransition(new Transition('B', 'B', Transition.LEFT, 5));

        s5.addTransition(new Transition('X', 'B', Transition.LEFT, 5));
        s5.addTransition(new Transition('Y', 'B', Transition.LEFT, 5));
        s5.addTransition(new Transition('H', '1', Transition.RIGHT, 7));

        s6.addTransition(new Transition('0', 'B', Transition.RIGHT, 6));
        s6.addTransition(new Transition('1', 'B', Transition.RIGHT, 6));
        s6.addTransition(new Transition('X', 'B', Transition.LEFT, 6));
        s6.addTransition(new Transition('Y', 'B', Transition.LEFT, 6));
        s6.addTransition(new Transition('B', 'B', Transition.LEFT, 6));
        s6.addTransition(new Transition('H', '0', Transition.RIGHT, 7));

        /**
         * Add the state to the machine
         */
        machine1.addState(s0);
        machine1.addState(s1);
        machine1.addState(s2);
        machine1.addState(s3);
        machine1.addState(s4);
        machine1.addState(s5);
        machine1.addState(s6);

        // As an exercise, trace the execution of this machine
        // using an	input tape with	the	symbols	0010
        String inTape = "111000";     // Define some input

        System.out.println(inTape);
        String outTape = inTape;

        if (inTape.length() == 0) {
            outTape = machine1.execute(inTape);  // Execute the machine
        } else {
            outTape = machine1.execute("H" + inTape);  // Execute the machine
        }

        System.out.println(outTape);  // Show the machine’s output
    }
}