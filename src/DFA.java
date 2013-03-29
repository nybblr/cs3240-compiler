import java.util.HashMap;
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
		
		HashMap<StateSet, State> map = new HashMap<StateSet, State>();
		
		// First, add start state
		// This is the NFA start state and all reachable states (epsilon transitions)
		StateSet startSet = new StateSet();
		startSet.add(nfa.getStart());
		startSet.states.addAll(nfa.statesReachableFrom(nfa.getStart()));
		
		setStart(startSet.toState());
		
		map.put(startSet, getStart());
		
		Queue<StateSet> queue = new LinkedList<StateSet>();
		queue.offer(startSet);
		
		while(!queue.isEmpty()) {
			StateSet set = queue.poll();
			
			for (int i = 0; i < 256; i++) {
				StateSet trans = set.transition((char)i);
				State to = null;
				if (!map.containsKey(trans)) {
					to = trans.toState();
					map.put(trans, to);
				} else {
					to = map.get(trans);
				}
				
				State from = map.get(set);
				addTransition(from, (char)i, to);
			}
		}
	}

	/* Export */
	public void toTable() {
		// Convert to some kind of table.
	}
}
