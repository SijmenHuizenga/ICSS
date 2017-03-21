package nl.han.ica.icss.ast;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import nl.han.ica.icss.astfactory.ConstantFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sijmen on 21-3-2017.
 */
@RunWith(DataProviderRunner.class)
public class GetTypeTest {

    @DataProvider
    public static Object[][] provideValueTypes() {
        Operation operationPxPlusPx = new Operation(new PixelLiteral(50), Operation.Operator.PLUS, new PixelLiteral(60));
        Operation operationPercPlusPerc = new Operation(new PercentageLiteral(50), Operation.Operator.DEV, new PercentageLiteral(60));
        Operation operationColPlusCol = new Operation(new ColorLiteral("#FFFFFF"), Operation.Operator.MIN, new ColorLiteral("#000000"));
        Operation operationPxPlusPerc =new Operation(new PixelLiteral(50), Operation.Operator.PLUS, new PercentageLiteral(60));
        return new Object[][]{
                {new PixelLiteral(20), Value.Type.PIXEL},
                {new ColorLiteral("#FFFFFF"), Value.Type.COLOR},
                {new PercentageLiteral(50), Value.Type.PERCENTAGE},
                {operationPxPlusPx, Value.Type.PIXEL},
                {operationPercPlusPerc, Value.Type.PERCENTAGE},
                {operationColPlusCol, Value.Type.COLOR},
                {operationPxPlusPerc, Value.Type.MIXED},
                {new Operation(operationPxPlusPx, Operation.Operator.PLUS, new PixelLiteral(50)), Value.Type.PIXEL},
                {new Operation(operationPxPlusPx, Operation.Operator.PLUS, new PercentageLiteral(50)), Value.Type.MIXED},
                {new Operation(operationPxPlusPerc, Operation.Operator.PLUS, new PixelLiteral(50)), Value.Type.MIXED},
                {new Operation(operationPxPlusPx, Operation.Operator.PLUS, new Operation(
                        new PixelLiteral(50), Operation.Operator.PLUS, new PixelLiteral(60)
                    )), Value.Type.PIXEL},

        };
    }

    @Test
    @UseDataProvider("provideValueTypes")
    public void testGetType(Value value, Value.Type expectedType){
        assertEquals(expectedType, value.getType());
    }

    @Test
    @UseDataProvider("provideValueTypes")
    public void testReferenceGetType(Value value, Value.Type expectedType) throws Exception {
        ConstantFactory factory = new ConstantFactory();

        factory.makeAssignment("a", value);
        ConstantReference reference = factory.makeReference("a");

        assertEquals(expectedType, reference.getType());
    }

    @Test
    @UseDataProvider("provideValueTypes")
    public void testReferenceGetTypeViaVia(Value value, Value.Type expectedType) throws Exception {
        ConstantFactory factory = new ConstantFactory();

        factory.makeAssignment("a", value);
        factory.makeAssignment("b", factory.makeReference("a"));

        ConstantReference referenceB = factory.makeReference("b");

        assertEquals(expectedType, referenceB.getType());
    }
}
