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

package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Declaration;
import nl.han.ica.icss.ast.Stylerule;

public class Generator {

    public String generate(AST ast) {
        StringBuilder builder = new StringBuilder();

        for(ASTNode node : ast.root.getChildren()){
            if(node instanceof Stylerule)
                addStylerule((Stylerule) node, builder, "");
        }

        return builder.toString();
    }

    private void addStylerule(Stylerule stylerule, StringBuilder builder, String prefix) {
        builder.append(prefix)
               .append(stylerule.selector.getSelector())
               .append(" {")
                .append(System.lineSeparator());
        for(ASTNode node : stylerule.getChildren()){
            if(node instanceof Declaration)
                addDecleration(builder, (Declaration) node);
        }
        builder.append('}')
                .append(System.lineSeparator());

        for(ASTNode node : stylerule.getChildren()){
            if(node instanceof Stylerule)
                addStylerule((Stylerule) node, builder, prefix + stylerule.selector.getSelector() + " > ");
        }
    }

    private void addDecleration(StringBuilder builder, Declaration node) {
        builder.append("    ")
                .append(node.property)
                .append(": ")
                .append(node.value)
                .append(';')
                .append(System.lineSeparator());
    }
}
