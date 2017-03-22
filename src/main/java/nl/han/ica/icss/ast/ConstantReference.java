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

import nl.han.ica.icss.checker.errors.NullReferenceError;

import java.util.List;

public class ConstantReference extends Value {

    public String name;
    public Assignment assignment; // the assignment that makes this constantReference

    public ConstantReference(String name, Assignment assignment) {
        super();
        this.name = name;
        this.assignment = assignment;
    }

    @Override
    public String getNodeLabel() {
        return "ConstantReference(" + name + ")";
    }

    @Override
    public void check() {
        if(assignment == null)
            addError(new NullReferenceError("Reference is referencing an unassigned constant."));
    }

    @Override
    public Type getType(List<ASTNode> vistitedNodes) {
        if(containsreal(vistitedNodes, this))
            return Type.UNKNOWN;
        vistitedNodes.add(this);
        return assignment == null ? Type.UNKNOWN : assignment.value.getType(vistitedNodes);
    }

    @Override
    public boolean containsReferenceTo(ConstantReference ref, List<ASTNode> vistitedNodes) {
        if (name.equals(ref.name))
            return true;
        if (containsreal(vistitedNodes, this))
            return true;
        vistitedNodes.add(this);
        return assignment != null && assignment.value.containsReferenceTo(ref, vistitedNodes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstantReference)) return false;

        ConstantReference reference = (ConstantReference) o;

        return name.equals(reference.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
