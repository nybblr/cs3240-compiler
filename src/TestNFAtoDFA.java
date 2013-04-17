import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class TestNFAtoDFA {

	public static void main(String[] args) throws IOException {
//		NFA nfa = new NFA(new State("Start"));
//		State start = nfa.getStart();
//		
//		State state1 = new State("1");
//		nfa.addState(state1);
//		state1.setAccepts(true);
//		
//		nfa.addTransition(start, 'h', state1);
//		
//		DFA dfa = nfa.toDFA();
//		
//		System.out.println(nfa.toString());
//		System.out.println(dfa.toString());
//		
//		System.out.println(nfa.statesReachableFrom(start));
		
		Parser parser = new Parser();
		parser.buildFromFile("");
		
		ArrayList<TokenClass> classes = parser.getClasses();
		
		TokenClass klass = null;
		String klassName = "FLOAT";
		
		for (TokenClass t : classes) {
			if (t.getName().equals(klassName)) {
				klass = t;
				break;
			}
		}
		
		//if (klass.getNFA() != klass.getNFA().getStart().getNFA()) System.out.println("THAT'S AN ISSUE!");
		DFA dfa = klass.getDFA();
		dfa.friendlyNames();
		State[][] table = dfa.toTable();
		String string = dfa.tableToString(table);
		System.out.println(string);
		
		
		// Now try table walking!
		String input = "3.4";
		System.out.println("Input: "+input);
		boolean accepts = DFA.walkTable(input, table).accepts;
		
		System.out.println('"'+input+'"'+" is"+((accepts) ? "" : " NOT")+" a valid "+klass.getName());
	}

}
