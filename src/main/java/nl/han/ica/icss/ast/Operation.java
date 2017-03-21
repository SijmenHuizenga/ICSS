package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.errors.OperationTypeError;

import java.util.ArrayList;
import java.util.List;

public class Operation extends Value {

    public Operator operator;

    public Value lhs;
    public Value rhs;

    public Operation(Value lhs, Operator operator, Value rhs) {
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public String getNodeLabel() {
        return "Operation("+lhs + " " + operator + " " + rhs + ")";
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

    @Override
    public void check() {
        //CH03
        Type lhsType = lhs.getType();
        Type rhsType = rhs.getType();

        if(lhsType != rhsType || lhsType == Type.MIXED)
            addError(new OperationTypeError("Operation left type " +lhsType + " does not equal the right "+rhsType+" type."));
        lhs.check();
        rhs.check();
    }

    @Override
    public Type getType(List<ASTNode> visitedNodes) {
        if(visitedNodes.contains(this))
            return Type.UNKNOWN;
        visitedNodes.add(this);
        Type lType = lhs.getType(visitedNodes);
        if(lType != rhs.getType(visitedNodes))
            return Type.MIXED;
        return lType;
    }

    @Override
    public boolean containsReferenceTo(ConstantReference ref, List<ASTNode> vistedNodes) {
        if(vistedNodes.contains(this))
            return true;
        vistedNodes.add(this);
        return lhs.containsReferenceTo(ref) || rhs.containsReferenceTo(ref);
    }

    public static enum Operator {
        PLUS, MIN, MUL, DEV
    }
}
