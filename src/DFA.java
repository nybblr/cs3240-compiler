import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

public class DFA extends NFA {
	public ArrayList<State> list = null;
	
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
			
			for (int i = 0; i < 128; i++) {
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
		list = new ArrayList<State>();
		list.addAll(getStates());
		
		// Move start state to front
		list.remove(getStart());
		list.add(getStart());
		
		State[][] table = new State[list.size()][128];
		
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
		
		State currState = start;
		
		if(currState.getAccepts()) lastAccept = start;
		
		while(!is.isConsumed()) {
			char c = is.peekToken().charValue();
			
			int index = states.indexOf(currState);
			currState = table[index][c];
			
			if(currState.getAccepts()) {
				lastAccept = start;
				acceptPointer = is.getPointer();
			}
		}
		
		return false;
	}
	
	public String tableToString(State[][] table, ArrayList<State> states) {
		MultiColumnPrinter tp = new MultiColumnPrinter(table[0].length, 2, "-");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		tp.stream = new PrintStream(baos);
		
		String[] ascii = new String[128];
		for (int i = 0; i < 128; i++) {
			ascii[i] = Helpers.niceCharToString((char)i);
		}
		
		tp.addTitle(ascii);
		
		for (int i = 0; i < table.length; i++) {
			String[] row = new String[table[i].length];
			for (int j = 0; j < row.length; j++) {
				if (table[i][j] == null) {
					row[j] = "~~";
					continue;
				}
				row[j] = table[i][j].toString();
			}
			tp.add(row);
		}
		
		tp.print();
		try {
			return baos.toString("UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
