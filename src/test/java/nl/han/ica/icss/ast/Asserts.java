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

import nl.han.ica.icss.parser.ASTListener;
import nl.han.ica.icss.parser.ICSSLexer;
import nl.han.ica.icss.parser.ICSSParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.internal.ArrayComparisonFailure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

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

    public static void assertASTFromFile(String resourceName, AST expectedOutput) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(
                Asserts.class.getClassLoader().getResourceAsStream(resourceName)
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

}
