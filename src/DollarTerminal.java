import java.util.HashSet;
import java.util.Set;


public class DollarTerminal extends Terminal {
	private Grammar grammar;
	
	private final String DOLLAR = "$";

	public DollarTerminal(Grammar grammar) {
		//super(grammar, "epsilon");
		this.grammar = grammar;
	}
	
	// Getters/setters
	public Grammar getGrammar() {
		return grammar;
	}

	public Set<Terminal> getFirst() {
		return new HashSet<Terminal>();
	}
	
	public Set<Terminal> getFollow() {
		return new HashSet<Terminal>();
	}
	
	public String toString() {
		return DOLLAR;
	}
	
	public boolean isDollar() {
		return true;
	}
	
	// As long as it's the same grammar, they're equal.
	public int hashCode() {
		return DOLLAR.hashCode() + grammar.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof DollarTerminal))
			return false;

		return true;
	}
}
