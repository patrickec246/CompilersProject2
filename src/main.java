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
        long start = System.currentTimeMillis();
        TigerScanner scanner = new TigerScanner(fileLocation);
        TigerParser parser = new TigerParser(fileLocation);

        while (!scanner.atEnd()) {
            Token token = scanner.getToken();
            System.out.println(token + " ");
        }

       //System.out.println("Successful parse: " + parser.successfulParse(false));
       //System.out.println("Total time: " + (System.currentTimeMillis() - start));
    }
}
