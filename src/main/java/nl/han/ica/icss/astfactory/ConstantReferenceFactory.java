package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.ConstantReference;
import nl.han.ica.icss.ast.Value;
import nl.han.ica.icss.parser.ICSSParser;

/**
 * Created by Sijmen on 17-3-2017.
 */
public class ConstantReferenceFactory {

    public String getConstantName(ICSSParser.ConstantreferenceContext constant) {
        return constant.getText().substring(1);
    }

    public Value make(ICSSParser.ConstantreferenceContext constantreference) {
        String constantName = getConstantName(constantreference);

        return new ConstantReference(constantName);
    }
}
