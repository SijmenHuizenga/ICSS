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

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.errors.CircularReferenceError;
import org.junit.Before;
import org.junit.Test;

import static nl.han.ica.icss.ast.Asserts.assertContainsAllType;
import static nl.han.ica.icss.ast.Asserts.assertContainsType;
import static org.junit.Assert.assertEquals;

/**
 * Created by Sijmen on 20-3-2017.
 */
public class CircularAssignmentCheckTest {

    AST ast;

    @Before
    public void setUp() throws Exception {
        ast = new AST();
    }

    /**
     * circular within assignment
     *   $a = $a
     */
    @Test
    public void testRecursive_AisA() throws Exception {
        ConstantReference ref1 = new ConstantReference("a", null);
        ConstantReference ref2 = new ConstantReference("a", null);

        Assignment assignment = new Assignment(ref1, ref2);
        ref1.assignment = assignment;
        ref2.assignment = assignment;

        ast.root.addChild(assignment);
        ast.check();

        assertContainsType(CircularReferenceError.class, ast.getErrors());
    }

    /**
     * circular over multiple constants
     *   $a = $b;
     *   $b = $a;
     *
     *   both assignments should give an errror
     */
    @Test
    public void testRecursive_AisB_BisA() throws Exception {
        ConstantReference refa1 = new ConstantReference("a", null);
        ConstantReference refa2 = new ConstantReference("a", null);
        ConstantReference refb1 = new ConstantReference("b", null);
        ConstantReference refb2 = new ConstantReference("b", null);

        Assignment assignmentA = new Assignment(refa1, refb2);
        refa1.assignment = assignmentA;
        refa2.assignment = assignmentA;

        Assignment assignmentB = new Assignment(refb1, refa2);
        refb1.assignment = assignmentB;
        refb2.assignment = assignmentB;

        ast.root.addChild(assignmentA);
        ast.root.addChild(assignmentB);
        ast.check();

        assertContainsAllType(CircularReferenceError.class, ast.getErrors());
        assertEquals(2, ast.getErrors().size());
    }

    /**
     * circular within opr within assignment
     *   $a = 100px + $a;
     */
    @Test
    public void testRecursive_Ais__Literal_Plus_A() throws Exception {
        ConstantReference ref1 = new ConstantReference("a", null);
        ConstantReference ref2 = new ConstantReference("a", null);

        Operation operation = new Operation(
                new PixelLiteral(100),
                Operation.Operator.PLUS,
                ref2
        );

        Assignment assignment = new Assignment(ref1, operation);
        ref1.assignment = assignment;
        ref2.assignment = assignment;

        ast.root.addChild(assignment);
        ast.check();

        assertContainsType(CircularReferenceError.class, ast.getErrors());
    }
}