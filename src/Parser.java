import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
public class Parser {
	public static void main(String[] args){
		try{
			Scanner scan = new Scanner(new File("input_spec.txt"));
			Terminals currClass = null;
			ArrayList<Terminals> classes = new ArrayList<Terminals>();
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				Scanner lineScan = new Scanner(line);
				int count = 0;
				while(lineScan.hasNext()){
				
					//System.out.println(token);
					String token = lineScan.next();
					if(count == 0 && token.charAt(0) == '$'){
						Terminals newClass = new Terminals();
						currClass = newClass;
						classes.add(currClass);
						currClass.setName(token.substring(1,token.length()));
						System.out.println(currClass.getName());
					}
					else {
						if(token.charAt(0) == '['){
							int endIndex = token.indexOf(']');
							String inside = token.substring(1,endIndex);
							System.out.println(inside);
							if(inside.charAt(0) == '^'){
								ArrayList<Character> list = getIntervalOfChars(inside.substring(1));
								String s = lineScan.next();
								if(s.equals("IN")){
									String newClass = lineScan.next();
									String className = newClass.substring(1);
									//classes.contains();
								}
							} else {
								ArrayList<Character> list = getIntervalOfChars(inside);
								for(char c : list){
									currClass.addChar(c);
									//System.out.println("added "+c+" to "+currClass.getName());
								}
							}
						}
					}
					count++;
				}
			}
		} catch(Exception e){}
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
