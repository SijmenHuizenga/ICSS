package nl.han.ica.icss.ast;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import nl.han.ica.icss.checker.errors.OperationTypeError;
import org.junit.Test;
import org.junit.runner.RunWith;

import static nl.han.ica.icss.ast.Asserts.assertContainsType;
import static org.junit.Assert.assertNull;

/**
 * Created by Sijmen on 21-3-2017.
 *
 * WE DO NOT NEED TO TEST VARIABLES OR OPERATIONS IN THE VALUES
 * because 'getType' is uesd, we can trust that if we test matching of
 * decleration-types and valuetypes, also the types of operations and variables will be ok
 */
@RunWith(DataProviderRunner.class)
public class OperationTypesCheckingTest {

    @DataProvider
    public static Object[][] equlaityDataProvider(){
        return new Object[][]{
                {new ColorLiteral("#123456"), new ColorLiteral("#AAAAAA"), true},
                {new PixelLiteral("123456px"), new PixelLiteral("12x"), true},
                {new PercentageLiteral("50%"), new PercentageLiteral("60%"), true},

                {new ColorLiteral("#123456"), new PixelLiteral(50), false},
                {new PixelLiteral(55), new PercentageLiteral(12), false},
                {new PercentageLiteral(66), new ColorLiteral("#FFFFFF"), false},
        };
    }

    @Test
    @UseDataProvider("equlaityDataProvider")
    public void testOperationEqualityCheck(Value left, Value right, boolean isOkay) throws Exception {
        Operation operation = new Operation(left, Operation.Operator.PLUS, right);
        operation.check();
        if(isOkay)
            assertNull(operation.getErrors());
        else
            assertContainsType(OperationTypeError.class, operation.getErrors());
    }
}
