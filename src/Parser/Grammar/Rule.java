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
    // epislons are empty lists
    private static Rule rule1 = new Rule(Token.compose("TP"), new Token[] {Token.LET, Token.compose("DS"), Token.IN, Token.compose("SS"), Token.END});
    private static Rule rule2 = new Rule(Token.compose("DS"), new Token[] {Token.compose("TDL"), Token.compose("VDL"), Token.compose("FDL")});
    
    //TDL//
    private static Rule rule3 = new Rule(Token.compose("TDL"), new Token[] {Token.compose("TD"), Token.compose("TDL")});
    private static Rule rule4 = new Rule(Token.compose("TDL"), new Token[] {});
    
    //VDL//
    private static Rule rule5 = new Rule(Token.compose("VDL"), new Token[] {Token.compose("VD"), Token.compose("VDL")});
    private static Rule rule6 = new Rule(Token.compose("VDL"), new Token[] {});
    
    //FDL//
    private static Rule rule7 = new Rule(Token.compose("FDL"), new Token[] {Token.compose("FD"), Token.compose("FDL")});
    private static Rule rule8 = new Rule(Token.compose("FDL"), new Token[] {});

    private static Rule rule9 = new Rule(Token.compose("TD"), new Token[] {Token.TYPE, Token.compose("ID"), Token.EQ, Token.compose("TYPE"), Token.SEMI});
    
    //TYPE// intlist and Braces?
    private static Rule rule10 = new Rule(Token.compose("TYPE"), new Token[] {Token.compose("TYPEID") });
    private static Rule rule11 = new Rule(Token.compose("TYPE"), new Token[] {Token.ARRAY, Token.LBRACK, Token.INTLIT, Token.RBRACK, Token.OF, Token.compose("TYPEID")});
    private static Rule rule12 = new Rule(Token.compose("TYPE"), new Token[] {Token.ID});

    //TYPEID
    private static Rule rule13 = new Rule(Token.compose("TYPEID"), new Token[] {Token.INT});
    private static Rule rule14 = new Rule(Token.compose("TYPEID"), new Token[] {Token.FLOAT}); 

    private static Rule rule15 = new Rule(Token.compose("VD"), new Token[] { Token.VAR, Token.compose("IDLIST"), Token.COLON, Token.compose("TYPE"), Token.compose("OPINIT"), Token.SEMI});
    private static Rule rule16 = new Rule(Token.compose("IDLIST"), new Token[] { Token.ID, Token.compose("OPIDLIST") });
    
    //OPIDLIST//
    private static Rule rule17 = new Rule(Token.compose("OPIDLIST"), new Token[] { Token.COMMA, Token.compose("IDLIST") });
    private static Rule rule18 = new Rule(Token.compose("OPIDLIST"), new Token[] {});

    //OPINIT//
    private static Rule rule19 = new Rule(Token.compose("OPINIT"), new Token[] { Token.ASSIGN, Token.compose("CONST") });
    private static Rule rule20 = new Rule(Token.compose("OPINIT"), new Token[] {});

    private static Rule rule21 = new Rule(Token.compose("FD"), new Token[] { Token.FUNC, Token.ID, Token.LPAREN, Token.compose("PLIST"), Token.RPAREN, Token.compose("RETTYPE"), Token.BEGIN, Token.compose("SS"), Token.END, Token.SEMI });

    //PLIST
    private static Rule rule22 = new Rule(Token.compose("PLIST"), new Token[] { Token.compose("P"), Token.compose("PLIST") });
    private static Rule rule23 = new Rule(Token.compose("PLIST"), new Token[] {});

    //PTL
    private static Rule rule24 = new Rule(Token.compose("PTL"), new Token[] { Token.COMMA ,Token.compose("P"), Token.compose("PLIST") });
    private static Rule rule25 = new Rule(Token.compose("PTL"), new Token[] {});

    //RETTYPE
    private static Rule rule26 = new Rule(Token.compose("RETTYPE"), new Token[] { Token.COLON, Token.compose("TYPE")});
    private static Rule rule27 = new Rule(Token.compose("RETTYPE"), new Token[] {});

    private static Rule rule28 = new Rule(Token.compose("P"), new Token[] { Token.ID ,Token.COLON, Token.compose("TYPE")});
    
    private static Rule rule29 = new Rule(Token.compose("SS"), new Token[] {Token.compose("S"), Token.compose("SSUF")});

    //SSURF
    private static Rule rule30 = new Rule(Token.compose("SSUF"), new Token[] {Token.compose("S"), Token.compose("SSUF")});
    private static Rule rule31 = new Rule(Token.compose("SSUF"), new Token[] {});

    //SS
    private static Rule rule32 = new Rule(Token.compose("S"), new Token[] {Token.compose("LVAL"), Token.ASSIGN, Token.compose("EXPR"), Token.SEMI});
    private static Rule rule33 = new Rule(Token.compose("S"), new Token[] {Token.IF, Token.compose("EXPR"), Token.THEN, Token.compose("SS"), Token.ENDIF});
    private static Rule rule34 = new Rule(Token.compose("S"), new Token[] {Token.WHILE, Token.compose("EXPR"), Token.DO, Token.compose("SS"), Token.ENDDO});
    private static Rule rule35 = new Rule(Token.compose("S"), new Token[] {Token.FOR, Token.ID, Token.ASSIGN, Token.compose("EXPR"), Token.TO, Token.compose("EXPR"), Token.DO, Token.compose("SS"), Token.ENDDO});
    private static Rule rule36 = new Rule(Token.compose("S"), new Token[] {Token.compose("OPPREF"), Token.ID, Token.LPAREN, Token.compose("EXPRLIST"), Token.RPAREN, Token.SEMI});
    private static Rule rule37 = new Rule(Token.compose("S"), new Token[] {Token.BREAK, Token.SEMI});
    private static Rule rule38 = new Rule(Token.compose("S"), new Token[] {Token.RETURN, Token.compose("EXPR")});
    private static Rule rule39 = new Rule(Token.compose("S"), new Token[] {Token.LET, Token.compose("DS"), Token.IN, Token.compose("SS"), Token.END});

    // OPPREF
    private static Rule rule40 = new Rule(Token.compose("OPPREF"), new Token[] {Token.compose("LVAL"), Token.ASSIGN, Token.compose("EXPR")});
    private static Rule rule41 = new Rule(Token.compose("OPPREF"), new Token[] {});

    private static Rule rule42 = new Rule(Token.compose("EXPR"), new Token[] {Token.compose("TERM"), Token.compose("EXPRP")});
    
    //EXPRP
    private static Rule rule43 = new Rule(Token.compose("EXPRP"), new Token[] {Token.compose("PLUSMINUS"), Token.compose("TERM"), Token.compose("EXPRP")});
    private static Rule rule44 = new Rule(Token.compose("EXPRP"), new Token[] {});

    //PLUSMINUS
    private static Rule rule45 = new Rule(Token.compose("PLUSMINUS"), new Token[] {Token.PLUS});
    private static Rule rule46 = new Rule(Token.compose("PLUSMINUS"), new Token[] {Token.MINUS}); 

    private static Rule rule47 = new Rule(Token.compose("TERM"), new Token[] {Token.compose("COMP"), Token.compose("TERMP")});
    
    //TERMP
    private static Rule rule48 = new Rule(Token.compose("TERMP"), new Token[] {Token.compose("MULTDIV"), Token.compose("COMP"), Token.compose("TERMP")});
    private static Rule rule49 = new Rule(Token.compose("TERMP"), new Token[] {});

    //MULTDIV
    private static Rule rule50 = new Rule(Token.compose("MULTDIV"), new Token[] {Token.MULT});

    private static Rule rule51 = new Rule(Token.compose("COMP"), new Token[] {Token.compose("LOGIC"), Token.compose("COMPP")});
    
    //COMPP
    private static Rule rule52 = new Rule(Token.compose("COMPP"), new Token[] {Token.compose("COMPARATOR"), Token.compose("LOGIC"), Token.compose("TERMP")});
    private static Rule rule53 = new Rule(Token.compose("COMPP"), new Token[] {});

    //COMPARATOR // double symbol?
    private static Rule rule54 = new Rule(Token.compose("COMPARATOR"), new Token[] {Token.EQ});
    private static Rule rule55 = new Rule(Token.compose("COMPARATOR"), new Token[] {Token.NEQ}); 
    private static Rule rule56 = new Rule(Token.compose("COMPARATOR"), new Token[] {Token.LESSER});
    private static Rule rule57 = new Rule(Token.compose("COMPARATOR"), new Token[] {Token.GREATER}); 
    private static Rule rule58 = new Rule(Token.compose("COMPARATOR"), new Token[] {Token.LESSEREQ});
    private static Rule rule59 = new Rule(Token.compose("COMPARATOR"), new Token[] {Token.GREATEREQ});     

    private static Rule rule60 = new Rule(Token.compose("LOGIC"), new Token[] {Token.compose("VAL"), Token.compose("LOGICP")});
    
    //LOGICP
    private static Rule rule61 = new Rule(Token.compose("LOGICP"), new Token[] {Token.compose("ANDOR"), Token.compose("VAL"), Token.compose("LOGICP")});
    private static Rule rule62 = new Rule(Token.compose("LOGICP"), new Token[] {});

    //ANDOR
    private static Rule rule63 = new Rule(Token.compose("ANDOR"), new Token[] {Token.AND});
    private static Rule rule64 = new Rule(Token.compose("ANDOR"), new Token[] {Token.OR}); 

    //VAL
    private static Rule rule65 = new Rule(Token.compose("VAL"), new Token[] {Token.compose("CONST")});
    private static Rule rule66 = new Rule(Token.compose("VAL"), new Token[] {Token.compose("LVAL")});
    private static Rule rule67 = new Rule(Token.compose("VAL"), new Token[] {Token.LPAREN, Token.compose("EXPR"), Token.RPAREN});

    //CONST int and float tokens
    private static Rule rule68 = new Rule(Token.compose("CONST"), new Token[] {Token.INTLIT});
    private static Rule rule69 = new Rule(Token.compose("CONST"), new Token[] {Token.FLOATLIT});  


    private static Rule rule70 = new Rule(Token.compose("EXPRLIST"), new Token[] {Token.compose("EXPR"), Token.compose("EXPRLISTTAIL")});
    private static Rule rule71 = new Rule(Token.compose("EXPRLIST"), new Token[] {});  

    private static Rule rule72 = new Rule(Token.compose("EXPRLISTTAIL"), new Token[] {Token.compose("EXPR"), Token.compose("EXPRLISTTAIL")});
    private static Rule rule73 = new Rule(Token.compose("EXPRLISTTAIL"), new Token[] {});  


    private static Rule rule74 = new Rule(Token.compose("LVAL"), new Token[] {Token.ID, Token.compose("LVALT")});
    
    //LOGICP
    private static Rule rule75 = new Rule(Token.compose("LVALT"), new Token[] {Token.LBRACK, Token.compose("EXPR"), Token.RBRACK});
    private static Rule rule76 = new Rule(Token.compose("LVALT"), new Token[] {});

    //MULTDIV 
    private static Rule rule77 = new Rule(Token.compose("MULTDIV"), new Token[] {Token.DIV});

    private static Rule rule78 = new Rule(Token.compose("S"), new Token[] {Token.ID, Token.compose("SP")});

    private static Rule rule79 = new Rule(Token.compose("SP"), new Token[] {Token.LPAREN, Token.compose("EXPRLIST"), Token.RPAREN, Token.SEMI});
    private static Rule rule80 = new Rule(Token.compose("SP"), new Token[] {Token.ASSIGN, Token.compose("RHS")});
    private static Rule rule81 = new Rule(Token.compose("SP"), new Token[] {Token.LBRACK, Token.compose("EXPR"), Token.RBRACK, Token.ASSIGN, Token.compose("RHS")});

    private static Rule rule82 = new Rule(Token.compose("RHS"), new Token[] {Token.ID, Token.compose("RHSP"), Token.SEMI});
    private static Rule rule83 = new Rule(Token.compose("RHS"), new Token[] {Token.LPAREN, Token.compose("EXPR"), Token.RPAREN, Token.compose("RHSPP"), Token.SEMI});
    private static Rule rule84 = new Rule(Token.compose("RHS"), new Token[] {Token.compose("CONST"), Token.compose("RHSPP"), Token.SEMI});

    private static Rule rule85 = new Rule(Token.compose("RHSP"), new Token[] {Token.LPAREN, Token.compose("EXPR"), Token.RPAREN});
    private static Rule rule86 = new Rule(Token.compose("RHSP"), new Token[] {Token.LBRACK, Token.compose("EXPR"), Token.RBRACK, Token.compose("RHSPP")});
    private static Rule rule87 = new Rule(Token.compose("RHSP"), new Token[] {Token.compose("RHSPP")});

    private static Rule rule88 = new Rule(Token.compose("RHSPP"), new Token[] {Token.compose("PLUSMINUS"), Token.compose("EXPR")});
    private static Rule rule89 = new Rule(Token.compose("RHSPP"), new Token[] {Token.compose("MULTDIV"), Token.compose("EXPR")});
    private static Rule rule90 = new Rule(Token.compose("RHSPP"), new Token[] {Token.compose("COMPARATOR"), Token.compose("EXPR")});
    private static Rule rule91 = new Rule(Token.compose("RHSPP"), new Token[] {Token.compose("ANDOR"), Token.compose("EXPR")});
    private static Rule rule92 = new Rule(Token.compose("RHSPP"), new Token[] {});

    public static Rule[] rules = {null,
        rule1,
        rule2,
        rule3,
        rule4,
        rule5,
        rule6,
        rule7,
        rule8,
        rule9,
        rule10,
        rule11,
        rule12,
        rule13,
        rule14,
        rule15,
        rule16,
        rule17,
        rule18,
        rule19,
        rule20,
        rule21,
        rule22,
        rule23,
        rule24,
        rule25,
        rule26,
        rule27,
        rule28,
        rule29,
        rule30,
        rule31,
        rule32,
        rule33,
        rule34,
        rule35,
        rule36,
        rule37,
        rule38,
        rule39,
        rule40,
        rule41,
        rule42,
        rule43,
        rule44,
        rule45,
        rule46,
        rule47,
        rule48,
        rule49,
        rule50,
        rule51,
        rule52,
        rule53,
        rule54,
        rule55,
        rule56,
        rule57,
        rule58,
        rule59,
        rule60,
        rule61,
        rule62,
        rule63,
        rule64,
        rule65,
        rule66,
        rule67,
        rule68,
        rule69,
        rule70,
        rule71,
        rule72,
        rule73,
        rule74,
        rule75,
        rule76,
        rule77,
        rule78,
        rule79,
        rule80,
        rule81,
        rule82,
        rule83,
        rule84,
        rule85,
        rule86,
        rule87,
        rule88,
        rule89,
        rule90,
        rule91,
        rule92
    };
}
