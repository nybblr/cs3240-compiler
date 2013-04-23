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
	private static final char VAR_START = '<';
	private static final char VAR_END = '>';
	private static final char NEW_RULE = '|';
	private static final char ASSIGN_START = ':';
	private static final String ASSIGN = "::=";
	
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
	            	is.matchToken(VAR_START);
	            	
	            	String varName = is.peekTill(VAR_END);
	            	
	            	// Do variable instantiation stuff here
	            	Variable var = new Variable(this, varName);
	            	if (map.containsKey(var)) {
	            		// Variable already instantiated; use it
	            		Variable varMatch = null;
	            		for (Variable v : map.keySet()) {
	            			if (v.equals(var)) {
	            				varMatch = v;
	            			}
	            		}
	            		
	            		// Ditch the other one
	            		var = varMatch;
	            	} else {
	            		// Not made; add it to map
	            		map.put(var, new HashSet<Rule>());
	            	}
	            	
	            	// See if we are working on rules
	            	if (currVar == null) {
	            		// First variable on line
	            		currVar = var;
	            	} else if (currRule != null) {
	            		// Working on rule; add
	            		currRule.addItem(var);
	            	}
	            	
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
	            	String string = is.peekTillSpace();
	            	TokenClass klass = parser.getTokenClass(string);
	            	
	            	if (klass != null) {
	            		// It's a valid TokenClass, like BEGIN
	            		// Add to current rule
	            		currRule.addItem(klass);
	            	} else {
	            		// See if any token classes match
	            		Token token = parser.scanToken(string);
	            		
	            		if (token != null) {
	            			// It's a valid string from TokenClass, like begin
	            			// Add to current rule
	            			currRule.addItem(token.getKlass());
	            			
	            			// Consume the text that matched
	            			is.matchToken(token.getString());
	            		} else {
	            			// That didn't work either, invalid!
	            			invalid();
	            		}
	            	}
	            	
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
