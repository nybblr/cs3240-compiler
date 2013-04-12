import java.util.HashSet;

public class State implements Cloneable {
    private String label = "";
    private HashSet<Transition> transitions = new HashSet<Transition>();
    private Boolean accepts = false;
    private NFA nfa;
    private int count;
    public Terminals klass; // used in the walker to determine what klass the state belongs to

    private static int counter = 0;

    public StateSet set; // state set that was used to generate this state

    /* Constructors */
    public State() {
        this(null);
    }

    public State(String label) {
        this.label = label;
        count = counter;
        counter++;
    }

    /* Getters and setters */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public NFA getNFA() {
        return nfa;
    }

    public void setNFA(NFA nfa) {
        this.nfa = nfa;
    }

    public HashSet<Transition> getTransitions() {
        return transitions;
    }

    public Boolean getAccepts() {
        return accepts;
    }

    public void setAccepts(Boolean accepts) {
        this.accepts = accepts;

        // If there's an NFA, tell it to update the accepting list
        if (nfa != null)
            nfa.setAccepts(this, accepts);
    }

    /**
     * @return the counter
     */
    public static int getCounter() {
        return counter;
    }

    /* Transitions */
    public Boolean hasTransition(Character on, State to) {
        return false;
    }

    public Transition addTransition(Character on, State to) {
        Transition transition = new Transition(this, on, to);
        return addTransition(transition);
    }

    public Transition addTransition(Transition transition) {
        this.transitions.add(transition);
        return transition;
    }

    public Boolean deleteTransition(Character on, State to) {
        return false;
    }

    public boolean equals(Object other) {
        if (other instanceof State) {
            State state = (State)other;
            if (state == this) return true;
            //			return label.equals(state.getLabel()) &&
            //					accepts == state.getAccepts() &&
            //					nfa.equals(state.getNFA());
            return false;
        } else {
            return false;
        }
    }

    public boolean isStart() {
        return nfa.getStart() == this;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        String string = label;
        if (string == null || string.equals(""))
            string = Integer.toString(count);
        if (klass != null)
        	string += "{"+klass.getName()+"}";
        if (getAccepts())
            string = "+"+string+"+";
        if (isStart())
            string = "->"+string;
        return string;
    }

//    public State rename(){
//        State s = (State) this.clone();
//        s.setCount(getCounter());
//        counter++;
//        return s;
//    }
    
//    @Override
//    public Object clone(){
//        try {
//            State s = (State) super.clone();
//            s.transitions = (HashSet<Transition>) transitions.clone();
//            if(klass == null)
//                s.klass = null;
//            else
//                s.klass = (Terminals) klass.clone();
//            if(set == null)
//                s.set = null;
//            else
//                s.set = (StateSet) set.clone();
//            return s;
//        }
//        catch (CloneNotSupportedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }
//    }
}
