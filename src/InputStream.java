public class InputStream {
	private String input;
	private int pointer = 0; // index of character peekToken will see

	/* Constructors */
	public InputStream(String input) {
		this.input = input;
	}

	/* Setter/Getters */
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public int getPointer() {
		return pointer;
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}

	/* Navigation */
	// Get character at current pointer without advancing
	public Character peekToken() {
		if (isConsumed()) return null;
		return input.charAt(pointer);
	}

	// Peek token at pointer and return if it matches
	public boolean peekToken(Character c) {
		return c.equals(peekToken());
	}

	// Get character at current pointer plus offset without advancing
	public Character peekToken(int i) {
		if (isConsumed()) return null;
		return input.charAt(pointer + i);
	}

	// Peek multibyte sequences
	public boolean peekToken(String token) {
		if (!isEnough(token.length())) return false;
		return input.substring(pointer, pointer+token.length()).equals(token);
	}

	// Consume character at current pointer
	// Returns if we matched
	public boolean matchToken(Character token) {
		// Make sure the token matches
		if (!token.equals(peekToken())) return false;

		// Advance pointer
		advancePointer();
		return true;
	}

	// Match multibyte
	public boolean matchToken(String token) {
		// Make sure the token matches
		if (!peekToken(token)) return false;

		// Advance pointer
		advancePointer(token.length());
		return true;
	}

	// Move pointer forward
	public int advancePointer() {
		if (isConsumed()) return -1;
		return ++pointer;
	}

	// Move pointer forward over multibyte
	public int advancePointer(int n) {
		if (!isEnough(n)) return -1;
		pointer += n;
		return pointer;
	}

	// Skip over whitespace
	public int skipWhitespace() {
		int i = 0;
		while (!isConsumed() && Character.isWhitespace(peekToken())) {
			advancePointer();
			i++;
		}

		return i;
	}

	public void resetPointer() {
		pointer = 0;
	}

	/* Utility */
	// Have we already matched all the tokens in the string?
	public boolean isConsumed() {
		return pointer >= input.length();
	}

	// Do we have enough characters in the string to match?
	public boolean isEnough(int tokenLength) {
		return pointer + tokenLength <= input.length();
	}
}
