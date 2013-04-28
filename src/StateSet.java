import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class StateSet implements Cloneable {
	private Set<State> states = new HashSet<State>();

	/* Constructors */
	public StateSet() {
	}

	public StateSet(Set<State> states) {
		this.states = states;
	}

	/* Setters/Getters */
	public Set<State> getStates() {
		return states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

	/* Manipulation */
	public boolean add(State state) {
		return this.states.add(state);
	}
	
	public boolean addAll(Set<State> states) {
		return this.states.addAll(states);
	}

	/* Info */
	public boolean contains(State state) {
		return states.contains(state);
	}

	public boolean equals(Object other) {
		if (other instanceof StateSet) {
			StateSet otherSet = (StateSet)other;
			return states.equals(otherSet.getStates());
		} else {
			return false;
		}
	}

	public int hashCode() {
		return states.hashCode();
	}

	// Do any of the states accept?
	public boolean accepts() {
		Iterator<State> iter = states.iterator();
		while (iter.hasNext())
			if (iter.next().getAccepts()) return true;

		return false;
	}

	// What characters cause transitions in this set?
	public Set<Character> transitionCharacters() {
		Set<Character> chars = new HashSet<Character>();
		Iterator<State> iter = states.iterator();
		while (iter.hasNext()) {
			Iterator<Transition> transIter = iter.next().getTransitions().iterator();
			while (transIter.hasNext())
				chars.add(transIter.next().c);
		}

		return chars;
	}

	public Set<State> statesReachableOn(Character on) {
		Set<State> reachable = new HashSet<State>();
		Iterator<State> iter = states.iterator();
		while (iter.hasNext()) {
			State state = iter.next();
			reachable.addAll(state.getNFA().statesReachableOn(state, on));
		}

		return reachable;
	}

	public StateSet transition(Character on) {
		Set<State> reachable = statesReachableOn(on);

		if (reachable.equals(states)) {
			return this;
		} else {
			return new StateSet(reachable);
		}
	}

	public String toString() {
		return states.toString();
	}

	public State toState(Parser parser) {
		State state = new State(toString());
		state.setAccepts(accepts());
		
		int lastIndex = -1;
		
		// Use whichever one appears earliest in token classes
		for(State currState : states){
			if(currState.getAccepts()){
				// Make sure it comes first
				int index = parser.getTokenClasses().indexOf(currState.klass);
				if (lastIndex == -1 ||
						(index < lastIndex && lastIndex != -1)) {
					state.klass = currState.klass;
					lastIndex = index;
				}
			}
		}
		return state;
	}
}
