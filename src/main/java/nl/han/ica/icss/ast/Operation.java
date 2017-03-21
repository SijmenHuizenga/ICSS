/*
 * Alle code zoals aangeleverd door ICA HAN is eigendom van ICA HAN. Deze code is te zien in het eerste commit van deze repository. Alle wijzigingen en uitbreidingen ná het eerste commit vallen onder de MIT Licentie:
 *
 * Copyright 2017 Sijmen Huizenga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
