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

package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.Declaration;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.astfactory.ASTBuilder;
import org.junit.Test;

import java.util.Scanner;

import static nl.han.ica.icss.ast.Asserts.assertASTFromFile;
import static nl.han.ica.icss.ast.Asserts.assertStringToFile;
import static org.junit.Assert.*;

/**
 * Created by Sijmen on 22-3-2017.
 */
public class GeneratorTest {

    Generator generator = new Generator();

    @Test
    public void testGenerateLevel0() throws Exception {
        AST level1 = ASTBuilder.build(b -> {
            b.style("p", p -> {
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
        });
        assertStringToFile("level0.css", generator.generate(level1));
    }

    @Test
    public void testLevel1() throws Exception {
        AST level1 = ASTBuilder.build(b -> {
            b.assignment("linkcolor", b.color(255, 0, 0));
            b.assignment("parwidth", b.px(500));
            b.style("p", p -> {
                p.decleration(Declaration.Type.BACKGROUND_COLOR, p.color(255, 255, 255));
                p.decleration(Declaration.Type.WIDTH, b.px(500));
            });
            b.style("a", a -> {
                a.decleration(Declaration.Type.COLOR, b.color(255, 0, 0));
            });
            b.style("#menu", menu -> {
                menu.decleration(Declaration.Type.WIDTH, menu.px(520));
            });
            b.style(".menu", menu -> {
                menu.decleration(Declaration.Type.COLOR, menu.color(0, 0, 0));
            });
        });
        assertStringToFile("level1.css", generator.generate(level1));
    }

    @Test
    public void testLevel2() throws Exception {
        AST level2 = ASTBuilder.build(b -> {
            b.assignment("linkcolor", b.color(255, 0, 0));
            b.assignment("parwidth", b.px(500));
            b.style("p", p ->{
                p.decleration(Declaration.Type.BACKGROUND_COLOR, p.color(255, 255, 255));
                p.decleration(Declaration.Type.WIDTH, b.px(500));
            });
            b.style("a", a -> {
                a.decleration(Declaration.Type.COLOR, b.color(255, 0, 0));
            });
            b.style("#menu", menu -> {
                menu.decleration(Declaration.Type.WIDTH, b.px(520));
            });
            b.style(".menu", menu -> {
                menu.decleration(Declaration.Type.COLOR, menu.color(0, 0, 0));
            });
        });
        assertStringToFile("level2.css", generator.generate(level2));
    }

    @Test
    public void testLevel3() throws Exception {
        AST level3 = ASTBuilder.build(b -> {
            b.assignment("linkcolor", b.color(255, 0, 0));
            b.assignment("parwidth", b.px(500));
            b.style("p", p -> {
                p.decleration(Declaration.Type.BACKGROUND_COLOR, p.color(255, 255, 255));
                p.decleration(Declaration.Type.WIDTH, b.px(500));

                p.style("h1", h1 -> {
                    h1.decleration(Declaration.Type.WIDTH, b.px(450));
                    h1.decleration(Declaration.Type.BACKGROUND_COLOR, h1.color(238, 238, 238));
                });
            });
            b.style("a", a -> {
                a.decleration(Declaration.Type.COLOR, b.color(255, 0, 0));
            });
            b.style("#menu", menu -> {
                menu.decleration(Declaration.Type.WIDTH, b.px(520));
            });
            b.style(".menu", menu -> {
                menu.decleration(Declaration.Type.COLOR, menu.color(0, 0, 0));
            });
        });
        assertStringToFile("level3.css", generator.generate(level3));
    }

}