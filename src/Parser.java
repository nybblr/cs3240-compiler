import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
public class Parser {
    static ArrayList<Terminals> charClasses;
    static ArrayList<Terminals> tokenDefs;
    static ArrayList<Terminals> charsAndTokens;
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException{
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        System.setOut(out);
        fileParser("input_spec2.txt");
        scanner("input2.txt");
    }
    
    public static void scanner(String filename) throws FileNotFoundException {
    	System.out.println("Scanning input file...");
        Scanner scan = new Scanner(new File(filename));
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            while(!line.isEmpty()) {
            	line = line.trim();
            	
            	ArrayList<ScanResult> results = new ArrayList<ScanResult>();
            	
            	// Run all the DFAs on the current string
            	// See which one matches the farthest
            	// First defined token takes precedence
            	int maxPointer = 0;
            	Terminals maxKlass = null;
            	for (Terminals klass : tokenDefs) {
            		ScanResult result = klass.getDFA().walk(line);
            		results.add(result);
            		if (result.lastPointer > maxPointer) {
            			maxPointer = result.lastPointer;
            			maxKlass = klass;
            		}
            	}
            	
            	// If nothing matched, invalid input!
            	if (maxPointer == 0) {
            		System.out.println("INVALID INPUT!");
            		return;
            	}
            	
            	// Something matched!
            	String token = line.substring(0, maxPointer);
            	
            	System.out.print(maxKlass.getName());
            	System.out.println(" "+token);
            	
            	// Consume and start over!
            	line = line.substring(maxPointer);
            }
        }
    }

    public static void fileParser(String filename) throws FileNotFoundException{
        System.out.println("Parsing input spec...");
        Scanner scan = new Scanner(new File(filename));
        Terminals currClass = null;
        charClasses = new ArrayList<Terminals>();
        tokenDefs = new ArrayList<Terminals>();
        charsAndTokens = new ArrayList<Terminals>();
        
        // Which array are we filling?
        ArrayList<Terminals> classes = charClasses;
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            
            // EOF or switch arrays?
            if (line.trim().isEmpty()) {
            	// Time for a switch?
            	if (classes == charClasses)
            		classes = tokenDefs;
            	continue;
            }
            
            Scanner lineScan = new Scanner(line);

            String token = lineScan.next();

            Terminals newClass = new Terminals();
            currClass = newClass;
            classes.add(currClass);
            charsAndTokens.add(currClass);
            currClass.setName(token.substring(1,token.length()));
            System.out.println(currClass.getName());

            token = line.substring(token.length());

            token = token.replaceAll("\\s","");
            System.out.println(token);
            RegExpFunc func = new RegExpFunc(token);
            NFA nfa = func.origRegExp(currClass.getName());
            currClass.setNFA(nfa);
            currClass.setDFA(nfa.toDFA());

            System.out.println(currClass.getNFA());
            System.out.println(currClass.getDFA());
        }
    }
    //	}
    /**
     * @return the classes
     */
    public static ArrayList<Terminals> getClasses() {
        return charsAndTokens;
    }

    public static ArrayList<Character> getIntervalOfChars(String inside){
        ArrayList<Character> list = new ArrayList<Character>();
        int index=0;
        char previousChar = inside.charAt(0);
        while(index<inside.length()){
            char c = inside.charAt(index);
            if(c == '-'){
                previousChar++;
                char currentChar = previousChar;
                char endChar = inside.charAt(index+1);
                while (currentChar < endChar){
                    list.add(currentChar);
                    //System.out.println("added "+currentChar+" to "+currClass.getName());
                    currentChar++;
                }
            }
            else{
                list.add(c);
                //System.out.println("added "+c+" to "+currClass.getName());
            }
            previousChar = c;
            index++;
        }
        return list;
    }
}
