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

package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.Value;
import org.antlr.v4.runtime.tree.ParseTree;

import static nl.han.ica.icss.parser.ICSSParser.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sijmen on 22-3-2017.
 */
public class OperationFactory {

    private ValueFactory valueFactory;

    public OperationFactory(ValueFactory valueFactory) {
        this.valueFactory = valueFactory;
    }

    public Operation make(SomContext som) {
        ArrayList<Object> parts = new ArrayList<>();
        for(int i = 0; i < som.getChildCount(); i++)
            parts.add(makeAstNode(som.getChild(i)));

        List<Operation.Operator> devmuloperators = Arrays.asList(Operation.Operator.DEV, Operation.Operator.MUL);
        List<Operation.Operator> plusminoperators = Arrays.asList(Operation.Operator.PLUS, Operation.Operator.MIN);

        //noinspection StatementWithEmptyBody Need to execute the method while result is true.
        while(combineFirstOperation(parts, devmuloperators));
        //noinspection StatementWithEmptyBody Need to execute the method while result is true.
        while(combineFirstOperation(parts, plusminoperators));

        if(parts.size() != 1)
            throw new IllegalStateException("Could not convert linear tree to 2D tree. Some elements were left!");

        return (Operation) parts.get(0);
    }

    private Object makeAstNode(ParseTree child) {
        if(child instanceof CalcoperatorContext)
            return getOperator((CalcoperatorContext) child);
        if(child instanceof RealvalueContext)
            return valueFactory.make((RealvalueContext) child);
        throw new IllegalStateException("Child is of a illegal type. This is impossible!");
    }

    /**
     * The given parts arraylist contains ONLY Value and Operation.Operator objects.
     */
    private boolean combineFirstOperation(ArrayList<Object> parts, List<Operation.Operator> types){
        for(int i = 1; i < parts.size(); i+=2){
            Operation.Operator operator = (Operation.Operator) parts.get(i);
            if(!types.contains(operator))
                continue;
            Value l = (Value) parts.get(i-1);
            Value r = (Value) parts.get(i+1);

            parts.remove(i);
            parts.remove(i);
            parts.set(i-1, new Operation(l, operator, r));
            return true;
        }
        return false;
    }

    private Operation.Operator getOperator(CalcoperatorContext calcoperator) {
        if(calcoperator.CALCOPERATOR_ADD() != null)
            return Operation.Operator.PLUS;
        else if(calcoperator.CALCOPERATOR_SUB() != null)
            return Operation.Operator.MIN;
        else if(calcoperator.CALCOPERATOR_DEV() != null)
            return Operation.Operator.DEV;
        else if(calcoperator.CALCOPERATOR_MUL() != null)
            return Operation.Operator.MUL;
        throw new IllegalArgumentException("PLUS, MIN, DEV and MUL are all null. This is impssible.");
    }
}
