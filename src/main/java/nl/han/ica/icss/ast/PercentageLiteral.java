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

import java.util.List;

public class PercentageLiteral extends Literal implements Calculateble<PercentageLiteral>{
    public int value;

    public PercentageLiteral(int value) {
        this.value = value;
    }

    public PercentageLiteral(String text) {
        this.value = Integer.parseInt(text.substring(0, text.length() - 1));
    }

    @Override
    public String getNodeLabel() {
        return "PercentageLiteral (" + value + ")";
    }

    @Override
    public void check() {
        if(value < 0)
            addError(new InvalidDataStateError("Percentage "+value+" is smaller than 0"));
    }

    @Override
    public Type getType(List<ASTNode> visited) {
        return Type.PERCENTAGE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PercentageLiteral)) return false;

        PercentageLiteral that = (PercentageLiteral) o;

        return value == that.value;
    }

    @Override
    public String toString() {
        return value + "%";
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public Literal add(PercentageLiteral other) {
        return new PercentageLiteral(this.value + other.value);
    }

    @Override
    public Literal subtract(PercentageLiteral other) {
        return new PercentageLiteral(this.value - other.value);
    }

    @Override
    public Literal devide(PercentageLiteral other) {
        return new PercentageLiteral(this.value / other.value);
    }

    @Override
    public Literal multiply(PercentageLiteral other) {
        return new PercentageLiteral(this.value * other.value);
    }
}
