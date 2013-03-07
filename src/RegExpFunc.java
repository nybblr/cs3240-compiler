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
    

    private void matchToken(Regex union) {
        // TODO Auto-generated method stub
        
    }

    private Regex peekToken() {
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
        regExTwo();
        regExOnePrime();
        return;
    }
    
    public void regExTwo() {
        if(peekToken() == Regex.LPAREN){
            matchToken(Regex.LPAREN);
            regExp();
            if(peekToken() == Regex.RPAREN){
                matchToken(Regex.RPAREN);
                regExTwoTail();
            }
        }
        else if(peekToken() == Regex.RECHAR){
            matchToken(Regex.RECHAR);
            regExTwoTail();
        }
        else{
            regExThree();
        }
    }
    
    public void regExTwoTail() {
        if(peekToken() == Regex.MULTI){
            matchToken(Regex.MULTI);
        }
        else if(peekToken() == Regex.PLUS){
            matchToken(Regex.PLUS);
        }
        else{
            return;
        }
    }
    
    public void regExThree() {
        charClass();
        return;
    }
    
    public void charClass() {
        if(peekToken() == Regex.PERIOD){
            matchToken(Regex.PERIOD);
        }
        else if(peekToken() == Regex.LBRAC){
            matchToken(Regex.LBRAC);
            charClassOne();
        }
        else{
            definedClass();
        }
    }

    public void charClassOne() {
        charSetList();
        excludeSet();
    }
    
    public void charSetList() {
        charSet();
        charSetList();
        if(peekToken() == Regex.RBRAC){
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
