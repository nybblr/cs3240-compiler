import java.util.HashSet;
import java.util.Set;


public class EpsilonTerminal extends Terminal {
	// Keyword for epsilon variable
	public static final String EPSILON = "epsilon";
	
	private Grammar grammar;

	public EpsilonTerminal(Grammar grammar) {
		//super(grammar, "epsilon");
		this.grammar = grammar;
	}
	
	public Set<Terminal> getFirst() {
		return null;
	}
	
	public String toString() {
		return "<epsilon>";
	}
}
