package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.errors.CircularReferenceError;
import org.junit.Test;

import static nl.han.ica.icss.ast.Asserts.assertContainsType;

/**
 * Created by Sijmen on 20-3-2017.
 */
public class AssignmentCircularityTest {

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

        assignment.check();

        assertContainsType(CircularReferenceError.class, assignment.getErrors());
    }

    /**
     * circular over multiple constants
     *   $a = $b;
     *   $b = $a;
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

        assignmentA.check();
        assignmentB.check();

        assertContainsType(CircularReferenceError.class, assignmentA.getErrors());
        assertContainsType(CircularReferenceError.class, assignmentB.getErrors());
    }

    /**
     * circular within operation within assignment
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

        assignment.check();

        assertContainsType(CircularReferenceError.class, assignment.getErrors());
    }
}