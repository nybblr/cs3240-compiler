import java.util.Set;


public abstract class RuleItem {
	public abstract Set<Terminal> getFirst();
	public abstract Set<Terminal> getFollow();
	
	public boolean isVariable() {
		return false;
	}
	
	public boolean isTerminal() {
		return false;
	}
}
