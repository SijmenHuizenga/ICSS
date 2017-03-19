package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class AST {
    //The root of the tree
    public Stylesheet root;

    //The symbol table
    public HashMap<String, Value> symboltable;

    //Keep track of whethere this AST was typechecked
    public boolean checked;

    public AST() {
        root = new Stylesheet();
        symboltable = new HashMap<>();
        checked = false;
    }

    public AST(Stylesheet root) {
        super();
        this.root = root;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setRoot(Stylesheet stylesheet) {
        root = stylesheet;
    }

    public ArrayList<SemanticError> getErrors() {
        ArrayList<SemanticError> errors = new ArrayList<>();
        collectErrors(errors, root);
        return errors;
    }

    private void collectErrors(ArrayList<SemanticError> errors, ASTNode node) {
        if (node.hasError()) {
            if(errors.contains(node.getError()))
                return;
            errors.add(node.getError());
        }
        for (ASTNode child : node.getChildren())
            collectErrors(errors, child);
    }

    @Override
    public String toString() {
        return "AST{" +
                "root=" + root +
                ", symboltable=" + symboltable +
                '}';
    }
}
