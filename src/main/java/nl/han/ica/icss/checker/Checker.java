package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.Value;

import java.util.HashMap;

public class Checker {

    private HashMap<String, Value> symboltable;

    public void check(AST ast) {
        //Clear
        symboltable = new HashMap<>();

        //DO THE CHECKING HERE...

        //Save the symboltable.
        ast.symboltable = symboltable;
        //Save the verdict
        if (ast.getErrors().isEmpty()) {
            ast.checked = true;
        }
    }

    public enum ValueType {
        PIXELVALUE,
        PERCENTAGE,
        COLORVALUE,
        UNDEFINED
    }
}
