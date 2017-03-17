package nl.han.ica.icss.ast;

import java.util.ArrayList;

public class Operation extends Value {

    public Operator operator;

    ;
    public Value lhs;
    public Value rhs;

    @Override
    public String getNodeLabel() {
        return "Operation (" + operator.toString() + ")";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(lhs);
        children.add(rhs);
        return children;
    }

    @Override
    public void addChild(ASTNode child) {
        if (lhs == null) {
            lhs = (Value) child;
        } else if (rhs == null) {
            rhs = (Value) child;
        }
    }

    public static enum Operator {PLUS, MIN}
}
