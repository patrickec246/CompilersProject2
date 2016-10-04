package Parser;

import Parser.Grammar.Rule;
import Scanner.TigerScanner;
import Scanner.Token.Token;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by patrickcaruso on 10/3/16.
 */
public class TigerParser {

    TigerScanner scanner;
    List<Token> stack = new LinkedList<>();

    public TigerParser(String fileLocation) {
        scanner = new TigerScanner(fileLocation);
    }

    public boolean successfulParse() {
        Token t = null;
        while ((t = scanner.getToken()) != null) {
            //check for terminal finality
            Rule rule = lookupRule(t);
            if (rule == null) return false; //couldn't find a left parsing solution
            //shit this doesn't work wtf
        }
        return true;
    }

    private Rule lookupRule(Token t) {
        //todo lookup from ParseTable
        return null;
    }
}
