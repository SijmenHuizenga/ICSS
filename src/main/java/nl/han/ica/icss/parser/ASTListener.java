package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.astfactory.ASTFactory;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

    private ASTFactory astFactory;

    public ASTListener() {
        astFactory = new ASTFactory();
    }

    public AST getAST() {
        System.out.println(astFactory.getAst());
        return astFactory.getAst();
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        astFactory.addStyleRule(ctx);
    }

    @Override
    public void exitConstantassignment(ICSSParser.ConstantassignmentContext ctx) {
        astFactory.addConstantDecleration(ctx);
    }
}