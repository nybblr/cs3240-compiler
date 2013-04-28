import java.util.Set;


public class Token extends Terminal {
	private TokenClass klass;
	private String string;
	
	public Token(TokenClass klass, String string) {
		super();
		this.klass = klass;
		this.string = string;
	}

	public TokenClass getKlass() {
		return klass;
	}

	public void setKlass(TokenClass klass) {
		this.klass = klass;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	
	public String toString() {
		return klass.toString() + ":\"" + string + "\"";
	}
	
	public boolean isToken() {
		return true;
	}

	// Shouldn't ever use these...
	public Set<Terminal> getFirst() {
		return null;
	}

	public Set<Terminal> getFollow() {
		return null;
	}
}
