import java.util.HashSet;

public class DFA extends NFA {
	/* Constructors */
	public DFA(State start) {
		super(start);
	}

	// Convert NFA to DFA
	public DFA(NFA nfa) {
		super(new State());
		
		// First, add start state
		// This is the NFA start state and all reachable states (epsilon transitions)
		StateSet start = new StateSet();
		start.add(nfa.getStart());
		start.states.addAll(nfa.statesReachableFrom(nfa.getStart()));
	}

	/* Export */
	public void toTable() {
		// Convert to some kind of table.
	}
}
