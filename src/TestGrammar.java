import java.io.File;
import java.util.Scanner;


public class TestGrammar {
	public static void main(String[] args) throws Exception {
		Grammar g = new Grammar(new Scanner(new File("grammars/minire.txt")), new Scanner(new File("specs/minire.txt")));
		
		System.out.println(g.toString());
	}

}
