import java.util.HashSet;
import java.util.Set;


public class TokenClass extends Terminal {
	private HashSet<Character> chars = new HashSet<Character>();
	private DFA dfa;
	private NFA nfa;
	private String name;

	/* Constructor */

	/* Setters and getters */
	public HashSet<Character> getChars() {
		return chars;
	}

	public void setChars(HashSet<Character> chars) {
		this.chars = chars;
	}
	public void addChar(char c) {
		chars.add(c);
	}
	public void removeChar(char c) {
		chars.remove(c);
	}
	public boolean containsChar(char c) {
		return chars.contains(c);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* Generators */
	public DFA getDFA() {
		return dfa;
	}

	public void setDFA(DFA dfa) {
		this.dfa = dfa;
	}

	public NFA getNFA() {
		return nfa;
	}

	public void setNFA(NFA nfa) {
		this.nfa = nfa;
	}

	public String toString() {
		return "$"+name;
	}
	
	public Set<Terminal> getFirst() {
		Set<Terminal> first = new HashSet<Terminal>();
		first.add(this);
		return first;
	}
	public Set<Terminal> getFollow() {
		Set<Terminal> follow = new HashSet<Terminal>();
		follow.add(this);
		return follow;
	}
}
