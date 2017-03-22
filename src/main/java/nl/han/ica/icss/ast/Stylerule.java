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

import java.util.ArrayList;

public class Stylerule extends ASTNode {

    public Selector selector;
    public ArrayList<ASTNode> body;

    public Stylerule(Selector selector, ArrayList<ASTNode> body) {
        this.selector = selector;
        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "Stylerule(" + selector + ")";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        return body;
    }

    @Override
    public void addChild(ASTNode child) {
        body.add(child);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Stylerule(").append(selector);
        for(ASTNode node : body)
            builder.append(System.lineSeparator()).append(node);
        builder.append(")");
        return builder.toString();
    }

    @Override
    public void check() {
        selector.check();
        body.forEach(ASTNode::check);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stylerule)) return false;

        Stylerule stylerule = (Stylerule) o;

        if (!selector.equals(stylerule.selector)) return false;
        return !(body != null ? !body.equals(stylerule.body) : stylerule.body != null);

    }

    @Override
    public int hashCode() {
        int result = selector.hashCode();
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }
}
