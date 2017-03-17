package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.Value;
import nl.han.ica.icss.parser.ICSSParser.*;

/**
 * Created by Sijmen on 17-3-2017.
 */
public class ValueFactory {

    public static Value getValue(CalculatedvalueContext calculatedValue) {
        ValueContext actualValue = calculatedValue.value();
        if(actualValue == null)
            throw new IllegalStateException("Value null. This is impossible!");

        MoreCalculatedValuesContext moreCalculatedValuesContext = calculatedValue.moreCalculatedValues();
        if(moreCalculatedValuesContext == null)
            return getValue(actualValue);

        //todo: next implement calculations

        return null;
    }

    private static Value getValue(ValueContext value) {
        if(value.literal() != null)
            return LiteralFactory.make(value.literal());
        else if(value.constantreference() != null)
            return ConstantReferenceFactory.make(value.constantreference());
        else
            throw new IllegalStateException("ConstantReference AND literal ARE null. This is impossible!");
    }

}
