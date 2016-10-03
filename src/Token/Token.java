package Token;

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
}
