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

public class Selector extends ASTNode {

    public String tag;
    public String cls;
    public String id;

    public Selector(String input){
        if(input.startsWith("."))
            setClass(input.substring(1));
        else if(input.startsWith("#"))
            setId(input.substring(1));
        else
            setTag(input);
    }

    @Override
    public String getNodeLabel() {
        return "Selector(" + getSelector() + ")";
    }

    @Override
    public void check() {
        if(tag == null && cls == null && id == null)
            addError(new InvalidDataStateError("TAG, CLASS and ID are all null. This should be impossible!"));
    }

    public String getSelector() {
        if (tag != null)
            return tag;
        else if (cls != null)
            return "."+cls;
        else
            return "#"+id;
    }

    public Selector setClass(String cls) {
        this.cls = cls;
        return this;
    }

    public Selector setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Selector setId(String id) {
        this.id = id;
        return this;
    }
}
