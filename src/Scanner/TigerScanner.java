package Scanner;

import Scanner.Token.Token;

import java.io.*;

/**
 * Created by patrickcaruso on 9/16/16.
 */
public class TigerScanner {

    InputStream stream;
    final Reader reader;
    char currentCharacter = 0;
    private final DFA logic = new DFA();
    String read = "";
    String peekStack = "";

    private final int LONGEST_MATCH = 1;
    private final int FIRST_MATCH = 2;
    private int mode = FIRST_MATCH;

    private int currentPosition = 0;
    private int lastMatch = -1;

    int lastValidState = 0;
    String lastValidToken = "";


    public TigerScanner(String fileLocation) {
        File file = new File(fileLocation);
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        reader = new InputStreamReader(stream);
    }

    /**
     * If mode == FIRST_MATCH, then we still haven't found a match,
     * meaning we need to pop from the stack until the stack is empty,
     * then get the characters from the stream in that order
     *
     * If mode == LONGEST_MATCH, then we've already found a match, but
     * we're looking ahead to find a longer match. Any characters popped
     * from the stream will be added to the stack
     * @return the next character in the relevant stream
     */
    public char popChar() {
        if (atEnd()) return 0;

        //our next character should come from the peekStack
        if (currentPosition < peekStack.length()) {
            char c = peekStack.charAt(currentPosition);
            currentPosition++;
            return c;
        } else {
            try {
                if (reader.ready()) {
                    currentCharacter = (char) reader.read();
                    return currentCharacter;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public boolean popAndStep() {
        char c = popChar();
        if (c > 0 && c < 127) {
            if (mode == LONGEST_MATCH && c >= 32 && c <= 128 && c != '\t') {
                peekStack += c;
                currentPosition++;
            }
            int i = logic.step(c);
            if (logic.accept()) {
                lastMatch = currentPosition;
                lastValidState = logic.getCurrentState();
                lastValidToken = logic.getStateName();
            }
            if (!logic.inValid()) {
                if (logic.inComment() || (c != ' ' && c != '\n' && c != '\t')) {
                    read += c;
                }
            }
            return true;
        } else {
            //System.out.println((int)c);
            return false;
        }
        //System.out.println(" -> " + logic.getStateName() + " [via '" + c + "' transition | " + peekStack + "]");
    }

    public boolean atEnd() {
        try {
            return !reader.ready() && (currentPosition >= peekStack.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Token getToken() {
        return getToken(true);
    }

    private Token getToken(boolean clear) {
        //we hit an invalid state
        if (logic.inValid()) {
            if (mode == LONGEST_MATCH) {
                peekStack = peekStack.substring(lastMatch);
                Token t = Token.getToken(lastValidToken, read);
                mode = FIRST_MATCH;
                read = "";
                if (clear) {
                    logic.reset();
                }
                currentPosition = 0;

                if (t.getToken().equals("ID")) {
                    t = logic.identifierToToken(t);
                }

                if (t == null) {
                    System.out.println("ERROR ON CHARACTER " + ((int)logic.getCharBuffer()) + "!");
                }
                System.out.println("NEXT TOKEN: " + t);
                return t;
            } else {
                System.out.println("Error at: " + currentCharacter);
                System.exit(0);
            }
        } else {
            if (popAndStep()) {
                if (logic.accept()) {
                    mode = LONGEST_MATCH;
                }
            } else {
                Token t = Token.getToken(lastValidToken, read);
                if (t.getToken().equals("ID")) {
                    t = logic.identifierToToken(t);
                }
                if (t == null) {
                    System.out.println("ERROR!");
                }

                System.out.println("NEXT TOKEN: " + t);
                return t;
            }
        }

        return getToken(clear);
    }

}
