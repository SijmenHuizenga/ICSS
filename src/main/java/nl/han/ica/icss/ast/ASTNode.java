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

import nl.han.ica.icss.checker.errors.SemanticError;

import java.util.ArrayList;

public abstract class ASTNode {

    private ArrayList<SemanticError> errors = null;

    /*
     This method is used in the GUI to create an appropriate label
     in the tree visualisation.
      */
    public String getNodeLabel() {
        return "ASTNode";
    }

    /*
     Different AST nodes use different attributes to store their children.
     This method provides a unified interface.
     */
    public ArrayList<ASTNode> getChildren() {
        return new ArrayList<>();
    }

    /*
    By implementing this method in a subclass you can easily create AST nodes
      incrementally.
    */
    public void addChild(ASTNode child) {
    }

    public ArrayList<SemanticError> getErrors() {
        return errors;
    }

    public void addError(SemanticError error) {
        if(this.errors == null)
            this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public boolean hasError() {
        return errors != null && !errors.isEmpty();
    }

    @Override
    public String toString() {
        return getNodeLabel();
    }

    public abstract void check();
}
