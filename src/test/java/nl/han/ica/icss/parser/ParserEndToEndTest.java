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

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.astfactory.ASTBuilder;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sijmen on 22-3-2017.
 */
@RunWith(DataProviderRunner.class)
public class ParserEndToEndTest {

    @DataProvider
    public static Object[][] getInput(){
        return new Object[][] {
                {"level0.icss", getLevel0Tree()}
        };
    }

    @UseDataProvider("getInput")
    @Test
    public void testApplyDeclaration(String resourceName, AST expectedOutput) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(
                this.getClass().getClassLoader().getResourceAsStream(resourceName)
        );

        ICSSLexer lexer = new ICSSLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        ICSSParser parser = new ICSSParser(tokens);
        ParseTree parseTree = parser.stylesheet();

        ASTListener listener = new ASTListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, parseTree);

        assertEquals(expectedOutput, listener.getAST());
    }

    public static AST getLevel0Tree(){
        return ASTBuilder.build(b -> {
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
        });
    }

}
