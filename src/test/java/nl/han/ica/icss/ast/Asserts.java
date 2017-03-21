package nl.han.ica.icss.ast;

import org.junit.internal.ArrayComparisonFailure;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sijmen on 21-3-2017.
 */
public class Asserts {

    /**
     * Check if the given arraylist contains at least 1 object of the given type
     */
    public static void assertContainsType(Class expectedType, ArrayList<? extends Object> objects) {
        if(objects == null || objects.isEmpty())
            throw new ArrayComparisonFailure(
                    "Expected array containing type " + expectedType + " but got empty array.",
                    new AssertionError("Input array " + (objects == null ? "null" : Arrays.toString(objects.toArray())) + " is empty."), -1);
        for(Object o : objects){
            if(o == null)
                continue;
            if(o.getClass().isAssignableFrom(expectedType))
                return;
        }
        throw new ArrayComparisonFailure(
                "Expected array containing type " + expectedType + " but the given array did not contain any of the given type.",
                new AssertionError("Input array " + Arrays.toString(objects.toArray())), -1);
    }

    /**
     * Check if all the items in the given arraylist are assignable from the given type
     */
    public static void assertContainsAllType(Class expectedType, ArrayList<? extends Object> objects) {
        if(objects == null || objects.isEmpty())
            return;
        for (int i = 0; i < objects.size(); i++) {
            Object o = objects.get(i);
            if (o == null)
                throw new ArrayComparisonFailure(
                        "Expected array item containing type " + expectedType + " but got null",
                        new AssertionError("Input array " + Arrays.toString(objects.toArray())), i);
            if (!o.getClass().isAssignableFrom(expectedType))
                throw new ArrayComparisonFailure(
                        "Expected array item containging type " + expectedType + " but item was type " + o.getClass(),
                        new AssertionError("Input array " + Arrays.toString(objects.toArray())), i);
        }
    }

}
