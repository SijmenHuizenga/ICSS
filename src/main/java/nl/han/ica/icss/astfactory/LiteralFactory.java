package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.ColorLiteral;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.PercentageLiteral;
import nl.han.ica.icss.ast.PixelLiteral;
import nl.han.ica.icss.parser.ICSSParser.*;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by Sijmen on 17-3-2017.
 */
public class LiteralFactory {

    public Literal make(LiteralContext literal) {
        if(literal.LITERAL_COLOR() != null)
            return makeColor(literal.LITERAL_COLOR());
        else if(literal.LITERAL_PERCENTAGE() != null)
            return makePercentage(literal.LITERAL_PERCENTAGE());
        else if(literal.LITERAL_PIXELS() != null)
            return makePixels(literal.LITERAL_PIXELS());
        else
            throw new IllegalArgumentException("COLOR, PERCENTAGE and PIXELS are all NULL. This is impossible.");
    }

    private ColorLiteral makeColor(TerminalNode terminalNode) {
        return new ColorLiteral(terminalNode.getText());
    }

    private PercentageLiteral makePercentage(TerminalNode terminalNode) {
        return new PercentageLiteral(terminalNode.getText());
    }

    private PixelLiteral makePixels(TerminalNode terminalNode) {
        return new PixelLiteral(terminalNode.getText());
    }
}
