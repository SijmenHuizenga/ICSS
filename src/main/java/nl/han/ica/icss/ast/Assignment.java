package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.errors.CircularReferenceError;

import java.util.ArrayList;

/**
 * An assignment binds a value to an identifier.
 */
public class Assignment extends ASTNode {

    public ConstantReference key;
    public Value value;

    public Assignment(ConstantReference key, Value value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getNodeLabel() {
        return "Assignment(" + key.name  + " = " + value + ")";
    }

    @Override
    public String toString() {
        return getNodeLabel();
    }

    @Override
    public void check() {
        value.check();
        if(value.containsReferenceTo(key))
            addError(new CircularReferenceError("Circular Reference! Value contains a reference to this key. This is not allowed."));
    }

    @Override
    public void addChild(ASTNode child) {
        value = (Value) child;
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> astNodes = new ArrayList<>();
        astNodes.add(value);
        return astNodes;
    }
}
