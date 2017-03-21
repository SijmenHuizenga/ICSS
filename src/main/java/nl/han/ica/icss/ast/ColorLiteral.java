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


import nl.han.ica.icss.checker.errors.InvalidDataStateError;

import java.awt.Color;
import java.util.List;

public class ColorLiteral extends Literal {

    public String value;

    public ColorLiteral(String value) {
        this.value = value.substring(1);
    }

    @Override
    public String getNodeLabel() {
        return "Colorliteral(" + value + ")";
    }

    @Override
    public void check() {
        try{
            new Color(
                    Integer.valueOf(value.substring( 0, 2 ), 16 ),
                    Integer.valueOf(value.substring( 2, 4 ), 16 ),
                    Integer.valueOf(value.substring( 4, 6 ), 16 ) );
        }catch (Exception e){
            addError(new InvalidDataStateError("Color " + value + " is not a valid color"));
        }
    }

    @Override
    public Type getType(List<ASTNode> visited) {
        return Type.COLOR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColorLiteral)) return false;

        ColorLiteral that = (ColorLiteral) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
