package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.Value;
import nl.han.ica.icss.parser.ICSSParser;
import nl.han.ica.icss.parser.ICSSParser.*;

/**
 * Created by Sijmen on 17-3-2017.
 */
public class ValueFactory {

    private final LiteralFactory literalFactory;
    private final ConstantReferenceFactory constantReferenceFactory;

    public ValueFactory(LiteralFactory literalFactory, ConstantReferenceFactory constantReferenceFactory) {
        this.literalFactory = literalFactory;

        this.constantReferenceFactory = constantReferenceFactory;
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
            return constantReferenceFactory.make(value.constantreference());
        else
            throw new IllegalStateException("ConstantReference AND literal ARE null. This is impossible!");
    }

}
