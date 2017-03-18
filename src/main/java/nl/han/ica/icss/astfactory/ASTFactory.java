package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.parser.ICSSParser;

/**
 * Created by Sijmen on 18-3-2017.
 */
public class ASTFactory {

    AST ast = new AST();

    ConstantReferenceFactory constantReferenceFactory = new ConstantReferenceFactory();
    LiteralFactory literalFactory = new LiteralFactory();

    ValueFactory valueFactory = new ValueFactory(literalFactory, constantReferenceFactory);

    StyleRuleFactory styleRuleFactory = new StyleRuleFactory(valueFactory);

    public void addConstantDecleration(ICSSParser.ConstantassignmentContext ctx){
        String constantName = constantReferenceFactory.getConstantName(ctx.constantreference());
        if(ast.symboltable.containsKey(constantName))
            throw new IllegalArgumentException("Je mag in ICSS een constante maar een keer defnieren.");

        ast.symboltable.put(
                constantName,
                valueFactory.make(ctx.calculatedvalue())
        );
    }

    public AST getAst() {
        return ast;
    }

    public void addStyleRule(ICSSParser.StyleruleContext ctx) {
        ast.root.addChild(styleRuleFactory.make(ctx));
    }
}
