package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.Stylesheet;
import nl.han.ica.icss.astfactory.ConstantReferenceFactory;
import nl.han.ica.icss.astfactory.ValueFactory;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

    private AST ast;

    public ASTListener() {
        ast = new AST();
    }

    public AST getAST() {
        System.out.println(ast.symboltable);
        return ast;
    }

    @Override
    public void exitAttribute(ICSSParser.AttributeContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitCalcoperator(ICSSParser.CalcoperatorContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitCalculatedvalue(ICSSParser.CalculatedvalueContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitConstantreference(ICSSParser.ConstantreferenceContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitDecleration(ICSSParser.DeclerationContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitDeclerationpart(ICSSParser.DeclerationpartContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());    
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitLiteral(ICSSParser.LiteralContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitMoreCalculatedValues(ICSSParser.MoreCalculatedValuesContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitSelector(ICSSParser.SelectorContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitValue(ICSSParser.ValueContext ctx) {
        System.out.println(ctx.getClass() + " - " + ctx.getText());
    }

    @Override
    public void exitConstantassignment(ICSSParser.ConstantassignmentContext ctx) {
        //todo: throw error if constant is already defined
        ast.symboltable.put(
                ConstantReferenceFactory.getConstantName((ICSSParser.ConstantreferenceContext) ctx.getChild(0)),
                ValueFactory.getValue((ICSSParser.CalculatedvalueContext) ctx.getChild(2)));
    }
}