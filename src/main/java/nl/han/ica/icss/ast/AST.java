package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.checker.errors.SemanticError;

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

    public void check(){
        Checker checker = new Checker();
        checker.check(this);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setRoot(Stylesheet stylesheet) {
        root = stylesheet;
    }

    public ArrayList<SemanticError> getErrors() {
        ArrayList<SemanticError> errors = new ArrayList<>();
        collectErrors(new ArrayList<>(), errors, root);
        return errors;
    }

    private void collectErrors(ArrayList<ASTNode> vistiedNodes, ArrayList<SemanticError> errors, ASTNode node) {
        if (node.hasError()) {
            for (SemanticError semanticError : node.getErrors()) {
                if (!errors.contains(semanticError))
                    errors.add(semanticError);
            }
        }
        vistiedNodes.add(node);
        for (ASTNode child : node.getChildren())
            if(!vistiedNodes.contains(child))
                collectErrors(vistiedNodes, errors, child);
    }

    @Override
    public String toString() {
        return "AST{" +
                "root=" + root +
                ", symboltable=" + symboltable +
                '}';
    }
}
