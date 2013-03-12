import java.awt.font.LineBreakMeasurer;

/**
 * 
 */

/**
 * @author Rochelle
 *
 */
public class RegExpFunc {
    public enum Regex{SPACE, BSLASH, MULTI, PLUS, OR, LBRAC, RBRAC,
        LPAREN, RPAREN, PERIOD, APOS, QUOT, UNION, RECHAR, CLSCHAR}
    public enum Class{DIGIT, NONZERO, CHAR, UPPER}
    

    private void matchToken(Regex token) {
        // TODO Auto-generated method stub
        
    }
    
    private void matchToken(Class token) {
        //TODO Auto-generated method stub
    }

    private Object peekToken() {
        // TODO Auto-generated method stub
        return null;
    }
    
    private void definedClass() {
        // TODO Auto-generated method stub
        
    }
    
    public void origRegExp() {
        regExp();
    }
    
    public void regExp() {
        regExOne();
        regExPrime();
    }
    
    public void regExPrime() {
        if(peekToken() == Regex.UNION) {
            matchToken(Regex.UNION);
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
        if(peekToken() == Regex.LPAREN) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken() == Regex.RECHAR) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken() == Regex.PERIOD) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken() == Regex.LBRAC) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken() == Class.CHAR) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken() == Class.DIGIT) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken() == Class.NONZERO) {
            regExTwo();
            regExOnePrime();
        }
        else if(peekToken() == Class.UPPER) {
            regExTwo();
            regExOnePrime();
        }
        else {
            return;
        }
    }
    
    public void regExTwo() {
        if(peekToken() == Regex.LPAREN) {
            matchToken(Regex.LPAREN);
            regExp();
            if(peekToken() == Regex.RPAREN) {
                matchToken(Regex.RPAREN);
                regExTwoTail();
            }
        }
        else if(peekToken() == Regex.RECHAR) {
            matchToken(Regex.RECHAR);
            regExTwoTail();
        }
        else {
            if(peekToken() == Regex.PERIOD) {
                regExThree();
            }
            else if(peekToken() == Regex.LBRAC) {
                regExThree();
            }
            else if(peekToken() == Class.CHAR) {
                regExThree();
            }
            else if(peekToken() == Class.DIGIT) {
                regExThree();
            }
            else if(peekToken() == Class.NONZERO) {
                regExThree();
            }
            else if(peekToken() == Class.UPPER) {
                regExThree();
            }
        }
    }
    
    public void regExTwoTail() {
        if(peekToken() == Regex.MULTI) {
            matchToken(Regex.MULTI);
        }
        else if(peekToken() == Regex.PLUS) {
            matchToken(Regex.PLUS);
        }
        else {
            return;
        }
    }
    
    public void regExThree() {
        if(peekToken() == Regex.PERIOD) {
            charClass();
        }
        else if(peekToken() == Regex.LBRAC) {
            charClass();
        }
        else if(peekToken() == Class.CHAR) {
            charClass();
        }
        else if(peekToken() == Class.DIGIT) {
            charClass();
        }
        else if(peekToken() == Class.NONZERO) {
            charClass();
        }
        else if(peekToken() == Class.UPPER) {
            charClass();
        }
        else {
            return;
        }
    }
    
    public void charClass() {
        if(peekToken() == Regex.PERIOD) {
            matchToken(Regex.PERIOD);
        }
        else if(peekToken() == Regex.LBRAC) {
            matchToken(Regex.LBRAC);
            charClassOne();
        }
        else if(peekToken() == Class.CHAR || peekToken() == Class.DIGIT ||
                peekToken() == Class.NONZERO || peekToken() == Class.UPPER) {
            definedClass();
        }
    }

    public void charClassOne() {
        if(peekToken() == Regex.CLSCHAR) {
            charSetList();
        }
        else if(peekToken() == Regex.RBRAC) {
            charSetList();
        }
        else {
            excludeSet();
        }
    }
    
    public void charSetList() {
        if(peekToken() == Regex.CLSCHAR) {
            charSet();
            charSetList();
        }
        else if(peekToken() == Regex.RBRAC){
            matchToken(Regex.RBRAC);
        }
    }
    
    public void charSet() {
        if(peekToken() == Regex.CLSCHAR){
            matchToken(Regex.CLSCHAR);
            charSetTail();
        }
    }
    
    public void charSetTail() {
        if(peekToken() == Regex.CLSCHAR){
            matchToken(Regex.CLSCHAR);
        }
        else{
            return;
        }
    }
    
    public void excludeSet() {
        charSet();
        if(peekToken() == Regex.RBRAC){
            matchToken(Regex.RBRAC);
        }
    }
    
    public void excludeSetTail() {
        if(peekToken() == Regex.LBRAC){
            matchToken(Regex.LBRAC);
            charSet();
            if(peekToken() == Regex.RBRAC){
                matchToken(Regex.RBRAC);
            }
        }
        else{
            definedClass();
        }
    }
}
