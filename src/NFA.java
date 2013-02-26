import java.util.ArrayList;

public class NFA {
	public ArrayList<Transition> transitions = new ArrayList<Transition>();
	public ArrayList<State> states = new ArrayList<State>();
	public State start;

	public State getStart() {
		return start;
	}

	public void setStart(State start) {
		this.start = start;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	public ArrayList<State> getStates() {
		return states;
	}
}
