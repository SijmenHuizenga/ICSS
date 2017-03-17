package nl.han.ica.icss.ast;

/**
 * An assignment binds a value to an identifier.
 */
public class Assignment extends ASTNode {

    public ConstantReference name;
    public Value value;

    @Override
    public String getNodeLabel() {
        return "Assignment (" + name.name + ")";
    }

    @Override
    public void addChild(ASTNode child) {
        value = (Value) child;
    }
}
