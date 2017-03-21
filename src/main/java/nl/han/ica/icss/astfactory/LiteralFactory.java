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
