import java.util.ArrayList;
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
	public State[][] toTable() {
		// Convert to some kind of table.
		
		// First make a linked list so we maintain order
		ArrayList<State> list = new ArrayList<State>();
		list.addAll(getStates());
		
		// Move start state to front
		list.remove(getStart());
		list.add(getStart());
		
		State[][] table = new State[list.size()][256];
		
		for (Transition t : getTransitions()) {
			int index = list.indexOf(t.from);
			table[index][t.c] = t.to;
		}
		
		return table;
	}
	
	public static boolean walkTable(String input, State[][] table, ArrayList<State> states) {
		InputStream is = new InputStream(input);
		
		State start = states.get(0);
		State lastAccept = null;
		int acceptPointer = 0;
		
		while(!is.isConsumed()) {
			
		}
		
		return false;
	}
}
