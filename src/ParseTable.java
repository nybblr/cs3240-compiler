
public class ParseTable {
	private Grammar grammar;
	private Rule[][] table;
	
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
		
	}
}
