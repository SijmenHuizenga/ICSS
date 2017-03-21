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

package nl.han.ica.icss.ast.constants;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.PixelLiteral;
import nl.han.ica.icss.ast.Stylerule;
import nl.han.ica.icss.astfactory.ConstantFactory;
import nl.han.ica.icss.astfactory.LiteralFactory;
import nl.han.ica.icss.astfactory.StyleRuleFactory;
import nl.han.ica.icss.astfactory.ValueFactory;
import nl.han.ica.icss.checker.errors.DuplicateAssignmentError;
import org.junit.Before;
import org.junit.Test;

import static nl.han.ica.icss.ast.Asserts.assertContainsAllType;
import static org.junit.Assert.assertEquals;


/**
 * Created by Sijmen on 21-3-2017.
 */
public class DuplicateAssignmentCheckerTest {

    ConstantFactory constantFactory;
    LiteralFactory literalFactory;
    ValueFactory valueFactory;
    StyleRuleFactory styleRuleFactory;

    ConstantFactory factory;
    AST ast;

    @Before
    public void setUp() throws Exception {
        factory = new ConstantFactory();
        ast = new AST();

        constantFactory = new ConstantFactory();
        literalFactory = new LiteralFactory();
        valueFactory = new ValueFactory(literalFactory, constantFactory);
        styleRuleFactory = new StyleRuleFactory(valueFactory);
    }

    /**
     * $a = 50
     * $a = 60
     *
     * both give an error
     */
    @Test
    public void testDuplicateAssignments() throws Exception {
        ast.root.addChild(factory.makeAssignment("A", new PixelLiteral(50)));
        ast.root.addChild(factory.makeAssignment("A", new PixelLiteral(60)));

        ast.check();

        assertContainsAllType(DuplicateAssignmentError.class, ast.getErrors());
        assertEquals(2, ast.getErrors().size());
    }

    /**
     * #id {
     *     $a = 50px;
     *     .id2 {
     *         $a = 100px;
     *     }
     * }
     *
     * both $a assignments give an error
     */
    @Test
    public void testDuplicateAssignmentsInsideBlocks() throws Exception {
        Stylerule idBlock2 = styleRuleFactory.make(".id2");
        idBlock2.addChild(factory.makeAssignment("a", new PixelLiteral(100)));

        Stylerule idBlock = styleRuleFactory.make("#id");
        idBlock.addChild(factory.makeAssignment("a", new PixelLiteral(50)));
        idBlock.addChild(idBlock2);

        ast.root.addChild(idBlock);

        ast.check();

        assertContainsAllType(DuplicateAssignmentError.class, ast.getErrors());
        assertEquals(2, ast.getErrors().size());
    }
}
