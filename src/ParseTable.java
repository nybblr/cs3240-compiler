import java.util.ArrayList;
import java.util.List;


public class ParseTable {
	private Grammar grammar;
	private Rule[][] table;
	private List<Variable> varList;
	private List<Terminal> termList;
	
	// Constructors
	public ParseTable(Grammar grammar) {
		this.grammar = grammar;
		constructTable();
	}

	// Getters/setters
	public Grammar getGrammar() {
		return grammar;
	}
	
	public Rule[][] getTable() {
		return table;
	}
	
	public void constructTable() {
		varList = grammar.getOrderedVars();
		termList = new ArrayList<Terminal>(grammar.getParser().getClasses());
		termList.add(new DollarTerminal(grammar));
		
		table = new Rule[varList.size()][termList.size()];
		
		// For each rule
		for (Rule rule : grammar.getRules()) {
			int varI = varList.indexOf(rule.getVariable());
			
			// For each token in First(rule)
			for (Terminal term : rule.getFirst()) {
				int termI = termList.indexOf(term);
				
				if (table[varI][termI] == null) {
					table[varI][termI] = rule;
				} else {
					invalid(varI, termI, rule);
				}
			}
			
			// If epsilon in First(rule)
			if (rule.hasEpsilonInFirst()) {
				// For each token in Follow(rule)
				for (Terminal term : rule.getFollow()) {
					int termI = termList.indexOf(term);
					
					if (table[varI][termI] == null) {
						table[varI][termI] = rule;
					} else {
						invalid(varI, termI, rule);
					}
				}
			}
		}
	}
	
	public void invalid(int var, int term, Rule rule) {
		System.out.println("The grammar is not LL(1)! Entries clashed.");
		System.out.println("Cell ["+var+","+term+"] currently has "+table[var][term]+" but tried to assign "+rule);
		System.exit(0);
	}
}
