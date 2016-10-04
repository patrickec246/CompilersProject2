import Scanner.TigerScanner;
import Scanner.Token.Token;
import java.util.*;

/**
 * Created by patrickcaruso on 9/16/16.
 */
public class main {

    private static final String fileLocation = "src/text.txt";

    public static void main(String[] args) {
        TigerScanner scanner = new TigerScanner(fileLocation);

        while (!scanner.atEnd()) {
            Token token = scanner.getToken();
            System.out.println(token + " ");
        }
    }
}
