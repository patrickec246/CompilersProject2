package Parser.Grammar;

/**
 * Created by patrickcaruso on 10/3/16.
 */
public class Rule {

    private String compose;
    private String[] decompose;

    public Rule(String compose, String[] decompose) {
        this.compose = compose;
        this.decompose = decompose;
    }

    //eg A -> id | EXPR will return {id, EXPR}
    public String[] getTokenDecomposition() {
        return decompose;
    }

}
