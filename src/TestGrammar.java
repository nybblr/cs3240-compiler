import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class TestGrammar {
	public static void main(String[] args) throws Exception {
		String scriptFile = "script1.txt";
		String grammarFile = "minire.txt";
		
		// Print to file
		File file  = new File("script_outputs/"+scriptFile);
	    PrintStream printStream = new PrintStream(new FileOutputStream(file));
	    System.setOut(printStream);
		
		Grammar g = new Grammar(new Scanner(new File("grammars/"+grammarFile)), new Scanner(new File("specs/"+grammarFile)));
		
		System.out.println(g.toString());
		
		for(Variable variable : g.getVariables()){
			System.out.print("First("+variable+") = ");
			System.out.println(variable.getFirst());
		}
		System.out.println();
		for(Variable variable : g.getVariables()){
			System.out.print("Follow("+variable+") = ");
			System.out.println(variable.getFollow());
		}
		
		ParseTable pt = new ParseTable(g);
		System.out.println(pt.toString());
		
		pt.walk((new Scanner(new File("scripts/"+scriptFile))));
	}
}
