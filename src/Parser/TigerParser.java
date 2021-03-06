package Parser;

import Parser.Grammar.Rule;
import Scanner.TigerScanner;
import Scanner.Token.Token;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by patrickcaruso on 10/3/16.
 */
public class TigerParser {

    TigerScanner scanner;
    LinkedList<Token> stack = new LinkedList<>();

    HashMap<String, Integer> variableMap = new HashMap<>();
    HashMap<String, Integer> tokenMap = new HashMap<>();

    public TigerParser(String fileLocation) {
        scanner = new TigerScanner(fileLocation);
        variableMap = initiateVariableMap();
        tokenMap = initiateTokenMap();
    }

    public boolean successfulParse(boolean verbose) {
        stack.clear();
        stack.add(Token.compose("TP"));

        Token t = null;

        while ((t = scanner.getToken()) != null && stack.size() > 0) {
            if (t.isComment()) {
                continue;
            }

            if (verbose) {
                System.out.println("===========================");
                System.out.println("TRYING TO MATCH: " + t.getToken());
            }

            boolean matches = false;
            while (!matches) {
                if (stack.size() > 0 && stack.get(0).matches(t)) {
                    Token tok = stack.removeFirst();
                    if (verbose) {
                        System.out.println("MATCHED: " + tok + " with " + t);
                    }
                    break;
                }

                if (verbose) {
                    System.out.print ("STACK: (" + stack.size() + ") ");
                    System.out.print("[");

                    for (Token tok : stack) {
                        System.out.print(tok.getToken() + " ");
                    }
                    System.out.println("]");
                }

                Token topToken = stack.removeFirst();

                if (verbose) {
                    System.out.println("POP: " + topToken.getToken());
                }

                int ruleNumber = lookupRule(topToken, t);

                Rule rule = null;

                rule = Rule.rules[ruleNumber];
                if (verbose) {
                    System.out.println("Using rule: " + ruleNumber);
                }

                //no valid LL(1) parse
                if (ruleNumber == 0) {
                    System.out.println("Couldnt match " + topToken + " with " + t);
                    return false;
                }

                if (!rule.satisfies(topToken)) {
                    System.out.println("Couldnt match " + topToken + " with " + t);
                    return false;
                }

                for (int i = rule.getDecompose().length - 1; i >= 0; i--) {
                    stack.addFirst(rule.getDecompose()[i]);
                }
                if (verbose) {
                    System.out.println("Checking whether [" + topToken + "] matches [" + t + "]");
                }
                if (stack.size() > 0 && stack.get(0).matches(t)) {
                    Token tok = stack.removeFirst();
                    if (verbose) {
                        System.out.println("MATCHED: " + tok + " with " + t);
                    }
                    break;
                }
            }
        }
        return stack.size() == 0;
    }

    private int lookupRule(Token topOfStack, Token input) {
        if (!variableMap.containsKey(topOfStack.getToken()) || !tokenMap.containsKey(input.getToken())) return 0;
        int rule = variableMap.get(topOfStack.getToken());
        int inputToken = tokenMap.get(input.getToken());
        return parseTable[rule][inputToken];
    }

    /**
     * 2D representation of the parse table. The inputs include the Token from the
     * Scanner stream and the top Token on the stack. The Tokens are passed through
     * a HashMap to map them to their respective index in the table, and the resulting
     * value is mapped to a certain rule in the Rule.rules[] rule list.
     */
    private static int[][] parseTable = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0, 0, 0, 0, 3, 4, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 6, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 14, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 17, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 20, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 22, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 24, 0, 0, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29, 0, 0, 0, 29, 0, 0, 0, 0, 0, 0, 29, 0, 29, 0, 29, 0, 0, 0, 0, 0, 29, 0, 0, 29},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 30, 0, 0, 31, 31, 31, 31, 30, 0, 30, 0, 30, 0, 0, 0, 0, 0, 30, 0, 0, 30},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 78, 0, 0, 0, 37, 0, 0, 0, 0, 0, 0, 35, 0, 33, 0, 39, 0, 0, 0, 0, 0, 34, 0, 0, 38},
            {0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 0, 93, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 42, 42, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 44, 0, 44, 0, 44, 0, 44, 0, 0, 0, 43, 43, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 44, 0, 0, 0, 44, 0, 44, 0, 44, 44, 44, 44, 0, 44, 0, 44, 0, 44, 44, 0, 0, 44, 0, 0, 44},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45, 46, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 47, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 47, 47, 47, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 49, 0, 49, 0, 49, 0, 49, 0, 0, 0, 49, 49, 48, 48, 0, 0, 0, 0, 0, 0, 0, 0, 0, 49, 0, 0, 0, 49, 0, 49, 0, 49, 49, 49, 49, 0, 49, 0, 49, 0, 49, 49, 0, 0, 49, 0, 0, 49},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 77, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 51, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 51, 51, 51, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 53, 0, 53, 0, 53, 0, 53, 0, 0, 0, 53, 53, 53, 53, 52, 52, 52, 52, 0, 0, 0, 0, 0, 53, 52, 52, 0, 53, 0, 53, 0, 53, 53, 53, 53, 0, 53, 0, 53, 0, 53, 53, 0, 0, 53, 0, 0, 53},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 56, 57, 59, 58, 0, 0, 0, 0, 0, 0, 55, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60, 60, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 62, 0, 62, 0, 62, 0, 62, 0, 0, 0, 62, 62, 62, 62, 62, 62, 62, 62, 61, 61, 0, 0, 0, 62, 62, 62, 0, 62, 0, 62, 0, 62, 62, 62, 62, 0, 62, 0, 62, 0, 62, 62, 0, 0, 62, 0, 0, 62},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 67, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 65, 65, 66, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 68, 69, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 70, 71, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 70, 70, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 72, 0, 0, 0, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 76, 0, 0, 0, 76, 75, 76, 0, 0, 0, 76, 76, 76, 76, 76, 76, 76, 76, 76, 76, 76, 0, 0, 76, 76, 76, 0, 76, 0, 76, 0, 76, 76, 76, 76, 0, 76, 0, 76, 0, 76, 76, 0, 0, 76, 0, 0, 76},
            {0, 0, 0, 0, 79, 0, 81, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 83, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 84, 84, 82, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 87, 85, 0, 86, 0, 0, 0, 0, 87, 87, 87, 87, 87, 87, 87, 87, 87, 87, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 87, 0, 0, 0, 0, 0, 0, 0, 88, 88, 89, 89, 90, 90, 90, 90, 91, 91, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}        
    
    };

    private static HashMap<String, Integer> initiateTokenMap() {
        HashMap<String, Integer> indexer = new HashMap<>();
        indexer.put("null", 0);
        indexer.put("COMMA", 1);
        indexer.put("COLON", 2);
        indexer.put("SEMI", 3);
        indexer.put("LPAREN", 4);
        indexer.put("RPAREN", 5);
        indexer.put("LBRACK", 6);
        indexer.put("RBRACK", 7);
        indexer.put("LBRACE", 8);
        indexer.put("RBRACE", 9);
        indexer.put("PERIOD", 10);
        indexer.put("PLUS", 11);
        indexer.put("MINUS", 12);
        indexer.put("MULT", 13);
        indexer.put("DIV", 14);
        indexer.put("LESSER", 15);
        indexer.put("GREATER", 16);
        indexer.put("GREATEREQ", 17);
        indexer.put("LESSEREQ", 18);
        indexer.put("AND", 19);
        indexer.put("OR", 20);
        indexer.put("ASSIGN", 21);
        indexer.put("INTLIT", 22);
        indexer.put("FLOATLIT", 23);
        indexer.put("ID", 24);
        indexer.put("NEQ", 25);
        indexer.put("EQ", 26);
        indexer.put("ARRAY", 27);
        indexer.put("BREAK", 28);
        indexer.put("BEGIN", 29);
        indexer.put("DO", 30);
        indexer.put("ELSE", 31);
        indexer.put("END", 32);
        indexer.put("ENDDO", 33);
        indexer.put("ENDIF", 34);
        indexer.put("FOR", 35);
        indexer.put("FUNC", 36);
        indexer.put("IF", 37);
        indexer.put("IN", 38);
        indexer.put("LET", 39);
        indexer.put("OF", 40);
        indexer.put("THEN", 41);
        indexer.put("TO", 42);
        indexer.put("TYPE", 43);
        indexer.put("VAR", 44);
        indexer.put("WHILE", 45);
        indexer.put("INT", 46);
        indexer.put("FLOAT", 47);
        indexer.put("RETURN", 48);
        return indexer;
    }

    private static HashMap<String, Integer> initiateVariableMap() {
        HashMap<String, Integer> indexer = new HashMap<>();
        indexer.put("TP", 0);
        indexer.put("DS", 1);
        indexer.put("TDL", 2);
        indexer.put("VDL", 3);
        indexer.put("FDL", 4);
        indexer.put("TD", 5);
        indexer.put("TYPE", 6);
        indexer.put("TYPEID", 7);
        indexer.put("VD", 8);
        indexer.put("IDLIST", 9);
        indexer.put("OPIDLIST", 10);
        indexer.put("OPINIT", 11);
        indexer.put("FD", 12);
        indexer.put("PLIST", 13);
        indexer.put("PLT", 14);
        indexer.put("RETTYPE", 15);
        indexer.put("P", 16);
        indexer.put("SS", 17);
        indexer.put("SSUF", 18);
        indexer.put("S", 19);
        indexer.put("EXPR", 20);
        indexer.put("EXPRP", 21);
        indexer.put("PLUSMINUS", 22);
        indexer.put("TERM", 23);
        indexer.put("TERMP", 24);
        indexer.put("MULTDIV", 25);
        indexer.put("COMP", 26);
        indexer.put("COMPP", 27);
        indexer.put("COMPARATOR", 28);
        indexer.put("LOGIC", 29);
        indexer.put("LOGICP", 30);
        indexer.put("ANDOR", 31);
        indexer.put("VAL", 32);
        indexer.put("CONST", 33);
        indexer.put("EXPRLIST", 34);
        indexer.put("EXPRLISTTAIL", 35);
        indexer.put("LVAL", 36);
        indexer.put("LVALT", 37);
        indexer.put("SP", 38);
        indexer.put("RHS", 39);
        indexer.put("RHSP", 40);
        indexer.put("RHSPP", 41);
        return indexer;
    }
}
