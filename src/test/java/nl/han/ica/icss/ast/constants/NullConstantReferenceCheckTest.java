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
