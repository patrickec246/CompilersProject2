package Parser.Grammar;

import Scanner.Token.Token;

/**
 * Created by patrickcaruso on 10/3/16.
 */
public class Rule {

    private Token compose;
    private Token[] decompose;

    public Rule(Token compose, Token[] decompose) {
        this.compose = compose;
        this.decompose = decompose;
    }

    public boolean satisfies(Token t) {
        return t.matches(t);
    }

    public Token[] getDecompose() {
        return decompose;
    }

    private static Rule rule1 = new Rule(Token.compose("TIGERPROGRAM"), new Token[] {Token.LET, Token.compose("DS"), Token.IN, Token.compose("SS"), Token.END});

    public static Rule[] rules = {null, rule1};
}
