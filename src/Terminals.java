import java.util.HashSet;


public class Terminals {
	private HashSet<Character> chars = new HashSet<Character>();
	private DFA dfa;
	private String name;

	/* Constructor */
	
	
	/* Setters and getters */
	public HashSet<Character> getChars() {
		return chars;
	}

	public void setChars(HashSet<Character> chars) {
		this.chars = chars;
	}
	
	/* Generators */
	public DFA getDFA() {
		this.dfa = null;
		return new DFA(new State());
	}
}
