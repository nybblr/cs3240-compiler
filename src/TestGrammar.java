import java.io.File;
import java.util.Scanner;


public class TestGrammar {
	public static void main(String[] args) throws Exception {
		Grammar g = new Grammar(new Scanner(new File("grammars/minire.txt")), new Scanner(new File("specs/minire.txt")));
		
		System.out.println(g.toString());
		g.calculateFirstSets();
		for(Variable variable : g.getVariables()){
			System.out.print("First("+variable+") = {");
			for(Terminal elementInFirstSet : variable.getFirst())
				System.out.print(elementInFirstSet+", ");
			System.out.println("}");
		}
	}

}
