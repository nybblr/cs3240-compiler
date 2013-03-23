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
    RECHAR = "RECHAR", CLSCHAR = "CLSCHAR", IN = "IN", NOT = "^", DASH = "-";
    private ArrayList<Terminals> classes = Parser.getClasses();
    
    private String input;
    private InputStream is;

    /* Constructor */
    public RegExpFunc(String input) {
    	this.input = new String(input);
    	this.is = new InputStream(this.input);
    	origRegExp();
    }
    
    private boolean matchToken(String token) {
        return is.matchToken(token.charAt(0));
    }

    private String peekToken() {
        String s = String.valueOf(is.peekToken());
        System.out.println("Current regex char: " + s);
        return s;
    }
    
    private void reChar() {
        // TODO Auto-generated method stub
    }
    
    private void clsChar() {
        // TODO Auto-generated method stub
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
                System.out.println("Comparing: " + s + "to" + t);
                if((s).equals(peekToken())){
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
                System.out.println("Comparing: " + s + "to" + t);
                if((s).equals(peekToken())){
                    matchToken(peekToken());
                }
            }
        }
    }
    
    public void origRegExp() {
        regExp();
    }
    
    public void regExp() {
        regExOne();
        if(peekToken().equals(OR))
            regExPrime();
    }
    
    public void regExPrime() {
        if(peekToken().equals(OR)) {
            matchToken(OR);
            regExOne();
            regExPrime();
        }
        else {
            return;
        }
    }

    public void regExOne() {
        regExTwo();
        regExOnePrime();
    }
    
    public void regExOnePrime() {
        if(peekToken().equals(LPAREN)) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken().equals(RECHAR)) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken().equals(PERIOD)) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken().equals(LBRAC)) {
            regExTwo();
            regExOnePrime();
        }
        else if(inSpecification()) {
                regExTwo();
                regExOnePrime();            
        }
        else {
            return;
        }
    }
    
    public void regExTwo() {
        if(peekToken().equals(LPAREN)) {
            matchToken(LPAREN);
            regExp();
            if(peekToken().equals(RPAREN)) {
                matchToken(RPAREN);
                regExTwoTail();
            }
            else {
                System.out.println("Invalid syntax");
                System.exit(0);
            }
        }
        else if(peekToken().equals(RECHAR)) {
            matchToken(RECHAR);
            regExTwoTail();
        }
        else {
            if(peekToken().equals(PERIOD)) {
                regExThree();
            }
            else if(peekToken().equals(LBRAC)) {
                regExThree();
            }
            else if(inSpecification()) {
                regExThree();
            }
        }
    }
    
    public void regExTwoTail() {
        if(peekToken().equals(MULTI)) {
            matchToken(MULTI);
        }
        else if(peekToken().equals(PLUS)) {
            matchToken(PLUS);
        }
        else {
            return;
        }
    }
    
    public void regExThree() {
        if(peekToken().equals(PERIOD)) {
            charClass();
        }
        else if(peekToken().equals(LBRAC)) {
            charClass();
        }
        else if(inSpecification()) {
            charClass();
        }
        else if(peekToken().equals("null")) {
            return;
        }
        else {
            System.out.println("Incorrect syntax");
            System.exit(0);
        }
    }
    
    public void charClass() {
        if(peekToken().equals(PERIOD)) {
            matchToken(PERIOD);
        }
        else if(peekToken().equals(LBRAC)) {
            matchToken(LBRAC);
            charClassOne();
        }
        else {
            definedClass();
        }
    }

    public void charClassOne() {
        if(peekToken().equals(CLSCHAR)) {
            charSetList();
        }
        else if(peekToken().equals(RBRAC)) {
            charSetList();
        }
        else {
            excludeSet();
        }
    }
    
    public void charSetList() {
        if(peekToken().equals(CLSCHAR)) {
            charSet();
            charSetList();
        }
        else if(peekToken().equals(RBRAC)) {
            matchToken(RBRAC);
        }
        else{
            System.out.println("Invlid Syntax");
        }
    }
    
    public void charSet() {
        if(peekToken().equals(CLSCHAR)) {
            matchToken(CLSCHAR);
            charSetTail();
        }
    }
    
    public void charSetTail() {
        if(peekToken().equals(DASH)) {
            matchToken(DASH);
            clsChar();
        }
        else{
            return;
        }
    }
    
    public void excludeSet() {
        if(peekToken().equals(NOT)) {
            charSet();
            if(peekToken().equals(RBRAC)) {
                matchToken(RBRAC);
                if(peekToken().equals(IN))
                    excludeSetTail();
                else {
                    System.out.println("Invalid Syntax");
                    System.exit(0);
                }
            }
            else {
                System.out.println("Invalid Syntax");
                System.exit(0);
            }
        }
        else {
            System.out.println("Invalid Syntax");
            System.exit(0);
        }
    }
    
    public void excludeSetTail() {
        if(peekToken().equals(LBRAC)) {
            matchToken(LBRAC);
            charSet();
            if(peekToken().equals(RBRAC)) {
                matchToken(RBRAC);
            }
        }
        else{
            definedClass();
        }
    }
}
