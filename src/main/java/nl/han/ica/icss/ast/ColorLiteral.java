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

import nl.han.ica.icss.checker.errors.IllegalColorValueError;
import nl.han.ica.icss.checker.errors.InvalidDataStateError;

import java.awt.Color;
import java.util.List;

public class ColorLiteral extends Literal implements Calculateble<ColorLiteral>{

    public int r, g, b;

    public ColorLiteral(String hex) {
        hex = hex.substring(1);
        this.r = Integer.valueOf(hex.substring(0, 2), 16);
        this.g = Integer.valueOf(hex.substring(2, 4), 16);
        this.b = Integer.valueOf(hex.substring(4, 6), 16);
    }

    public ColorLiteral(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String getNodeLabel() {
        return "Colorliteral(" + r + "," + g + "," + b + ")";
    }

    @Override
    public void check() {
        if(r < 0 || r > 255)
            addError(new IllegalColorValueError("Red color is not between 0 and 255."));
        if(g < 0 || g > 255)
            addError(new IllegalColorValueError("Green color is not between 0 and 255."));
        if(b < 0 || b > 255)
            addError(new IllegalColorValueError("Blue color is not between 0 and 255."));
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

        return r == that.r && g == that.g && b == that.b;
    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        return result;
    }

    @Override
    public Literal add(ColorLiteral other) {
        return new ColorLiteral(
                minmax(this.r + other.r),
                minmax(this.g + other.g),
                minmax(this.b + other.b)
        );
    }

    @Override
    public Literal subtract(ColorLiteral other) {
        return new ColorLiteral(
                minmax(this.r - other.r),
                minmax(this.g - other.g),
                minmax(this.b - other.b)
        );
    }

    @Override
    public Literal devide(ColorLiteral other) {
        return new ColorLiteral(
                this.r / other.r,
                this.g / other.g,
                this.b / other.b
        );
    }

    @Override
    public Literal multiply(ColorLiteral other) {
        return new ColorLiteral(
                minmax(this.r * other.r),
                minmax(this.g * other.g),
                minmax(this.b * other.b)
        );
    }

    private int minmax(int i){
        if(i > 255)
            return 255;
        if(i < 0)
            return 0;
        return i;
    }
}
