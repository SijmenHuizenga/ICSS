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

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.Value;
import nl.han.ica.icss.parser.ICSSParser;

/**
 * Created by Sijmen on 18-3-2017.
 */
public class ASTFactory {

    AST ast = new AST();

    ConstantFactory constantFactory = new ConstantFactory();
    LiteralFactory literalFactory = new LiteralFactory();

    ValueFactory valueFactory = new ValueFactory(literalFactory, constantFactory);

    StyleRuleFactory styleRuleFactory = new StyleRuleFactory(valueFactory);

    public void addConstantDecleration(ICSSParser.ConstantassignmentContext ctx){
        Value value = valueFactory.make(ctx.calculatedvalue());
        ast.root.addChild(constantFactory.makeAssignment(ctx.constantreference(), value));
    }

    public AST getAst() {
        return ast;
    }

    public void addStyleRule(ICSSParser.StyleruleContext ctx) {
        ast.root.addChild(styleRuleFactory.make(ctx));
    }
}
