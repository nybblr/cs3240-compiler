import java.util.HashSet;
import java.util.Iterator;


public class Terminals implements Cloneable{
    private HashSet<Character> chars = new HashSet<Character>();
    private DFA dfa;
    private NFA nfa;
    private String name;

    /* Constructor */

    /* Setters and getters */
    public HashSet<Character> getChars() {
        return chars;
    }

    public void setChars(HashSet<Character> chars) {
        this.chars = chars;
    }
    public void addChar(char c) {
        chars.add(c);
    }
    public void removeChar(char c) {
        chars.remove(c);
    }
    public boolean containsChar(char c) {
        return chars.contains(c);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* Generators */
    public DFA getDFA() {
        return dfa;
    }

    public void setDFA(DFA dfa) {
        this.dfa = dfa;
    }

    public NFA getNFA() {
        return nfa;
    }

    public void setNFA(NFA nfa) {
        this.nfa = nfa;
    }

    public String toString() {
        return "$"+name+": "+chars.toString();
    }
    
//    @Override
//    public Object clone() {
//        try {
//            Terminals t = (Terminals) super.clone();
//            return t;
//        } catch (CloneNotSupportedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }  
//    }
}
