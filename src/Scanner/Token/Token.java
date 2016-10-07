package Scanner.Token;

/**
 * Created by patrickcaruso on 9/16/16.
 */
public class Token {

    private String token;
    private String input;

    public Token(String token, String input) {
        this.token = token;
        this.input = input;
    }

    public String toString() {
        return "<" + token + ", \"" + input + "\">";
    }

    public static Token getToken(String token, String input) {
        return new Token(token, input);
    }

    public String getToken() {
        return token;
    }

    public String getInput() {
        return input;
    }

    public boolean matches(Token t) {
        return t.getToken() != null && t.getToken().equals(token);
    }

    public static Token NULL = new Token("null", null);
    public static Token COMMA = new Token("COMMA", null);
    public static Token COLON = new Token("COLON", null);
    public static Token SEMI = new Token("SEMI", null);
    public static Token LPAREN = new Token("LPAREN", null);
    public static Token RPAREN = new Token("RPAREN", null);
    public static Token LBRACK = new Token("LBRACK", null);
    public static Token RBRACK = new Token("RBRACK", null);
    public static Token LBRACE = new Token("LBRACE", null);
    public static Token RBRACE = new Token("RBRACE", null);
    public static Token PERIOD = new Token("PERIOD", null);
    public static Token PLUS = new Token("PLUS", null);
    public static Token MINUS = new Token("MINUS", null);
    public static Token MULT = new Token("MULT", null);
    public static Token DIV = new Token("DIV", null);
    public static Token LESSER = new Token("LESSER", null);
    public static Token GREATER = new Token("GREATER", null);
    public static Token LESSEREQ = new Token("LESSEREQ", null);
    public static Token GREATEREQ = new Token("GREATEREQ", null);
    public static Token AND = new Token("AND", null);
    public static Token OR = new Token("OR", null);
    public static Token ASSIGN = new Token("ASSIGN", null);
    public static Token INTLIT = new Token("INTLIT", null);
    public static Token FLOATLIT = new Token("FLOATLIT", null);
    public static Token ID = new Token("ID", null);
    public static Token NEQ = new Token("NEQ", null);
    public static Token EQ = new Token("EQ", null);
    public static Token ARRAY = new Token("ARRAY", null);
    public static Token BREAK = new Token("BREAK", null);
    public static Token BEGIN = new Token("BEGIN", null);
    public static Token DO = new Token("DO", null);
    public static Token ELSE = new Token("ELSE", null);
    public static Token END = new Token("END", null);
    public static Token ENDDO = new Token("ENDDO", null);
    public static Token FOR = new Token("FOR", null);
    public static Token FUNC = new Token("FUNC", null);
    public static Token IF = new Token("IF", null);
    public static Token IN = new Token("IN", null);
    public static Token LET = new Token("LET", null);
    public static Token OF = new Token("OF", null);
    public static Token THEN = new Token("THEN", null);
    public static Token TO = new Token("TO", null);
    public static Token TYPE = new Token("TYPE", null);
    public static Token VAR = new Token("VAR", null);
    public static Token WHILE = new Token("WHILE", null);
    public static Token ENDIF = new Token("ENDIF", null);
    public static Token INT = new Token("INT", null);
    public static Token FLOAT = new Token("FLOAT", null);

    public static Token compose(String token) {
        return new Token(token, null);
    }
}
