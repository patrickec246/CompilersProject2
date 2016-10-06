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
        stack.add(Token.compose("TIGERPROGRAM"));

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
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}
    };

    private static HashMap<String, Integer> initiateTokenMap() {
        HashMap<String, Integer> indexer = new HashMap<>();
        indexer.put("TIGERPROGRAM", 1);
        indexer.put("LET", 2);
        return indexer;
    }

    private static HashMap<String, Integer> initiateVariableMap() {
        HashMap<String, Integer> indexer = new HashMap<>();
        indexer.put("TIGERPROGRAM", 0);
        indexer.put("DS", 1);
        return indexer;
    }
}
