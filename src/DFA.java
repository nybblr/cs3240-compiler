public class DFA extends NFA {
	/* Constructors */
	public DFA(State start) {
		super(start);
	}

	public DFA(NFA nfa) {
		// Convert NFA to DFA
		super(new State());
	}

	/* Export */
	public void toTable() {
		// Convert to some kind of table.
	}
}
