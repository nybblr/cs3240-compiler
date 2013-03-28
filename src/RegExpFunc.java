import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 */

/**
 * @author Rochelle
 *
 */
public class RegExpFunc {
    private String SPACE = " ", BSLASH = "/", MULTI = "*",
	PLUS = "+", OR = "|", LBRAC = "[", RBRAC = "]", LPAREN = "(",
	RPAREN = ")", PERIOD = ".", APOS = "'", QUOT = "\"", UNION = "UNION",
	IN = "IN", NOT = "^", DASH = "-";
    
    private HashSet<Character> CLS_CHAR = new HashSet<Character>();
    private HashSet<Character> RE_CHAR = new HashSet<Character>();
    
    private ArrayList<Terminals> classes = Parser.getClasses();

    private String input;
    private InputStream is;

    /* Constructor */
    public RegExpFunc(String input) {
	this.input = new String(input);
	this.is = new InputStream(this.input);
//	origRegExp();
    }

    private boolean matchToken(String token) {
        //return is.matchToken(token.charAt(0));
        return is.matchToken(token);
    }

    private String peekToken() {
	String s = String.valueOf(is.peekToken());
	System.out.println("Current regex char: " + s);
	return s;
    }

    private boolean peekToken(String token) {
	//return peekToken().equals(token);
	return is.peekToken(token);
    }

    private boolean peekReToken() {
	// TODO Auto-generated method stub
    }

    private boolean peekClsToken() {
	// TODO Auto-generated method stub
    	
    }
    
    private boolean matchReToken() {
    	// TODO Auto-generated method stub
    }

    private boolean matchClsToken() {
	// TODO Auto-generated method stub
    	
    }

    private void invalid() {
	System.out.println("Invalid Syntax");
	System.exit(0);
    }

    private boolean inSpecification() {
	String s="", t = peekToken();
	if(t.equals("null"))
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
	String s="",t=peekToken();
	if(t == null)
	    return;
	for(int i=0; i<classes.size(); i++){
	    HashSet<Character> chars = classes.get(i).getChars();
	    Iterator<Character> iter = chars.iterator();
	    while(iter.hasNext() && !(s.equals(t))){
		s = iter.next().toString();
		//System.out.println("Comparing: " + s + "to" + t);
		if(peekToken(s)){
		    matchToken(peekToken());
		}
	    }
	}
    }

    public NFA origRegExp() {
	return regExp();
	
    }

    public NFA regExp() {
	NFA nfa = regExOne();
	if(peekToken(OR)){
	    regExPrime();
	}
        return null;
    }

    public NFA regExPrime() {
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
	regExTwo();
	regExOnePrime();
	return null;
    }

    public NFA regExOnePrime() {
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
	if(peekToken(PERIOD)) {
	    matchToken(PERIOD);
	}
	else if(peekToken(LBRAC)) {
	    matchToken(LBRAC);
	    charClassOne();
	}
	else {
	    definedClass();
	}
	return null;
    }

    public NFA charClassOne() {
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
	if(peekClsToken()) {
	    matchClsToken();
	    charSetTail();
	}
	return null;
    }

    public NFA charSetTail() {
        if(peekToken(DASH)) {
            matchToken(DASH);
            
            // ???????? What should this be?????
            //clsChar();
        }
	else{
	    return null;
	}
        return null;
    }

    public NFA excludeSet() {
	if(peekToken(NOT)) {
	    charSet();
	    if(peekToken(RBRAC)) {
	    	matchToken(RBRAC);
		if(peekToken(IN))
		    excludeSetTail();
		else
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
	if(peekToken(LBRAC)) {
	    matchToken(LBRAC);
	    charSet();
	    if(peekToken(RBRAC)) {
		matchToken(RBRAC);
	    }
	}
	else{
	    definedClass();
	}
	return null;
    }
}
