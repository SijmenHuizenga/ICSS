package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.errors.SemanticError;
import org.junit.internal.ArrayComparisonFailure;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sijmen on 21-3-2017.
 */
public class Asserts {

    public static void assertContainsType(Class<? extends SemanticError> expectedError, ArrayList<SemanticError> errors) {
        if(errors == null || errors.isEmpty())
            throw new ArrayComparisonFailure(
                    "Expected array containing type " + expectedError + " but got empty array.",
                    new AssertionError("Input array " + (errors == null ? "null" : Arrays.toString(errors.toArray())) + " is empty."), -1);
        for(SemanticError error : errors){
            if(error == null)
                continue;
            if(error.getClass().isAssignableFrom(expectedError))
                return;
        }
        throw new ArrayComparisonFailure(
                "Expected array containing type " + expectedError + " but the given array did not contain any of the given type.",
                new AssertionError("Input array " + Arrays.toString(errors.toArray())), -1);
    }

    public static void assertContainsAllType(Class<? extends SemanticError> expectedError, ArrayList<SemanticError> errors) {
        if(errors == null || errors.isEmpty())
            return;
        for (int i = 0; i < errors.size(); i++) {
            SemanticError error = errors.get(i);
            if (error == null)
                throw new ArrayComparisonFailure(
                        "Expected array item containing type " + expectedError + " but got null",
                        new AssertionError("Input array " + Arrays.toString(errors.toArray())), i);
            if (!error.getClass().isAssignableFrom(expectedError))
                throw new ArrayComparisonFailure(
                        "Expected array item containging type " + expectedError + " but item was type " + error.getClass(),
                        new AssertionError("Input array " + Arrays.toString(errors.toArray())), i);
        }
    }

}
