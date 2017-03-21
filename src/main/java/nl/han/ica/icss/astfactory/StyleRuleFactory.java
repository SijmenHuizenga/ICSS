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

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.parser.ICSSParser.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sijmen on 18-3-2017.
 */
public class StyleRuleFactory {

    private ValueFactory valueFactory;

    public StyleRuleFactory(ValueFactory valueFactory) {
        this.valueFactory = valueFactory;
    }

    public Stylerule make(String selector){
        return new Stylerule(makeSelector(selector), new ArrayList<>());
    }

    public Stylerule make(StyleruleContext ctx) {
        return new Stylerule(
                make(ctx.selector()),
                make(ctx.declerationpart())
        );
    }

    private ArrayList<ASTNode> make(List<DeclerationpartContext> declerationparts) {
        ArrayList<ASTNode> nodes = new ArrayList<>();
        if(declerationparts == null)
            return nodes;
        for(DeclerationpartContext dpc : declerationparts)
            nodes.add(make(dpc));
        return nodes;
    }

    private ASTNode make(DeclerationpartContext dpc) {
        if(dpc.decleration() != null)
            return make(dpc.decleration());
        else if(dpc.stylerule() != null)
            return make(dpc.stylerule());
        throw new IllegalArgumentException("DECLERATION and STYLERULE are both null. This is not possible.");
    }

    private ASTNode make(DeclerationContext decleration) {
        return new Declaration(make(decleration.attribute()),
                valueFactory.make(decleration.calculatedvalue()));
    }

    private Declaration.Type make(AttributeContext attribute) {
        if(attribute.ATTRIBUTE_BACKGROUND_COLOR() != null)
            return Declaration.Type.BACKGROUND_COLOR;
        if(attribute.ATTRIBUTE_COLOR() != null)
            return Declaration.Type.COLOR;
        if(attribute.ATTRIBUTE_HEIGHT() != null)
            return Declaration.Type.HEIGHT;
        if(attribute.ATTRIBUTE_WIDTH() != null)
            return Declaration.Type.WIDTH;
        throw new IllegalArgumentException("unknown attribte type");
    }

    private Selector make(SelectorContext selector) {
        if(selector.SELECTOR_CLASS() != null)
            return new Selector(selector.SELECTOR_CLASS().toString());
        if(selector.SELECTOR_ELEEMNT() != null)
            return new Selector(selector.SELECTOR_ELEEMNT().toString());
        if(selector.SELECTOR_ID() != null)
            return new Selector(selector.SELECTOR_ID().toString());
        return new Selector(null);
    }

    private Selector makeSelector(String selector){
        return new Selector(selector);
    }


}
