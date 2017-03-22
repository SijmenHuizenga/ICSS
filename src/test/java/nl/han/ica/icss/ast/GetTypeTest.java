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

    /**
     * Test if a getting a type on a illegal circular reference
     * does not crash.
     */
    @Test
    public void testGetTypeCircularReference(){
        ConstantReference ref1 = new ConstantReference("a", null);
        ConstantReference ref2 = new ConstantReference("a", null);

        Assignment assignment = new Assignment(ref1, ref2);
        ref1.assignment = assignment;
        ref2.assignment = assignment;

        assertEquals(Value.Type.UNKNOWN, ref1.getType());
    }
}
