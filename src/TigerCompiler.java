import Parser.TigerParser;
import Scanner.TigerScanner;
import Scanner.Token.Token;
import Scanner.DFA;
import java.util.*;

/**
 * Created by patrickcaruso on 9/16/16.
 */
public class TigerCompiler {

    private static final String fileLocation = "src/text.txt";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        TigerScanner scanner = new TigerScanner(fileLocation);
        TigerParser parser = new TigerParser(fileLocation);

        //prints the individual tokens from the text
        printScannerTokens(scanner);

        //prints the parse results
        printParse(parser);
    }

    public static void printScannerTokens(TigerScanner scan) {
        while (!scan.atEnd()) {
            Token token = scan.getToken();
            System.out.println(token + " ");
        }
    }

    public static void printParse(TigerParser p) {
        System.out.println("Successful parse: " + p.successfulParse(false));
    }
}
