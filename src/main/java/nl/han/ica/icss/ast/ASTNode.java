package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.errors.SemanticError;

import java.util.ArrayList;

public abstract class ASTNode {

    private ArrayList<SemanticError> errors = null;

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

    /*
    By implementing this method in a subclass you can easily create AST nodes
      incrementally.
    */
    public void addChild(ASTNode child) {
    }

    public ArrayList<SemanticError> getErrors() {
        return errors;
    }

    public void addError(SemanticError error) {
        if(this.errors == null)
            this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public boolean hasError() {
        return errors != null && !errors.isEmpty();
    }

    @Override
    public String toString() {
        return getNodeLabel();
    }

    public abstract void check();
}
