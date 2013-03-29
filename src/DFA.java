import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

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
		
		Queue<StateSet> queue = new LinkedList<StateSet>();
		queue.offer(start);
		
		while(!queue.isEmpty()) {
			StateSet set = queue.poll();
			
			
		}
	}

	/* Export */
	public void toTable() {
		// Convert to some kind of table.
	}
}
