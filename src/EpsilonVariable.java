import java.util.HashSet;
import java.util.Set;


public class EpsilonVariable extends Variable {
	// Keyword for epsilon variable
	public static final String EPSILON = "epsilon";

	public EpsilonVariable(Grammar grammar) {
		super(grammar, "epsilon");
	}
}
