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

import nl.han.ica.icss.checker.errors.PropertyTypeError;

import java.util.ArrayList;

/*
 * A Declaration defines a style property. Declarations are things like "width: 100px"
 */
public class Declaration extends ASTNode {

    public Type property;
    public Value value;

    public Declaration(Type property, Value value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public String getNodeLabel() {
        return "Declaration(" + property + ")";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(value);
        return children;
    }

    @Override
    public String toString() {
        return "Declaration(" + property + ":" + value + ")";
    }

    @Override
    public void check() {
        value.check();
        //CH04
        if(!property.accepts(value.getType()))
            addError(new PropertyTypeError("Property " + property + " does not accept valuetype " + value.getType()));

    }

    @Override
    public void addChild(ASTNode child) {
        value = (Value) child;
    }

    public enum Type {

        COLOR(Value.Type.COLOR),
        BACKGROUND_COLOR(Value.Type.COLOR),
        WIDTH(Value.Type.PERCENTAGE, Value.Type.PIXEL),
        HEIGHT(Value.Type.PERCENTAGE, Value.Type.PIXEL);

        private Value.Type[] acceptedtypes;

        Type(Value.Type... acceptedtypes) {
            this.acceptedtypes = acceptedtypes;
        }

        public boolean accepts(Value.Type type) {
            for(Value.Type accepted : acceptedtypes)
                if(type == accepted)
                    return true;
            return false;
        }
    }
}
