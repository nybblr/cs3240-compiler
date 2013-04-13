To run the application

open command line in Windows, navigate to the folder containing the .java files.
Then type into command line
javac Parser.java

This will compile all of the files, then type in the following to execute the Parser:
	java Parser -s <input_spec.txt> [-i <input.txt>] [-d <dfa.txt>] [-o <output.txt>]
	
	[] - these are optional inputs however if -o <output.txt> is used then -i <input.txt> must also be used.
	<> - the text file name (do not include <> around your filename just type filename)