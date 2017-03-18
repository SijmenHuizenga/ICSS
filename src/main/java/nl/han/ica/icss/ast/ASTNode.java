package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.SemanticError;

import java.util.ArrayList;

public abstract class ASTNode {

    private SemanticError error = null;

    /*
     This method is used in the GUI to create an appropriate label
     in the tree visualisation.
      */
    public String getNodeLabel() {
        return "ASTNode";
    }

    /*
     Different AST nodes use different attributes to store their children.
     This method provides a unified interface.
     */
    public ArrayList<ASTNode> getChildren() {
        return new ArrayList<>();
    }

    ;

    /*
    By implementing this method in a subclass you can easily create AST nodes
      incrementally.
    */
    public void addChild(ASTNode child) {
    }

    public SemanticError getError() {
        return error;
    }

    public void setError(String description) {
        this.error = new SemanticError(description);
    }

    public boolean hasError() {
        return error != null;
    }

    @Override
    public String toString() {
        return getNodeLabel();
    }

    public abstract void check();
}
