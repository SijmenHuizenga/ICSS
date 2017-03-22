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

package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.*;

import java.util.ArrayList;

/**
 * Created by Sijmen on 22-3-2017.
 */
public class ASTBuilder {

    private AST ast;
    private ASTNode root;
    private ConstantFactory constantFactory;

    public ASTBuilder(ASTNode root, ConstantFactory constantFactory) {
        this.root = root;
        this.constantFactory = constantFactory;
    }

    public ASTBuilder() {
        this.constantFactory = new ConstantFactory();
        this.ast = new AST();
        this.root = this.ast.root;
    }

    public static AST build(ASTBuilderLamba runner){
        ASTBuilder astBuilder = new ASTBuilder();
        runner.run(astBuilder);
        return astBuilder.ast;
    }

    public PixelLiteral px(int i) {
        return new PixelLiteral(i);
    }

    public PercentageLiteral perc(int i) {
        return new PercentageLiteral(i);
    }

    public ColorLiteral color(int r, int g, int b) {
        return new ColorLiteral(r, g, b);
    }

    public void assignment(String s, Value value) {
        root.addChild(constantFactory.makeAssignment(s, value));
    }

    public void style(String selector, ASTBuilderLamba lamba){
        Stylerule stylerule = new Stylerule(new Selector(selector), new ArrayList<>());
        lamba.run(new ASTBuilder(stylerule, this.constantFactory));
        root.addChild(stylerule);
    }

    public void decleration(Declaration.Type type, Value value) {
        root.addChild(new Declaration(type, value));
    }

    public ConstantReference cons(String name) {
        return constantFactory.makeReference(name);
    }

    public Operation operation(Value left, Operation.Operator operator, Value right) {
        return new Operation(left, operator, right);
    }

    @FunctionalInterface
    public interface ASTBuilderLamba {
        void run(ASTBuilder builder);
    }

}
