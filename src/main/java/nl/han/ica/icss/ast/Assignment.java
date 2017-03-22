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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;

        Assignment that = (Assignment) o;

        return key.equals(that.key) && value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
