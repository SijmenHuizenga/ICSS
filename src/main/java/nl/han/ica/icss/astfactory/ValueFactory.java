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

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.Value;
import nl.han.ica.icss.parser.ICSSParser.*;

/**
 * Created by Sijmen on 17-3-2017.
 */
public class ValueFactory {

    private final LiteralFactory literalFactory;
    private final ConstantFactory constantFactory;

    public ValueFactory(LiteralFactory literalFactory, ConstantFactory constantFactory) {
        this.literalFactory = literalFactory;

        this.constantFactory = constantFactory;
    }

    public Value make(CalculatedvalueContext calculatedValue) {
        ValueContext actualValue = calculatedValue.value();
        if(actualValue == null)
            throw new IllegalStateException("Value null. This is impossible!");

        MoreCalculatedValuesContext moreCalcs = calculatedValue.moreCalculatedValues();

        if(moreCalcs == null)
            return make(actualValue);

        return make(actualValue, moreCalcs);
    }

    private Value make(ValueContext left, MoreCalculatedValuesContext right) {
        return new Operation(
                make(left),
                getOperator(right.calcoperator()),
                make(right.calculatedvalue())
        );
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

    private Value make(ValueContext value) {
        if(value.literal() != null)
            return literalFactory.make(value.literal());
        else if(value.constantreference() != null)
            return constantFactory.makeReference(value.constantreference());
        else
            throw new IllegalStateException("ConstantReference AND literal ARE null. This is impossible!");
    }

}
