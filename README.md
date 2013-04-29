CS 3240 Scanner & Grammar Parser
================================
Compiler project for CS 3240 at GT: Languages and Computation. By Jonathan Martin, Rochelle Lobo, and Ramya Ramakrishnan.

Scanner
=======

Execution
---------
Open a command line/terminal and navigate to the `/src` directory. Run the following to compile the main program:

		javac Parser.java

Then type in the following to execute the Parser:

		java Parser -s <input_spec.txt> [-i <input.txt>] [-d <dfa.txt>] [-o <output.txt>]
		
		[] - these are optional inputs however if -o <output.txt> is used then -i <input.txt> must also be used.
		<> - the text file name (do not include <> around your filename just type filename)

**Note:** *the DFA table outputted will be very large; make sure you open it with line wrapping turned off. In Vim, type `:set nowrap` from normal mode to turn of wrapping.*

For debug output and testing other methods, use the TestScanner class's main function.

Modules
-------
Further information on the different classes is attached in the report. Those unaddressed include:
- Helpers: a simple class for ASCII ditties.
- StateSet: a simple wrapper around a set of NFA states used in the NFA to DFA conversion.
- ScanResult: a tuple returned after executing a DFA on input.

Grammar Parser
==============

Execution
---------
Run the main method of TestGrammar. Make sure to set the grammar, token spec, and sample script in the various paths.

The console will show:
1. parsed grammar rules as represented in memory
2. first/follow sets
3. LL(1) parse table
4. parse results of the input script

Modules
-------
Most of the classes are also discussed in the writeup. The class hierarchy is shown here for reference:
- Grammar
- Rule
- RuleItem (abstract)
  - Terminal (abstract)
    - Token
    - TokenClass
    - EpsilonTerminal
    - DollarTerminal
  - Variable
