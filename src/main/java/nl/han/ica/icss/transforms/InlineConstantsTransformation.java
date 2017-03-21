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

package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;

public class InlineConstantsTransformation implements Transform {
    @Override
    public void apply(AST ast) {
        apply(ast.root);
    }

    private void apply(ASTNode node){
        //search for all nodes that contain Value's. On these values apply @this.getTransformedValue
        if(node instanceof Operation){
            Operation operation = (Operation) node;
            operation.lhs = getTransformedValue(operation.lhs);
            operation.rhs = getTransformedValue(operation.rhs);
        }
        if(node instanceof Declaration){
            Declaration declaration = (Declaration) node;
            declaration.value = getTransformedValue(declaration.value);
        }
        if(node instanceof Assignment){
            ((Assignment) node).value = getTransformedValue(((Assignment) node).value);
        }

        for(ASTNode child : node.getChildren())
            apply(child);
    }

    private Value getTransformedValue(Value rhs) {
        if(rhs == null)
            return null;
        if(rhs instanceof ConstantReference)
            return ((ConstantReference) rhs).assignment.value;
        return rhs;
    }
}
