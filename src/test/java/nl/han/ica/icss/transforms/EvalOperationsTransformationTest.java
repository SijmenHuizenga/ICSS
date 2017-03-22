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

package nl.han.ica.icss.transforms;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.astfactory.ConstantFactory;
import nl.han.ica.icss.astfactory.LiteralFactory;
import nl.han.ica.icss.astfactory.StyleRuleFactory;
import nl.han.ica.icss.astfactory.ValueFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Sijmen on 22-3-2017.
 */
@RunWith(DataProviderRunner.class)
public class EvalOperationsTransformationTest {

    private EvalOperationsTransformation transformer;

    private ConstantFactory constantFactory;
    private LiteralFactory literalFactory;
    private ValueFactory valueFactory;
    private StyleRuleFactory styleRuleFactory;

    private AST ast;

    @Before
    public void setUp() throws Exception {
        ast = new AST();

        constantFactory = new ConstantFactory();
        literalFactory = new LiteralFactory();
        valueFactory = new ValueFactory(literalFactory, constantFactory);
        styleRuleFactory = new StyleRuleFactory(valueFactory);

        transformer = new EvalOperationsTransformation();
    }

    @DataProvider
    public static Object[][] getCalculations(){
        return new Object[][]{
                //PIXELS
                {Declaration.Type.HEIGHT, new Operation(new PixelLiteral(50), Operation.Operator.PLUS, new PixelLiteral(40)), new PixelLiteral(90)},
                {Declaration.Type.HEIGHT, new Operation(new PixelLiteral(50), Operation.Operator.MIN, new PixelLiteral(20)), new PixelLiteral(30)},
                {Declaration.Type.HEIGHT, new Operation(new PixelLiteral(60), Operation.Operator.DEV, new PixelLiteral(3)), new PixelLiteral(20)},
                {Declaration.Type.HEIGHT, new Operation(new PixelLiteral(3), Operation.Operator.MUL, new PixelLiteral(3)), new PixelLiteral(9)},

                //PERCENTAGES
                {Declaration.Type.WIDTH, new Operation(new PercentageLiteral(50), Operation.Operator.PLUS, new PercentageLiteral(40)), new PercentageLiteral(90)},
                {Declaration.Type.WIDTH, new Operation(new PercentageLiteral(50), Operation.Operator.MIN, new PercentageLiteral(20)), new PercentageLiteral(30)},
                {Declaration.Type.WIDTH, new Operation(new PercentageLiteral(60), Operation.Operator.DEV, new PercentageLiteral(3)), new PercentageLiteral(20)},
                {Declaration.Type.WIDTH, new Operation(new PercentageLiteral(3), Operation.Operator.MUL, new PercentageLiteral(3)), new PercentageLiteral(9)},

                //COLORS
                {Declaration.Type.BACKGROUND_COLOR, new Operation(new ColorLiteral(10, 20, 30), Operation.Operator.PLUS, new ColorLiteral(1, 2, 3)), new ColorLiteral(11, 22, 33)},
                {Declaration.Type.BACKGROUND_COLOR, new Operation(new ColorLiteral(254, 254, 200), Operation.Operator.PLUS, new ColorLiteral(20, 30, 55)), new ColorLiteral(255, 255, 255)},

                {Declaration.Type.COLOR, new Operation(new ColorLiteral(10, 20, 30), Operation.Operator.MIN, new ColorLiteral(1, 2, 3)), new ColorLiteral(9, 18, 27)},
                {Declaration.Type.COLOR, new Operation(new ColorLiteral(10, 10, 10), Operation.Operator.MIN, new ColorLiteral(50, 60, 70)), new ColorLiteral(0, 0, 0)},

                {Declaration.Type.COLOR, new Operation(new ColorLiteral(10, 20, 30), Operation.Operator.DEV, new ColorLiteral(10, 2, 4)), new ColorLiteral(1, 10, 7)},
                {Declaration.Type.COLOR, new Operation(new ColorLiteral(10, 20, 30), Operation.Operator.MUL, new ColorLiteral(2, 3, 4)), new ColorLiteral(20, 60, 120)},
                {Declaration.Type.COLOR, new Operation(new ColorLiteral(200, 1, 30), Operation.Operator.MUL, new ColorLiteral(300, 255, 4)), new ColorLiteral(255, 255, 120)},

                //NESTED
                {Declaration.Type.HEIGHT, // 50px / (2px + 3px)
                        new Operation(new PixelLiteral(50), Operation.Operator.DEV,
                                new Operation(new PixelLiteral(2), Operation.Operator.PLUS, new PixelLiteral(3))
                        ),
                        new PixelLiteral(10)
                },
                {Declaration.Type.WIDTH,  // 50px + 10px / 2px     = 50+(10/2)
                        new Operation(new PixelLiteral(50), Operation.Operator.PLUS,
                                new Operation(new PixelLiteral(10), Operation.Operator.DEV, new PixelLiteral(2))
                        ),
                        new PixelLiteral(55)
                },
                {Declaration.Type.WIDTH,  // 50px + 10px / 2px * 25px   =  50px + ((10px / 2px) * 25px)
                        new Operation(new PixelLiteral(50), Operation.Operator.PLUS,
                                new Operation(
                                        new Operation(new PixelLiteral(10), Operation.Operator.DEV, new PixelLiteral(2))
                                        , Operation.Operator.MUL, new PixelLiteral(25))
                        ),
                        new PixelLiteral(175)
                },
        };
    }


    @Test
    @UseDataProvider("getCalculations")
    public void testApplyDeclaration(Declaration.Type declerationType, Operation operation, Literal expectedResult) throws Exception {
        Stylerule stylerule = styleRuleFactory.make("#id");
        stylerule.addChild(new Declaration(declerationType, operation));
        ast.root.addChild(stylerule);

        transformer.apply(ast);

        assertArrayEquals(
                new ASTNode[]{new Declaration(declerationType, expectedResult)},
                stylerule.getChildren().toArray());
    }

    @Test
    @UseDataProvider("getCalculations")
    public void testApplyAssignment(Declaration.Type declerationType, Operation operation, Literal expectedResult) throws Exception {
        ast.root.addChild(constantFactory.makeAssignment("a", operation));

        transformer.apply(ast);

        assertArrayEquals(
                new ASTNode[]{constantFactory.makeAssignment("a", expectedResult)},
                ast.root.getChildren().toArray());
    }
}