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

import nl.han.ica.icss.ast.Assignment;
import nl.han.ica.icss.ast.ConstantReference;
import nl.han.ica.icss.ast.PixelLiteral;
import nl.han.ica.icss.astfactory.ConstantFactory;
import nl.han.ica.icss.checker.errors.NullReferenceError;
import org.junit.Test;

import static nl.han.ica.icss.ast.Asserts.assertContainsType;
import static org.junit.Assert.assertNull;

/**
 * Created by Sijmen on 21-3-2017.
 */
public class NullConstantReferenceCheckTest {

    /**
     *  ref to $a;
     *  $a = 20px;
     *  is valide
     */
    @Test
    public void testValidReferenceFirstRefThanAssignment() throws Exception {
        ConstantFactory factory = new ConstantFactory();
        ConstantReference reference = factory.makeReference("A");
        Assignment assignment = factory.makeAssignment("A", new PixelLiteral(50));

        assignment.check();
        reference.check();

        assertNull(assignment.getErrors());
        assertNull(reference.getErrors());
    }

    /**
     *  $a = 20px;
     *  ref to $a;
     *  is valide
     */
    @Test
    public void testValidReferenceFirstAssignemntThanReference() throws Exception {
        ConstantFactory factory = new ConstantFactory();
        Assignment assignment = factory.makeAssignment("A", new PixelLiteral(50));
        ConstantReference reference = factory.makeReference("A");

        assignment.check();
        reference.check();

        assertNull(assignment.getErrors());
        assertNull(reference.getErrors());
    }

    /**
     * reference to constant that does not exist
     */
    @Test
    public void testInvalidReference() throws Exception {
        ConstantFactory factory = new ConstantFactory();
        ConstantReference reference = factory.makeReference("A");

        reference.check();

        assertContainsType(NullReferenceError.class, reference.getErrors());
    }

    /**
     * reference to constant that references a constant that does not exist that does not exist
     *   $a = $b
     * $b should be invalid because referencing a non-exisitng constant
     */
    @Test
    public void testRecursive_AisA() throws Exception {
        ConstantFactory factory = new ConstantFactory();
        ConstantReference referenceToB = factory.makeReference("B");
        Assignment assignment = factory.makeAssignment("A", referenceToB);

        assignment.check();

        assertContainsType(NullReferenceError.class, assignment.value.getErrors());
    }

}
