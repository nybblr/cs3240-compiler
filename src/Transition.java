public class Transition implements Cloneable{
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

	public boolean equals(Object other) {
		if (other instanceof Transition) {
			Transition trans = (Transition)other;
			if (trans == this) return true;
			return from.equals(trans.from) &&
					to.equals(trans.to) &&
					(c == trans.c || c.equals(trans.c));
		} else {
			return false;
		}
	}

	public int hashCode() {
		int code = from.hashCode() + to.hashCode();
		if (c != EPSILON) code += c.hashCode();
		return code;
	}

	public boolean isEmptyTransition() {
		return c == EPSILON;
	}

	public String toString() {
		return ""+from+"=>'"+Helpers.niceCharToString(c)+"'=>"+to;
	}
}
