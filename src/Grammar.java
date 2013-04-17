import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Grammar {
	// Map from variable to the set of rules
	private Map<Variable,Set<Rule>> map = new HashMap<Variable, Set<Rule>>();
	// Start variable
	private Variable start;
	
	// Constructors
	public Grammar() {
		super();
	}

	// Getters/setters
	public Variable getStart() {
		return start;
	}

	public void setStart(Variable start) {
		this.start = start;
	}
	
	public Set<Variable> getVariables() {
		return map.keySet();
	}
	
	// Traversal
	public Set<Rule> getRulesFor(Variable v) {
		return map.get(v);
	}
	
	// Manipulation
	public void addRule(Rule r) {
		r.setGrammar(this);
		
		// Does the map already have the variable?
		if (map.containsKey(r.getVariable())) {
			map.get(r.getVariable()).add(r);
		} else {
			Set<Rule> rules = new HashSet<Rule>();
			rules.add(r);
			map.put(r.getVariable(), rules);
		}
	}
	
	// Utility
	public String toString() {
		// Print out pretty grammar here
		return "";
	}
}
