import java.util.HashSet;
import java.util.Set;


public class Variable extends RuleItem {
	private String label;
	// First set
	private Set<TokenClass> first = new HashSet<TokenClass>();
	// Follow set
	private Set<TokenClass> follow = new HashSet<TokenClass>();

	// Constructors
	public Variable(String label) {
		super();
		this.label = label;
	}

	// Getters/setters
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Set<TokenClass> getFirst() {
		return first;
	}

	public Set<TokenClass> getFollow() {
		return follow;
	}
	
	// First/follow sets
	public void addToFirst(TokenClass klass) {
		first.add(klass);
	}
	
	public void addAllToFirst(Set<TokenClass> klasses) {
		first.addAll(klasses);
	}
	
	public void addToFollow(TokenClass klass) {
		follow.add(klass);
	}
	
	public void addAllToFollow(Set<TokenClass> klasses) {
		follow.addAll(klasses);
	}
	
	// Utility
	public String toString() {
		return label;
	}
}
