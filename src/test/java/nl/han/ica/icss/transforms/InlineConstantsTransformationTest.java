/*
 * Alle code zoals aangeleverd door ICA HAN is eigendom van ICA HAN. Deze code is te zien in het eerste commit van deze repository. Alle wijzigingen en uitbreidingen n� het eerste commit vallen onder de MIT Licentie:
 *
 * Copyright 2017 Sijmen Huizenga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.astfactory.ConstantFactory;
import nl.han.ica.icss.astfactory.LiteralFactory;
import nl.han.ica.icss.astfactory.StyleRuleFactory;
import nl.han.ica.icss.astfactory.ValueFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sijmen on 21-3-2017.
 */
public class InlineConstantsTransformationTest {

    ConstantFactory constfact;
    LiteralFactory literalFactory;
    ValueFactory valueFactory;

    StyleRuleFactory styleRuleFactory;

    AST ast;
    InlineConstantsTransformation transformer;

    @Before
    public void setUp() throws Exception {
        ast = new AST();

        constfact = new ConstantFactory();
        literalFactory = new LiteralFactory();
        valueFactory = new ValueFactory(literalFactory, constfact);
        styleRuleFactory = new StyleRuleFactory(valueFactory);

        transformer = new InlineConstantsTransformation();
    }

    /**
     * input:
     * $a = 540px;
     * $b = $a;
     *
     * output:
     * $a = 540px;
     * $b = 540px;
     *
     */
    @Test
    public void testApplyRefInAssignment() throws Exception {
        Assignment as = constfact.makeAssignment("b", constfact.makeReference("a"));

        ast.root.addChild(constfact.makeAssignment("a", new PixelLiteral(540)));
        ast.root.addChild(as);

        transformer.apply(ast);

        assertEquals(new PixelLiteral(540), as.value);
    }

    /**
     * input:
     * $a = 12%;
     * a {
     *     $b = $a;
     * }
     *
     * output:
     * $a = 12%;
     * a {
     *     $b = 12%;
     * }
     */
    @Test
    public void testApplyNestedRefInAssignment() throws Exception {
        ast.root.addChild(constfact.makeAssignment("a", new PercentageLiteral(12)));

        Stylerule a = styleRuleFactory.make("a");
        ast.root.addChild(a);

        Assignment ba = constfact.makeAssignment("b", constfact.makeReference("a"));
        a.addChild(ba);

        transformer.apply(ast);

        assertEquals(new PercentageLiteral(12), ba.value);
    }

    /**
     * input:
     * $c = #FFFFFF;
     * #a {
     *     color: $c;
     * }
     *
     * output:
     * $c = #FFFFFF;
     * #a {
     *     color: #FFFFFF;
     * }
     */
    @Test
    public void testApplyRefInDecleration() throws Exception {
        ast.root.addChild(constfact.makeAssignment("c", new ColorLiteral("#FFFFFF")));
        Stylerule idA = styleRuleFactory.make("#a");
        ast.root.addChild(idA);
        Declaration decl = new Declaration(Declaration.Type.COLOR, constfact.makeReference("c"));
        idA.addChild(decl);

        transformer.apply(ast);

        assertEquals(new ColorLiteral("#FFFFFF"), decl.value);
    }

    /**
     * input:
     *
     * #a {
     *     #b {
     *         $c = #FFFFFF;
     *         color: $c;
     *     }
     * }
     *
     * output:
     * #a {
     *     #b {
     *         $c = #FFFFFF;
     *         color: #FFFFFF;
     *     }
     * }
     */
    @Test
    public void testApplyRefInNestedDecleration() throws Exception {
        Stylerule idA = styleRuleFactory.make("#a");
        ast.root.addChild(idA);

        Stylerule idB = styleRuleFactory.make("#a");
        idA.addChild(idB);

        idB.addChild(constfact.makeAssignment("c", new ColorLiteral("#FFFFFF")));

        Declaration decl = new Declaration(Declaration.Type.COLOR, constfact.makeReference("c"));
        idB.addChild(decl);

        transformer.apply(ast);

        assertEquals(new ColorLiteral("#FFFFFF"), decl.value);
    }

    /**
     * input:
     * $b = 20%;
     * $a = $b + 10%;
     *
     * output:
     * $b = 20%;
     * $a = $20% + 10%;
     */
    @Test
    public void testApplyRefInOperation() throws Exception {
        ast.root.addChild(constfact.makeAssignment("b", new PercentageLiteral(20)));
        Operation ops = new Operation(
                constfact.makeReference("b"),
                Operation.Operator.PLUS,
                new PercentageLiteral(20)
        );
        ast.root.addChild(constfact.makeAssignment("a", ops));

        transformer.apply(ast);

        assertEquals(new PercentageLiteral(20), ops.lhs);
    }

    /**
     * input:
     *   $a = 5%;
     *   $b = 10%;
     *   $c = $a + $b + 2%;
     *
     * output:
     *   $a = 5%;
     *   $b = 10%;
     *   $a = 5% + 10% + 2%;
     */
    @Test
    public void testApplyRefInNestedOperation() throws Exception {
        ast.root.addChild(constfact.makeAssignment("a", new PercentageLiteral(5)));
        ast.root.addChild(constfact.makeAssignment("b", new PercentageLiteral(10)));
        Operation opsInner = new Operation(
                constfact.makeReference("b"),
                Operation.Operator.PLUS,
                new PercentageLiteral(2)
        );

        Operation ops = new Operation(
                constfact.makeReference("a"),
                Operation.Operator.PLUS,
                opsInner
        );
        ast.root.addChild(constfact.makeAssignment("c", ops));

        transformer.apply(ast);

        assertEquals(new PercentageLiteral(5), ops.lhs);
        assertEquals(new PercentageLiteral(10), opsInner.lhs);
    }

}