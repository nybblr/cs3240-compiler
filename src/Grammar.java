import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Grammar {
	// Map from variable to the set of rules
	private Map<Variable,Set<Rule>> map = new HashMap<Variable, Set<Rule>>();
	// Start variable
	private Variable start;
	
	// Special characters for parsing
	private final char VAR_START = '<';
	private final char VAR_END = '>';
	private final char NEW_RULE = '|';
	private final char ASSIGN_START = ':';
	private final String ASSIGN = "::=";
	
	// Constructors
	public Grammar() {
		super();
	}
	
	// Create grammar from files
	public Grammar(Scanner grammar, Scanner spec) {
		super();
		
		// Build scanner first
		Parser parser = new Parser();
		parser.build(spec);

		Variable currVar = null;
		Rule currRule = null;
		
		while(grammar.hasNextLine()){
            InputStream is = new InputStream(grammar.nextLine());
            
            while(!is.isConsumed()) {
	            switch(is.peekToken()) {
	            case VAR_START:
	            	// It's <variable>; make new var (if it doesn't exist in map.keys())
	            	// If the label == Variable.EPSILON, make epsilon variable instead.
	            	// Add to the current rule.
	            	String varName = is.peekTill(VAR_END);
	            	
	            	// Do variable instantiation stuff here
	            	// TODO
	            	
	            	// Consume variable and VAR_END
	            	is.matchToken(varName);
	            	is.matchToken(VAR_END);
	            	
	            	break;
	            case NEW_RULE:
	            	// We found a | so start on the new rule
	            	// Make new rule, assign to map, and start over
	            	currRule = new Rule(this, currVar);
	            	
	            	// Consume symbol
	            	is.matchToken(NEW_RULE);
	            	
	            	break;
	            case ASSIGN_START:
	            	// Could it be ::=? Try it! Same behavior as NEW_RULE.
	            	if (is.peekToken(ASSIGN)) {
	            		currRule = new Rule(this, currVar);
	            		
	            		// Consume string
	            		is.matchToken(ASSIGN);
	            	} else {
	            		invalid();
	            	}
	            	
	            	break;
	            default:
	            	// Could be a TokenClass like BEGIN, or a matching token like 'begin'
	            	// Try matching TokenClass first. As a last resort, try scanToken.
	            	
	            	// TODO
	            	
	            	break;
	            }
	            
	            is.skipWhitespace();
            }
    		
    		// Reset currVariable and currRule.
    		currVar = null;
    		currRule = null;
		}
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
	
	public void invalid() {
		System.out.println("Couldn't parse gramamr spec.");
		System.exit(0);
	}
}
