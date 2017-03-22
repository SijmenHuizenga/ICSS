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
import nl.han.ica.icss.astfactory.LiteralFactory;
import nl.han.ica.icss.astfactory.StyleRuleFactory;
import nl.han.ica.icss.astfactory.ValueFactory;
import nl.han.ica.icss.checker.errors.PropertyTypeError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sijmen on 21-3-2017.
 *
 * Test if the type is accepted by the decleration.
 * For example:
 * color: 50px    //error
 * width: #FFFFFF //error
 * height: 75%    //ok
 *
 * WE DO NOT NEED TO TEST VARIABLES OR OPERATIONS
 * because 'getType' is uesd, we can trust that if we test matching of
 * decleration-types and valuetypes, also the types of operations and variables will be ok
 */
@RunWith(DataProviderRunner.class)
public class DeclerationTypeCheckTest {

    private AST ast;

    @Before
    public void setUp() throws Exception {
        ast = new AST();
    }

    @DataProvider
    public static Object[][] getDeclerationTypeData(){
        return new Object[][]{
                {new Declaration(Declaration.Type.BACKGROUND_COLOR, new ColorLiteral("#123456")), true},
                {new Declaration(Declaration.Type.COLOR, new ColorLiteral("#123456")), true},
                {new Declaration(Declaration.Type.HEIGHT, new ColorLiteral("#123456")), false},
                {new Declaration(Declaration.Type.WIDTH, new ColorLiteral("#123456")), false},

                {new Declaration(Declaration.Type.BACKGROUND_COLOR, new PixelLiteral("123456px")), false},
                {new Declaration(Declaration.Type.COLOR, new PixelLiteral("123456px")), false},
                {new Declaration(Declaration.Type.HEIGHT, new PixelLiteral("123456px")), true},
                {new Declaration(Declaration.Type.WIDTH, new PixelLiteral("123456px")), true},

                {new Declaration(Declaration.Type.BACKGROUND_COLOR, new PercentageLiteral("50%")), false},
                {new Declaration(Declaration.Type.COLOR, new PercentageLiteral("50%")), false},
                {new Declaration(Declaration.Type.HEIGHT, new PercentageLiteral("50%")), true},
                {new Declaration(Declaration.Type.WIDTH, new PercentageLiteral("50%")), true},
        };
    }


    @Test
    @UseDataProvider("getDeclerationTypeData")
    public void testDeclerationTypeMatch(Declaration declaration, boolean isOkay)
            throws Exception {
        ast.root.addChild(declaration);
        ast.check();

        if(isOkay){
            assertEquals(0, ast.getErrors().size());
        } else {
            assertEquals(1, ast.getErrors().size());
            Asserts.assertContainsAllType(PropertyTypeError.class, ast.getErrors());
        }
    }
}
