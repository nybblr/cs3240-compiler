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
	            matchToken(OR);
	            nfa = NFA.union(regExPrime(), nfa);
		}
		return nfa;
	}

	public NFA regExPrime() {
		debug();
		if(peekToken(OR)) {
		    matchToken(OR);
	            NFA nfa = regExOne();
	            nfa = NFA.concat(regExPrime(), nfa);
	            return nfa;
		}
		else {
		    State s = new State();
	            State a = new State();
	            NFA nfa = new NFA(s);
	            nfa.addEpsilonTransition(nfa.getStart(), a);
	            nfa.setAccepts(a, true);
	            return nfa;
		}
	}

	public NFA regExOne() {
		debug();
		NFA nfa = regExTwo();
	        nfa = NFA.concat(nfa, regExOnePrime());
	        return nfa;
	}

	public NFA regExOnePrime() {
		debug();
		State s = new State();
		NFA nfa = new NFA(s);
		if(peekToken(LPAREN) || peekToken(PERIOD) || peekToken(LBRAC) || peekToken(DOLLAR)){
		    nfa.addEpsilonTransition(s, regExTwo().getStart());
                    NFA.concat(nfa, regExOnePrime());
                    return nfa;
		}
		else if(peekReToken()) {
	            nfa.addEpsilonTransition(s, regExTwo().getStart());
	            NFA.concat(nfa, regExOnePrime());
	            return nfa;
	        }
	        else {
	            State a = new State();
	            nfa.addEpsilonTransition(nfa.getStart(), a);
	            nfa.setAccepts(a, true);
	            return nfa;
	        }
	}

	public NFA regExTwo() {
		debug();
		State s = new State();
		NFA nfa = new NFA(s);
//***********************************Double check the if for minor bug issues***********************************
		if(peekToken(LPAREN)) {
		      matchToken(LPAREN);
		      NFA rexpNFA = regExp();
		      nfa.addTransition(s, '(', rexpNFA.getStart());
		      if(peekToken(RPAREN)) {
		          matchToken(RPAREN);
		          State t = new State();
		          nfa.addTransition(t, ')', regExTwoTail().getStart());
		          NFA.concat(rexpNFA, nfa);
		          return nfa;
		      }
		      else {
		          invalid();
		          return null;
		      }
		}
		else if(peekReToken()) {
			nfa.addTransition(s, matchReToken(), regExTwoTail().getStart());
			return nfa;
		}
		else {
			if(peekToken(PERIOD) || peekToken(LBRAC) || peekToken(DOLLAR)){
			    nfa.addEpsilonTransition(s, regExThree().getStart());
			    return nfa;
			}
			else {
			    invalid();
			    return null;
			}
		}
	}

	public NFA regExTwoTail() {
		debug();
		if(peekToken(MULTI)) {
		    matchToken(MULTI);
		    State s = new State();
	            State a = new State();
	            NFA nfa = new NFA(s);
	            nfa.addTransition(s, '*', a);
	            return nfa;
		}
		else if(peekToken(PLUS)) {
		    matchToken(PLUS);
	            State s = new State();
	            State a = new State();
	            NFA nfa = new NFA(s);
	            nfa.addTransition(s, '+', a);
	            return nfa;
		}
		else {
		    State s = new State();
	            State a = new State();
	            NFA nfa = new NFA(s);
	            nfa.addEpsilonTransition(nfa.getStart(), a);
	            nfa.setAccepts(a, true);
	            return nfa;
		}
	}

	public NFA regExThree() {
		debug();
		State s = new State();
		NFA nfa = new NFA(s);
		if(peekToken(PERIOD) || peekToken(LBRAC) || peekToken(DOLLAR)) {
		    nfa.addEpsilonTransition(s, charClass().getStart());
		    return nfa;
		}
		else if(peekToken("null")) {
                    State a = new State();
                    nfa.addEpsilonTransition(s, a);
                    return nfa;
		}
		else {
			invalid();
			return null;
		}
	}

	public NFA charClass() {
		debug();
		State s = new State();
		NFA nfa = new NFA(s);
		if(peekToken(PERIOD)) {
			matchToken(PERIOD);
	                State a = new State();
	                nfa.addTransition(s, '.', a);
	                return nfa;
		}
		else if(peekToken(LBRAC)) {
			matchToken(LBRAC);
			nfa.addTransition(s, '[', charClassOne().getStart());
			return nfa;
		}
//******************************Not sure what to do for transition********************************************
		else if(peekToken(DOLLAR)){
			matchToken(DOLLAR);
	                State a = new State();
	                nfa.addTransition(s, '$', a);
	                nfa.setAccepts(a, true);
			System.out.println("I'm in char class!!!");
			definedClass();
			return null;
		}
		else{
		    invalid();
		    return null;
		}
	}

	public NFA charClassOne() {
		debug();
		State s = new State();
		NFA nfa = new NFA(s);
		//Not sure what to do for transition
		if(peekClsToken()) {
		    nfa.addEpsilonTransition(s,charSetList().getStart());
			return null;
		}
		else if(peekToken(RBRAC) || peekToken(NOT)) {
		    nfa.addEpsilonTransition(s, excludeSet().getStart());
		    return nfa;
		}
		else {
		    invalid();
		    return null;
		}
	}

	public NFA charSetList() {
		debug();
		State s = new State();
		NFA nfa = new NFA(s);
		if(peekClsToken()) {
		    nfa.addEpsilonTransition(s, charSet().getStart());
		    NFA.concat(nfa, charSetList());
                    return nfa;
		}
		else if(peekToken(RBRAC)) {
		    matchToken(RBRAC);
	            State a = new State();
	            nfa.addTransition(s, ']', a);
	            return nfa;
		}
		else{
			invalid();
			return null;
		}
	}

	public NFA charSet() {
		debug();
		System.out.println(peekToken());
		System.out.println(is.getPointer());
		State s = new State();
		NFA nfa = new NFA(s);
		if(peekClsToken()) {
		    nfa.addTransition(s, matchClsToken(),charSetTail().getStart());
                    return nfa;
		}
		else {
		    invalid();
                    return null;
		}
	}

	public NFA charSetTail() {
		debug();
		State s = new State();
		NFA nfa = new NFA(s);
		if(peekToken(DASH)) {
			System.out.println("We're there!");
			matchToken(DASH);
			State t = new State();
			nfa.addTransition(s, '-', t);
			if(peekClsToken()) {
			    nfa.addTransition(t, matchClsToken(), new State());
			    return nfa;
			}
			else {
			    invalid();
			    return null;
			}
		}
		else{
                    State a = new State();
                    nfa.addEpsilonTransition(s, a);
                    return nfa;
		}
	}
/** --------------------------------------------------------------------------------------------------*/
	//Special case with IN exclusion so make sure to pay detailed attention to this part
	public NFA excludeSet() {
		debug();
		State s = new State();
		NFA nfa = new NFA(s);
//***********************************Double check the if for minor bug issues***********************************
		if(peekToken(NOT)) {
			matchToken(NOT);
			nfa.addTransition(s, '^', charSet().getStart());
			if(peekToken(RBRAC)) {
				System.out.println("We're at the ]!");
				matchToken(RBRAC);
				State t = new State();
				State u = new State();
				NFA nfa1 = new NFA(t);
				nfa1.addTransition(t, ']', u);
				NFA.concat(nfa, nfa1);
				if(peekToken(SPACE)){
				    matchToken(SPACE);
				    State v = new State();
	                            State w = new State();
	                            NFA nfa2 = new NFA(v);
	                            nfa1.addTransition(v, ']', w);
	                            NFA.concat(nfa1, nfa2);
				    if(peekToken(IN)) {
                                        matchToken(IN);
                                        State x = new State();
                                        State y = new State();
                                        NFA nfa3 = new NFA(x);
                                        nfa1.addTransition(x, ']', y);
                                        NFA.concat(nfa2, nfa3);
                                            if(peekToken(SPACE)){
                                                matchToken(SPACE);
                                                State z = new State();
                                                NFA nfa4 = new NFA(z);
                                                nfa1.addTransition(z, ']', excludeSetTail().getStart());
                                                NFA.concat(nfa3, nfa4);
                                            }
                                            else{
                                                invalid();
                                                return null;
                                            }
                                    }
				    else{
                                        invalid();
				        return null;
				    }
				}
				else{
                                    invalid();
                                    return null;
                                }
			}
			else {
				invalid();
				return null;
			}
			return nfa;
		}
		else {
			invalid();
			return null;
		}
	}

	public NFA excludeSetTail() {
		debug();
		State s = new State();
		NFA nfa = new NFA(s);
		if(peekToken(LBRAC)) {
			matchToken(LBRAC);
			nfa.addTransition(s, '[', charSet().getStart());
			if(peekToken(RBRAC)) {
				matchToken(RBRAC);
				State t = new State();
				State u = new State();
				NFA nfa1 = new NFA(t);
				nfa1.addTransition(t, ']', u);
				nfa.setAccepts(u, true);
			}
		}
//***********************************Not sure what to do for transition***********************************
		else{
			System.out.println("I'm here!!!");
			matchToken(DOLLAR);
			definedClass();
		}
		return null;
	}
}
