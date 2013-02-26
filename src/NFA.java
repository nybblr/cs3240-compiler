import java.util.HashSet;

public class NFA {
	public HashSet<Transition> transitions = new HashSet<Transition>();
	public HashSet<State> states = new HashSet<State>();
	public State start;

	/* Constructors */
	public NFA(State start) {
		this.start = start;
	}

	/* Getters and setters */
	public State getStart() {
		return start;
	}

	public void setStart(State start) {
		this.start = start;
	}

	public HashSet<Transition> getTransitions() {
		return transitions;
	}

	public HashSet<State> getStates() {
		return states;
	}

	/* Traversal */
	public Boolean isAccepted(String string) {
		return false;
	}
}
