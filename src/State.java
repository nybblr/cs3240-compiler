import java.util.ArrayList;

public class State {
	private String label;
	private ArrayList<Transition> transitions = new ArrayList<Transition>();
	private Boolean accepts;

	/* Getters and setters */
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<Transition> getTransitions() {
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
