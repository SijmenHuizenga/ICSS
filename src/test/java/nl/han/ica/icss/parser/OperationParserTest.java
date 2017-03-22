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

package nl.han.ica.icss.parser;

import static nl.han.ica.icss.ast.Operation.Operator.*;

import static nl.han.ica.icss.ast.Declaration.Type.*;
import nl.han.ica.icss.astfactory.ASTBuilder;
import org.junit.Test;

import static nl.han.ica.icss.ast.Asserts.assertASTFromFile;

/**
 * Created by Sijmen on 22-3-2017.
 */
@SuppressWarnings("CodeBlock2Expr")
public class OperationParserTest {

    @Test
    public void testApplyOperationLevel2() throws Exception {
        assertASTFromFile("Operation2.icss", ASTBuilder.build(b ->{
            b.assignment("a", b.opr(b.px(10), PLUS, b.px(20)));
            b.style("#a", a -> {
                a.decleration(HEIGHT, b.opr(b.perc(20), PLUS, b.perc(30)));
            });
        }));
    }

    @Test
    public void testApplyOperationLevel3() throws Exception {
        assertASTFromFile("Operation3.icss", ASTBuilder.build(b ->{
            b.assignment("a", b.opr(b.opr(b.px(10), PLUS, b.px(20)), PLUS, b.px(30)));
            b.style("#a", a -> {
                a.decleration(HEIGHT, b.opr(b.opr(b.perc(20), PLUS, b.perc(30)), MIN, b.perc(40)));
            });
        }));
    }

    @Test
    public void testApplyOperationLevel4() throws Exception {
        assertASTFromFile("Operation4.icss", ASTBuilder.build(b ->{
            b.assignment("a",
                    b.opr(
                            b.opr(
                                    b.opr(b.px(10), PLUS, b.px(20))
                                    , MIN, b.px(30)
                            ), PLUS, b.px(40)
                    ));
            b.style("#a", a -> {
                a.decleration(HEIGHT,
                        b.opr(
                                b.opr(
                                        b.opr(b.perc(20), PLUS, b.perc(30))
                                        , MIN, b.perc(40)
                                ), PLUS, b.perc(50)
                        ));
            });
        }));
    }

    @Test
    public void testApplyOperationLevel5() throws Exception {
        assertASTFromFile("Operation5.icss", ASTBuilder.build(b ->{
            b.assignment("a",
                    b.opr(
                            b.opr(
                                    b.opr(
                                            b.opr(b.px(10), PLUS, b.px(20)),
                                            MIN, b.px(30)
                                    ), PLUS, b.px(40)
                            ), PLUS, b.px(50)
                    ));
            b.style("#a", a -> {
                a.decleration(HEIGHT,
                        b.opr(
                                b.opr(
                                        b.opr(
                                                b.opr(b.perc(20), PLUS, b.perc(30)),
                                                MIN, b.perc(40)
                                        ), PLUS, b.perc(50)
                                ), MIN, b.perc(60)
                        ));
            });
        }));
    }

    @Test
    public void testApplyOperationLevel6() throws Exception {
        assertASTFromFile("Operation6.icss", ASTBuilder.build(b ->{
            b.assignment("a", b.opr(b.px(10), DEV, b.px(2)));
            b.style("#a", a -> {
                a.decleration(HEIGHT, b.opr(b.perc(20), MUL, b.perc(30)));
            });
        }));
    }

    @Test
    public void testApplyOperationLevel7() throws Exception {
        assertASTFromFile("Operation7.icss", ASTBuilder.build(b ->{
            b.assignment("a", b.opr(b.px(10), PLUS, b.opr(b.px(20), DEV, b.px(4))));
            b.style("#a", a -> {
                a.decleration(HEIGHT, b.opr(b.opr(b.perc(10), DEV, b.perc(2)), PLUS, b.perc(5)));
            });
        }));
    }

    @Test
    public void testApplyOperationLevel8() throws Exception {
        assertASTFromFile("Operation8.icss", ASTBuilder.build(b ->{
            b.assignment("a", b.opr(
                    b.opr(b.px(10), DEV, b.px(20)),
                    PLUS,
                    b.opr(b.px(4), DEV, b.px(2))
            ));
            b.style("#a", a -> {
                a.decleration(HEIGHT,
                        b.opr(
                            b.opr(
                                    b.perc(10),
                                    PLUS,
                                    b.opr(b.perc(2), DEV, b.perc(1))
                            ),PLUS, b.perc(5)
                        ));
            });
        }));
    }

    @Test
    public void testApplyOperationLevel9() throws Exception {
        assertASTFromFile("Operation9.icss", ASTBuilder.build(b ->{
            b.assignment("a", b.opr(
                b.px(10), PLUS, b.opr(
                            b.opr(b.px(20), DEV, b.px(4)),
                            MUL,
                            b.px(2)
                    )
            ));
            b.style("#a", a -> {
                a.decleration(HEIGHT, b.opr(
                    b.opr(
                            b.opr(b.perc(10), DEV, b.perc(2)),
                            MUL,
                            b.perc(1)
                    ),
                    PLUS,
                    b.perc(5)

                ));
            });
        }));
    }

}
