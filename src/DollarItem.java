import java.util.HashSet;
import java.util.Set;


public class DollarItem extends Terminal {
	private Grammar grammar;

	public DollarItem(Grammar grammar) {
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
		return "$";
	}
}
