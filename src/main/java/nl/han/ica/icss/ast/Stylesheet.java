/*
 * Alle code zoals aangeleverd door ICA HAN is eigendom van ICA HAN. Deze code is te zien in het eerste commit van deze repository. Alle wijzigingen en uitbreidingen n� het eerste commit vallen onder de MIT Licentie:
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A stylesheet is the root node of the AST, it consists of one or more statements
 */
public class Stylesheet extends ASTNode {

    public ArrayList<ASTNode> body;

    public Stylesheet() {
        this.body = new ArrayList<>();
    }

    public Stylesheet(ArrayList<ASTNode> body) {
        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "Stylesheet";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        return this.body;
    }

    @Override
    public void addChild(ASTNode child) {
        body.add(child);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Sylesheet(");
        for(ASTNode node : body)
            builder.append(System.lineSeparator()).append(node);
        builder.append(")");
        return builder.toString();
    }

    public void check() {
        body.forEach(ASTNode::check);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stylesheet)) return false;

        Stylesheet that = (Stylesheet) o;

        return body.equals(that.body);

    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }
}
