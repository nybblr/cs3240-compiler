public class Transition {
	public State from;
	public State to;
	public Character c;
	
	/* Constructors */
	public Transition(State from, Character on, State to) {
		this.from = from;
		this.to = to;
		this.c = on;
	}
}
