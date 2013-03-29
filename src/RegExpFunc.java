import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Arrays;

/**
 *
 */

/**
 * @author Rochelle
 *
 */
public class RegExpFunc {
	private static final String SPACE = " ", BSLASH = "/", MULTI = "*",
			PLUS = "+", OR = "|", LBRAC = "[", RBRAC = "]", LPAREN = "(",
			RPAREN = ")", PERIOD = ".", APOS = "'", QUOT = "\"", UNION = "UNION",
			IN = "IN", NOT = "^", DASH = "-", DOLLAR = "$";

	private static final Character ESCAPE = new Character('\\');

	private static final Character[] EX_RE_CHAR = {
		' ', '\\', '*', '+', '?', '|', '[', ']', '(', ')', '.', '\'', '"', '$'
	};
	private static final Character[] EX_CLS_CHAR = {
		'\\', '^', '-', '[', ']', '$'
	};

	private static final HashSet<Character> RE_CHAR = new HashSet<Character>(Arrays.asList(EX_RE_CHAR));
	private static final HashSet<Character> CLS_CHAR = new HashSet<Character>(Arrays.asList(EX_CLS_CHAR));

	private ArrayList<Terminals> classes = Parser.getClasses();

	private String input;
	private InputStream is;

	/* Constructor */
	public RegExpFunc(String input) {
		this.input = new String(input);
		this.is = new InputStream(this.input);
		//origRegExp();
	}

	private boolean matchToken(String token) {
		//return is.matchToken(token.charAt(0));
		return is.matchToken(token);
	}

	private String peekToken() {
		if (is.peekToken() == null) return null;
		String s = String.valueOf(is.peekToken());
		System.out.println("Current regex char: " + s);
		return s;
	}

	private boolean peekToken(String token) {
		//return peekToken().equals(token);
		return is.peekToken(token);
	}

	private boolean peekEscaped(HashSet<Character> escaped) {
		debug();
		if (is.isConsumed()) return false;

		// Escaped character?
		if (is.peekToken(ESCAPE)) {
			return escaped.contains(is.peekToken(1));
		} else {
			return !escaped.contains(is.peekToken());
		}
	}

	private Character matchEscaped(HashSet<Character> escaped) {
		if (is.isConsumed()) return null;

		// Escaped character?
		Character token;
		if (is.peekToken(ESCAPE)) {
			token = is.peekToken(1);
			is.advancePointer(2);
		} else {
			token = is.peekToken();
			is.advancePointer();
		}

		return token;
	}

	private boolean peekReToken() {
		return peekEscaped(RE_CHAR);
	}

	private boolean peekClsToken() {
		return peekEscaped(CLS_CHAR);
	}

	private Character matchReToken() {
		return matchEscaped(CLS_CHAR);
	}

	private Character matchClsToken() {
		return matchEscaped(CLS_CHAR);
	}

	private void invalid() {
		System.out.println("Invalid Syntax");
		System.exit(0);
	}

	private void debug() {
		System.out.println("Pointer at: "+peekToken());
		System.out.println(Thread.currentThread().getStackTrace()[2]);
	}

	private boolean inSpecification() {
		String s="", t = peekToken();
		if(t == null)
			return false;
		for(int i=0; i<classes.size(); i++){
			HashSet<Character> chars = classes.get(i).getChars();
			Iterator<Character> iter = chars.iterator();
			while(iter.hasNext() && !(s.equals(t))){
				s = iter.next().toString();
				//System.out.println("Comparing: " + s + "to" + t);
				if(peekToken(s)){
					return true;
				}
			}
		}
		return false;
	}

	private void definedClass() {
		debug();
		
		for(int i=0; i<classes.size(); i++){
			String name = classes.get(i).getName();
			if (is.peekToken(name)) {
				is.matchToken(name);
			}
//			Iterator<Character> iter = chars.iterator();
//			while(iter.hasNext() && !(s.equals(t))){
//				s = iter.next().toString();
//				//System.out.println("Comparing: " + s + "to" + t);
//				if(peekToken(s)){
//					matchToken(peekToken());
//				}
//			}
		}
	}

	public NFA origRegExp() {
		debug();
		return regExp();
	}

	public NFA regExp() {
		debug();
		NFA nfa = regExOne();
		if(peekToken(OR)){
			regExPrime();
		}
		return null;
	}

	public NFA regExPrime() {
		debug();
		if(peekToken(OR)) {
			matchToken(OR);
			regExOne();
			regExPrime();
		}
		else {
			return null;
		}
		return null;
	}

	public NFA regExOne() {
		debug();
		regExTwo();
		regExOnePrime();
		return null;
	}

	public NFA regExOnePrime() {
		debug();
		if(peekToken(LPAREN)) {
			regExTwo();
			regExOnePrime();
		}
		else if(peekReToken()) {
			regExTwo();
			regExOnePrime();
		}
		else if(peekToken(PERIOD)) {
			regExTwo();
			regExOnePrime();
		}
		else if(peekToken(LBRAC)) {
			regExTwo();
			regExOnePrime();
		}
		else if(inSpecification()) {
			
			regExTwo();
			regExOnePrime();
		}
		else {
			return null;
		}
		return null;
	}

	public NFA regExTwo() {
		debug();
		if(peekToken(LPAREN)) {
			matchToken(LPAREN);
			regExp();
			if(peekToken(RPAREN)) {
				matchToken(RPAREN);
				regExTwoTail();
			}
			else {
				invalid();
			}
		}
		else if(peekReToken()) {
			matchReToken();
			regExTwoTail();
		}
		else {
			if(peekToken(PERIOD)) {
				regExThree();
			}
			else if(peekToken(LBRAC)) {
				regExThree();
			}
			else if(inSpecification()) {
				regExThree();
			}
		}
		return null;
	}

	public NFA regExTwoTail() {
		debug();
		if(peekToken(MULTI)) {
			matchToken(MULTI);
		}
		else if(peekToken(PLUS)) {
			matchToken(PLUS);
		}
		else {
			return null;
		}
		return null;
	}

	public NFA regExThree() {
		debug();
		if(peekToken(PERIOD)) {
			charClass();
		}
		else if(peekToken(LBRAC)) {
			charClass();
		}
		else if(inSpecification()) {
			charClass();
		}
		else if(peekToken("null")) {
			return null;
		}
		else {
			invalid();
		}
		return null;
	}

	public NFA charClass() {
		debug();
		if(peekToken(PERIOD)) {
			matchToken(PERIOD);
		}
		else if(peekToken(LBRAC)) {
			matchToken(LBRAC);
			charClassOne();
		}
		else {
			matchToken(DOLLAR);
			System.out.println("I'm in char class!!!");
			definedClass();
		}
		return null;
	}

	public NFA charClassOne() {
		debug();
		if(peekClsToken()) {
			charSetList();
		}
		else if(peekToken(RBRAC)) {
			charSetList();
		}
		else {
			excludeSet();
		}
		return null;
	}

	public NFA charSetList() {
		debug();
		if(peekClsToken()) {
			charSet();
			charSetList();
		}
		else if(peekToken(RBRAC)) {
			matchToken(RBRAC);
		}
		else{
			invalid();
		}
		return null;
	}

	public NFA charSet() {
		debug();
		System.out.println(peekToken());
		System.out.println(is.getPointer());
		if(peekClsToken()) {
			matchClsToken();
			charSetTail();
		}
		return null;
	}

	public NFA charSetTail() {
		debug();
		if(peekToken(DASH)) {
			System.out.println("We're there!");
			matchToken(DASH);

			// ???????? What should this be?????
			//clsChar();
			matchClsToken();
		}
		else{
			return null;
		}
		return null;
	}

	public NFA excludeSet() {
		debug();
		if(peekToken(NOT)) {
			matchToken(NOT);
			charSet();
			if(peekToken(RBRAC)) {
				System.out.println("We're at the ]!");
				matchToken(RBRAC);
				if(peekToken(IN)) {
					matchToken(IN);
					excludeSetTail();
				} else
					invalid();
			}
			else {
				invalid();
			}
		}
		else {
			invalid();
		}
		return null;
	}

	public NFA excludeSetTail() {
		debug();
		if(peekToken(LBRAC)) {
			matchToken(LBRAC);
			charSet();
			if(peekToken(RBRAC)) {
				matchToken(RBRAC);
			}
		}
		else{
			System.out.println("I'm here!!!");
			matchToken(DOLLAR);
			definedClass();
		}
		return null;
	}
}
