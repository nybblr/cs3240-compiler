import java.util.List;


public class TerminalStream {
	private List<Token> tokens;
	private int pointer;
	
	// Constructors
	public TerminalStream(List<Token> tokens) {
		this.tokens = tokens;
		this.pointer = 0;
	}

	// Setters/getters
	public List<Token> getTokens() {
		return tokens;
	}

	public int getPointer() {
		return pointer;
	}
	
	// Manipulation
	public Token peekToken() {
		if (isConsumed()) return null;
		return tokens.get(pointer);
	}

	// Peek token at pointer and return if it matches
	public boolean peekToken(TokenClass klass) {
		return klass.equals(peekToken().getKlass());
	}

	// Get character at current pointer plus offset without advancing
	public Token peekToken(int i) {
		if (isConsumed()) return null;
		return tokens.get(pointer + i);
	}
	
	// Consume character at current pointer
	// Returns if we matched
	public boolean matchToken(TokenClass klass) {
		// Make sure the token matches
		if (!klass.equals(peekToken().getKlass())) return false;

		// Advance pointer
		advancePointer();
		return true;
	}
	
	// Move pointer forward
	public int advancePointer() {
		if (isConsumed()) return -1;
		return ++pointer;
	}

	// Move pointer forward over multitoken
	public int advancePointer(int n) {
		if (!isEnough(n)) return -1;
		pointer += n;
		return pointer;
	}
	
	/* Utility */
	// Have we already matched all the tokens in the string?
	public boolean isConsumed() {
		return isConsumed(0);
	}
	
	// Is some i from now beyond consumption?
	public boolean isConsumed(int i) {
		return pointer+i >= tokens.size();
	}

	// Do we have enough characters in the string to match?
	public boolean isEnough(int tokenLength) {
		return pointer + tokenLength <= tokens.size();
	}
	
	public String toString() {
		String s = "";
		
		int offset = 0;
		int length = 0;
		for (int i = 0; i < tokens.size(); i++) {
			String ts = tokens.get(i).toString() + " ";
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
