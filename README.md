# TigerCompiler
This is a project for compiling the Tiger language implementing a Scanner, Parser, IML, and Assembler approach. 

# Scanner
The scanner receives as input a stream of characters from the source file. Each character drives a logical representation of the lexical representation of the language. The Scanner produces Tokens on a longest-match based on a look-ahead system. To do so, the Scanner implements a two-stream system: one stream holds raw characters retrieved sequentially from the source; the other stream holds peeked characters which have not had a decision applied to the lexical component. Together, the Scanner can operate in O(n) time, efficiently converting the input stream to a list of tokens and their relevant components.

Example:
Stream:
```
var a := 25;
```
Output: 
```
<VAR, "var"> <ID, "a"> <ASSIGN, ":="> <INTLIT, "25"> <SEMI, ";">
```

# Parser
The parser is currently a baseform implementation of an LL(1) parser, where the Token stream is received from left to right, and the Token is expanded and predicted based on the leftmost Token in the working stack. Using this method, the parser can make an absolute decision which reudction rule to use at any point. 

Example:

Stack:

| SS | 
| --- | ---
| $ | 

becomes
| S |
| ; | 
| --- | ---
| $ | 
