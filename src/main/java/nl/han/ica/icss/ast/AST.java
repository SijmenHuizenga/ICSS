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

import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.checker.errors.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class AST {
    //The root of the tree
    public Stylesheet root;

    //The symbol table
    public HashMap<String, Value> symboltable;

    //Keep track of whethere this AST was typechecked
    public boolean checked;

    public AST() {
        root = new Stylesheet();
        symboltable = new HashMap<>();
        checked = false;
    }

    public AST(Stylesheet root) {
        super();
        this.root = root;
    }

    public void check(){
        Checker checker = new Checker();
        checker.check(this);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setRoot(Stylesheet stylesheet) {
        root = stylesheet;
    }

    public ArrayList<SemanticError> getErrors() {
        ArrayList<SemanticError> errors = new ArrayList<>();
        collectErrors(new ArrayList<>(), errors, root);
        return errors;
    }

    private void collectErrors(ArrayList<ASTNode> vistiedNodes, ArrayList<SemanticError> errors, ASTNode node) {
        if (node.hasError()) {
            for (SemanticError semanticError : node.getErrors()) {
                if (!errors.contains(semanticError))
                    errors.add(semanticError);
            }
        }
        vistiedNodes.add(node);
        for (ASTNode child : node.getChildren())
            if(!Value.containsreal(vistiedNodes, child))
                collectErrors(vistiedNodes, errors, child);
    }

    @Override
    public String toString() {
        return "AST{" +
                "root=" + root +
                ", symboltable=" + symboltable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AST)) return false;

        AST ast = (AST) o;

        if (checked != ast.checked) return false;
        if (root != null ? !root.equals(ast.root) : ast.root != null) return false;
        return !(symboltable != null ? !symboltable.equals(ast.symboltable) : ast.symboltable != null);

    }

    @Override
    public int hashCode() {
        int result = root != null ? root.hashCode() : 0;
        result = 31 * result + (symboltable != null ? symboltable.hashCode() : 0);
        result = 31 * result + (checked ? 1 : 0);
        return result;
    }
}
