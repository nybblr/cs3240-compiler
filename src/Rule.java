import java.util.LinkedHashSet;


public class Rule extends RuleItem {
	// Parent grammar
	private Grammar grammar;
	// Variable this rule substitutes
	private Variable variable;
	// Ordered set of the token classes/variables
	private LinkedHashSet<RuleItem> items;
	
	// Constructors
	public Rule(Grammar grammar, Variable variable) {
		super();
		this.grammar = grammar;
		this.variable = variable;
	}
	
	// Getters/setters
	public Grammar getGrammar() {
		return grammar;
	}
	public void setGrammar(Grammar grammar) {
		this.grammar = grammar;
	}
	public Variable getVariable() {
		return variable;
	}
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	public LinkedHashSet<RuleItem> getItems() {
		return items;
	}
	
	// Utility
	public String toString() {
		// Print out pretty rule here
		return "";
	}
}
