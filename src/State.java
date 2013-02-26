import java.util.HashSet;

public class State {
	private String label;
	private HashSet<Transition> transitions = new HashSet<Transition>();
	private Boolean accepts;

	/* Getters and setters */
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public HashSet<Transition> getTransitions() {
		return transitions;
	}

	public Boolean getAccepts() {
		return accepts;
	}

	public void setAccepts(Boolean accepts) {
		this.accepts = accepts;
	}

	/* Transitions */
	public Boolean hasTransition(Character on, State to) {
		return false;
	}

	public Boolean addTransition(Character on, State to) {
		return false;
	}

	public Boolean deleteTransition(Character on, State to) {
		return false;
	}
}
