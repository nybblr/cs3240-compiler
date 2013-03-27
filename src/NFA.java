import java.util.HashSet;
import java.util.Iterator;

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

	public State step(State from, Character on) {
		return from;
	}
	
	// Which states can be reached on a certain character?
	public HashSet<State> statesReachableOn(State from, Character on) {
		HashSet<State> reachable = new HashSet<State>();
		Iterator<Transition> iter = from.getTransitions().iterator();
		while (iter.hasNext()) {
			Transition t = iter.next();
			
			// Do they want the epsilon transitions?
			boolean equals = false;
			if (on == null)
				equals = on == t.c;
			else
				equals = on.equals(t.c);
			
			// Add all matching transitions
			// Skip if already added to prevent loops
			if (equals  && !reachable.contains(t.to)) {
				// Add this state
				reachable.add(t.to);
				
				// Recurse and add all reachable from this state (epsilon transitions)
				reachable.addAll(statesReachableFrom(t.to));
			}
		}
		
		return reachable;
	}

	// Which states can be reached via empty string?
	public HashSet<State> statesReachableFrom(State from) {
		return statesReachableOn(from, null);
//		HashSet<State> reachable = new HashSet<State>();
//		Iterator<Transition> iter = from.getTransitions().iterator();
//		while (iter.hasNext()) {
//			Transition t = iter.next();
//			
//			// Add all empty transitions
//			// Skip if already added to prevent loops
//			if (t.isEmptyTransition() && !reachable.contains(t.to)) {
//				// Add this state
//				reachable.add(t.to);
//				
//				// Recurse and add all reachable from this state
//				reachable.addAll(statesReachableFrom(t.to));
//			}
//		}
//		
//		return reachable;
	}

	/* Manipulation */
	public Boolean addState(State state) {
		this.states.add(state);
		return false;
	}
	
	public Boolean addTransition(State from, Character on, State to) {
		Transition transition = new Transition(from, on, to);
		this.transitions.add(transition);
		from.addTransition(on, to);
		
		return false;
	}

	/* Export */
	public void toTable() {
		// Should return some kind of transition table
		toDFA().toTable();
	}

	public DFA toDFA() {
		return new DFA(this);
	}
}
