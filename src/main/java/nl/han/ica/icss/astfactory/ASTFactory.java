package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.Value;
import nl.han.ica.icss.parser.ICSSParser;

/**
 * Created by Sijmen on 18-3-2017.
 */
public class ASTFactory {

    AST ast = new AST();

    ConstantFactory constantFactory = new ConstantFactory();
    LiteralFactory literalFactory = new LiteralFactory();

    ValueFactory valueFactory = new ValueFactory(literalFactory, constantFactory);

    StyleRuleFactory styleRuleFactory = new StyleRuleFactory(valueFactory);

    public void addConstantDecleration(ICSSParser.ConstantassignmentContext ctx){
        Value value = valueFactory.make(ctx.calculatedvalue());
        ast.root.addChild(constantFactory.makeAssignment(ctx.constantreference(), value));
    }

    public AST getAst() {
        return ast;
    }

    public void addStyleRule(ICSSParser.StyleruleContext ctx) {
        ast.root.addChild(styleRuleFactory.make(ctx));
    }
}
