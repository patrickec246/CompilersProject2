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

    public boolean successfulParse() {
        stack.clear();
        stack.add(Token.compose("TP"));

        Token t = null;
        int iv = 0;
        while ((t = scanner.getToken()) != null && iv <= 1) {
            System.out.println("===========================");
            System.out.println("TRYING TO MATCH: " + t.getToken());
            boolean atLeft = false;
            int max = 0;
            while (!atLeft && max < 2) {
                iv++;
                max++;
                System.out.println("STACK: (" + stack.size() + ")");
                for (Token tok : stack) {
                    System.out.println(tok.getToken());
                }
                Token topToken = stack.removeFirst();
                System.out.println("POP: " + topToken.getToken());

                //lol obviously lookup the rule to use
                int ruleNumber = lookupRule(topToken, t);
                Rule rule = Rule.rules[ruleNumber];
                System.out.println("Using rule: " + ruleNumber);
                //no valid LL(1) parse
                if (ruleNumber == 0) {
                    return false;
                }
                if (!rule.satisfies(topToken)) {
                    return false;
                }

                for (int i = rule.getDecompose().length - 1; i >= 0; i--) {
                    stack.addFirst(rule.getDecompose()[i]);
                }

                if (stack.get(0).matches(t)) {
                    atLeft = true;
                    System.out.println("MATCHED: " + stack.removeFirst() + " with " + t);
                }
            }
        }
        return true;
    }

    private int lookupRule(Token topOfStack, Token input) {
        if (!variableMap.containsKey(topOfStack.getToken()) || !tokenMap.containsKey(input.getToken())) return 0;
        int rule = variableMap.get(topOfStack.getToken());
        int inputToken = tokenMap.get(input.getToken());
        return parseTable[rule][inputToken];
    }

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
            {0, 22, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 24, 0, 0, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29, 0, 0, 0, 29, 0, 0, 0, 0, 0, 0, 29, 29, 0, 0, 29, 0, 0, 0, 0, 0, 29, 0, 0, 29},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 31, 31, 31, 30, 30, 0, 0, 30, 0, 0, 0, 0, 0, 30, 0, 0, 30},
            
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 37, 0, 0, 0, 0, 0, 0, 35, 33, 0, 0, 39, 0, 0, 0, 0, 0, 34, 0, 0, 38},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            
            {0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 42, 42, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 44, 0, 0, 0, 44, 0, 44, 0, 0, 0, 43, 43, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 44, 0, 0, 0, 44, 0, 44, 0, 44, 44, 44, 44, 0, 44, 0, 44, 0, 44, 44, 0, 0, 44, 0, 0, 44},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45, 46, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 47, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 47, 47, 47, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 49, 0, 0, 0, 49, 0, 49, 0, 0, 0, 49, 49, 48, 48, 0, 0, 0, 0, 0, 0, 0, 0, 0, 49, 0, 0, 0, 49, 0, 49, 0, 49, 49, 49, 49, 0, 49, 0, 49, 0, 49, 49, 0, 0, 49, 0, 0, 49},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 77, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 51, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 51, 51, 51, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 53, 0, 0, 0, 53, 0, 53, 0, 0, 0, 53, 53, 53, 53, 52, 52, 52, 52, 0, 0, 0, 0, 0, 53, 52, 52, 0, 53, 0, 53, 0, 53, 53, 53, 53, 0, 53, 0, 53, 0, 53, 53, 0, 0, 53, 0, 0, 53},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 56, 57, 59, 58, 0, 0, 0, 0, 0, 0, 55, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60, 60, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 62, 0, 0, 0, 62, 0, 62, 0, 0, 0, 62, 62, 62, 62, 62, 62, 62, 62, 61, 61, 0, 0, 0, 62, 62, 62, 0, 62, 0, 62, 0, 62, 62, 62, 62, 0, 62, 0, 62, 0, 62, 62, 0, 0, 62, 0, 0, 62},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 67, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 65, 65, 66, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 68, 69, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 70, 71, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 70, 70, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 72, 0, 0, 0, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 76, 0, 0, 0, 76, 75, 76, 0, 0, 0, 76, 76, 76, 76, 76, 76, 76, 76, 76, 76, 76, 0, 0, 76, 76, 76, 0, 76, 0, 76, 0, 76, 76, 76, 76, 0, 76, 0, 76, 0, 76, 76, 0, 0, 76, 0, 0, 76}
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
        indexer.put("OPPREF", 20);
        indexer.put("EXPR", 21);
        indexer.put("EXPRP", 22);
        indexer.put("PLUSMINUS", 23);
        indexer.put("TERM", 24);
        indexer.put("TERMP", 25);
        indexer.put("MULTDIV", 26);
        indexer.put("COMP", 27);
        indexer.put("COMPP", 28);
        indexer.put("COMPARATOR", 29);
        indexer.put("LOGIC", 30);
        indexer.put("LOGICP", 31);
        indexer.put("ANDOR", 32);
        indexer.put("VAL", 33);
        indexer.put("CONST", 34);
        indexer.put("EXPRLIST", 35);
        indexer.put("EXPRLISTTAIL", 36);
        indexer.put("LVAL", 37);
        indexer.put("LVALT", 38);
        return indexer;
    }
}
