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

import nl.han.ica.icss.ast.Declaration;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.astfactory.ASTBuilder;
import org.junit.Test;

import static nl.han.ica.icss.ast.Asserts.assertASTFromFile;

/**
 * Created by Sijmen on 22-3-2017.
 */
public class ParserEndToEndTest {

    @Test
    public void testLevel0() throws Exception {
        assertASTFromFile("level0.icss", ASTBuilder.build(b -> {
            b.style("p", p ->{
                p.decleration(Declaration.Type.BACKGROUND_COLOR, p.color(255, 255, 255));
                p.decleration(Declaration.Type.WIDTH, p.px(500));
            });
            b.style("a", a -> {
                a.decleration(Declaration.Type.COLOR, a.color(255, 0, 0));
            });
            b.style("#menu", menu -> {
                menu.decleration(Declaration.Type.WIDTH, menu.px(520));
            });
            b.style(".menu", menu -> {
                menu.decleration(Declaration.Type.COLOR, menu.color(0, 0, 0));
            });
        }));
    }

    @Test
    public void testLevel1() throws Exception {
        assertASTFromFile("level1.icss", ASTBuilder.build(b -> {
            b.assignment("linkcolor", b.color(255, 0, 0));
            b.assignment("parwidth", b.px(500));
            b.style("p", p -> {
                p.decleration(Declaration.Type.BACKGROUND_COLOR, p.color(255, 255, 255));
                p.decleration(Declaration.Type.WIDTH, p.cons("parwidth"));
            });
            b.style("a", a -> {
                a.decleration(Declaration.Type.COLOR, a.cons("linkcolor"));
            });
            b.style("#menu", menu -> {
                menu.decleration(Declaration.Type.WIDTH, menu.px(520));
            });
            b.style(".menu", menu -> {
                menu.decleration(Declaration.Type.COLOR, menu.color(0, 0, 0));
            });
        }));
    }

    @Test
    public void testLevel2() throws Exception {
        assertASTFromFile("level2.icss", ASTBuilder.build(b -> {
            b.assignment("linkcolor", b.color(255, 0, 0));
            b.assignment("parwidth", b.px(500));
            b.style("p", p ->{
                p.decleration(Declaration.Type.BACKGROUND_COLOR, p.color(255, 255, 255));
                p.decleration(Declaration.Type.WIDTH, p.cons("parwidth"));
            });
            b.style("a", a -> {
                a.decleration(Declaration.Type.COLOR, a.cons("linkcolor"));
            });
            b.style("#menu", menu -> {
                menu.decleration(Declaration.Type.WIDTH, b.operation(
                        menu.cons("parwidth"),
                        Operation.Operator.PLUS,
                        menu.px(20)
                ));
            });
            b.style(".menu", menu -> {
                menu.decleration(Declaration.Type.COLOR, menu.color(0, 0, 0));
            });
        }));
    }

    @Test
    public void testLevel3() throws Exception {
        assertASTFromFile("level3.icss", ASTBuilder.build(b -> {
            b.assignment("linkcolor", b.color(255, 0, 0));
            b.assignment("parwidth", b.px(500));
            b.style("p", p ->{
                p.decleration(Declaration.Type.BACKGROUND_COLOR, p.color(255, 255, 255));
                p.decleration(Declaration.Type.WIDTH, p.cons("parwidth"));

                p.style("h1", h1 ->{
                    h1.decleration(Declaration.Type.WIDTH, h1.operation(
                        h1.cons("parwidth"),
                        Operation.Operator.MIN,
                        h1.px(50)
                    ));
                    h1.decleration(Declaration.Type.BACKGROUND_COLOR, h1.color(238, 238, 238));
                });
            });
            b.style("a", a -> {
                a.decleration(Declaration.Type.COLOR, a.cons("linkcolor"));
            });
            b.style("#menu", menu -> {
                menu.decleration(Declaration.Type.WIDTH, b.operation(
                        menu.cons("parwidth"),
                        Operation.Operator.PLUS,
                        menu.px(20)
                ));
            });
            b.style(".menu", menu -> {
                menu.decleration(Declaration.Type.COLOR, menu.color(0, 0, 0));
            });
        }));
    }

}
