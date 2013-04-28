
public abstract class Terminal extends RuleItem {
	public boolean isEpsilon() {
		return false;
	}
	
	public boolean isDollar() {
		return false;
	}
	
	public boolean isTerminal() {
		return true;
	}
}
