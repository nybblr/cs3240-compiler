CS 3240 Scanner
===============
Compiler project for CS 3240 at GT: Languages and Computation. By Jonathan Martin, Rochelle Lobo, and Ramya Ramakrishnan.

Execution
---------
Open a command line/terminal and navigate to the `/src` directory. Run the following to compile the main program:

		javac Parser.java

Then type in the following to execute the Parser:

		java Parser -s <input_spec.txt> [-i <input.txt>] [-d <dfa.txt>] [-o <output.txt>]
		
		[] - these are optional inputs however if -o <output.txt> is used then -i <input.txt> must also be used.
		<> - the text file name (do not include <> around your filename just type filename)

**Note:** *the DFA table outputted will be very large; make sure you open it with line wrapping turned off. In Vim, type `:set nowrap` from normal mode to turn of wrapping.*
