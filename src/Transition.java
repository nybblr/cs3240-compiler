public class Transition {
	public State from;
	public State to;
	public Character c;
	
	// Null character represents epsilon
	static Character EPSILON = null;
	
	/* Constructors */
	public Transition(State from, Character on, State to) {
		this.from = from;
		this.to = to;
		this.c = on;
	}
	
	public boolean isEmptyTransition() {
		return c == EPSILON;
	}
}
