import java.util.HashSet;
import java.util.Set;


public class DollarItem extends Terminal {
	private Grammar grammar;

	public DollarItem(Grammar grammar) {
		//super(grammar, "epsilon");
		this.grammar = grammar;
	}
	
	public Set<Terminal> getFirst() {
		return null;
	}
}
