import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;


public class TestNFAtoDFA {

	@Test
	public void test() throws UnsupportedEncodingException {
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
		
		Parser.main(null);
		
		ArrayList<Terminals> classes = Parser.getClasses();
		
		Terminals klass = null;
		String klassName = "CONSTANT";
		
		for (Terminals t : classes) {
			if (t.getName().equals(klassName)) {
				klass = t;
				break;
			}
		}
		
		State[][] table = klass.getDFA().toTable();
		String string = klass.getDFA().tableToString(table);
		System.out.println(string);
		
		
		// Now try table walking!
		String input = "Algebra2";
		System.out.println("Input: "+input);
		boolean accepts = DFA.walkTable(input, table);
		
		System.out.println('"'+input+'"'+" is"+((accepts) ? "" : " NOT")+" a valid "+klass.getName());
	}

}
