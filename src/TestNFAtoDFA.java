import static org.junit.Assert.*;

import org.junit.Test;


public class TestNFAtoDFA {

	@Test
	public void test() {
		NFA nfa = new NFA(new State("Start"));
		System.out.println(nfa.toString());
	}

}
