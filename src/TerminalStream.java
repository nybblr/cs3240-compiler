import java.util.List;


public class TerminalStream {
	private List<Terminal> terminals;
	private int pointer;
	
	// Constructors
	public TerminalStream(List<Terminal> terminals) {
		this.terminals = terminals;
		this.pointer = 0;
	}

	// Setters/getters
	public List<Terminal> getTerminals() {
		return terminals;
	}

	public int getPointer() {
		return pointer;
	}
	
	// Manipulation
	public Terminal peekTerminal() {
		if (isConsumed()) return null;
		return terminals.get(pointer);
	}

	// Peek terminal at pointer and return if it matches
	public boolean peekTerminal(Terminal term) {
		if (term.isEpsilon()) return true;
		if (peekTerminal().isToken()) return term.equals(((Token)peekTerminal()).getKlass());
		return term.equals(peekTerminal());
	}

	// Get character at current pointer plus offset without advancing
	public Terminal peekTerminal(int i) {
		if (isConsumed()) return null;
		return terminals.get(pointer + i);
	}
	
	// Consume character at current pointer
	// Returns if we matched
	public boolean matchTerminal(Terminal term) {
		// Make sure the terminal matches
		if (!peekTerminal(term))
			return false;

		// Advance pointer if not epsilon
		if (!term.isEpsilon()) advancePointer();
		return true;
	}
	
	// Move pointer forward
	public int advancePointer() {
		if (isConsumed()) return -1;
		return ++pointer;
	}

	// Move pointer forward over multiterminal
	public int advancePointer(int n) {
		if (!isEnough(n)) return -1;
		pointer += n;
		return pointer;
	}
	
	/* Utility */
	// Have we already matched all the terminals in the string?
	public boolean isConsumed() {
		return isConsumed(0);
	}
	
	// Is some i from now beyond consumption?
	public boolean isConsumed(int i) {
		return pointer+i >= terminals.size();
	}

	// Do we have enough characters in the string to match?
	public boolean isEnough(int terminalLength) {
		return pointer + terminalLength <= terminals.size();
	}
	
	public String toString() {
		String s = "";
		
		int offset = 0;
		int length = 0;
		for (int i = 0; i < terminals.size(); i++) {
			String ts = terminals.get(i).toCleanString() + " ";
			s += ts;
			if (i < pointer) offset += ts.length();
			else if (i == pointer) length = ts.length() - 1;
		}
		
		s += "\n";
		
		for (int i = 0; i < offset; i++)
			s += " ";
		
		for (int i = 0; i < length; i++)
			s += "^";
		
		return s;
	}
}
