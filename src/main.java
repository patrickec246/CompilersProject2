import Parser.TigerParser;
import Scanner.TigerScanner;
import Scanner.Token.Token;
import Scanner.DFA;
import java.util.*;

/**
 * Created by patrickcaruso on 9/16/16.
 */
public class main {

    private static final String fileLocation = "src/text.txt";

    public static void main(String[] args) {
        System.out.println(((int) '\n'));
        TigerScanner scanner = new TigerScanner(fileLocation);
        TigerParser parser = new TigerParser(fileLocation);

        while (!scanner.atEnd()) {
            Token token = scanner.getToken();
           // System.out.println(token + " ");
        }

        parser.successfulParse();
    }
}
