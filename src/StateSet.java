import java.util.HashSet;
import java.util.Iterator;

public class StateSet {
	public HashSet<State> states = new HashSet<State>();
	
	/* Constructors */
	public StateSet() {
		
	}
	
	public StateSet(HashSet<State> states) {
		this.states = states;
	}
	
	/* Setters/Getters */
	public HashSet<State> getStates() {
		return states;
	}

	public void setStates(HashSet<State> states) {
		this.states = states;
	}
	
	/* Manipulation */
	public void add(State state) {
		states.add(state);
	}

	/* Info */
	public boolean contains(State state) {
		return states.contains(state);
	}
	
	public boolean equals(Object object) {
		return false;
	}
	
	// Do any of the states accept?
	public boolean accepts() {
		Iterator<State> iter = states.iterator();
		while (iter.hasNext())
			if (iter.next().getAccepts()) return true;
		
		return false;
	}
	
	// What characters cause transitions in this set?
	public HashSet<Character> transitionCharacters() {
		HashSet<Character> chars = new HashSet<Character>();
		Iterator<State> iter = states.iterator();
		while (iter.hasNext()) {
			Iterator<Transition> transIter = iter.next().getTransitions().iterator();
			while (transIter.hasNext())
				chars.add(transIter.next().c);
		}
		
		return chars;
	}

	public String toString() {
		String string = "{";
		String delim = ", ";
		Iterator<State> iter = states.iterator();
		while (iter.hasNext()) {
			string += iter.next().toString();
			if (iter.hasNext()) string += delim;
		}
		string += "}";
		return string;
	}
}
