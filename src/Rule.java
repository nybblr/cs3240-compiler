import java.util.LinkedList;
import java.util.List;


public class Rule {
	// Parent grammar
	private Grammar grammar;
	// Variable this rule substitutes
	private Variable variable;
	// Ordered list of the token classes/variables
	// Can't be an ordered set because a Rule Item may appear multiple times
	private List<RuleItem> items;
	
	// Constructors
	public Rule(Grammar grammar, Variable variable) {
		super();
		this.grammar = grammar;
		this.variable = variable;
		this.items = new LinkedList<RuleItem>();
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
	public List<RuleItem> getItems() {
		return items;
	}
	
	// Manipulation
	public void addItem(RuleItem item) {
		// Add item to end
		items.add(item);
	}
	
	// Utility
	public String toString() {
		// Print out pretty rule here
		return variable.toString()+" => "+toVarlessString();
	}
	
	public String toVarlessString() {
		// Print out pretty rule here
		return items.toString();
	}
}
