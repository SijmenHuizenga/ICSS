package nl.han.ica.icss.ast;

import nl.han.ica.icss.astfactory.ConstantFactory;
import nl.han.ica.icss.astfactory.LiteralFactory;
import nl.han.ica.icss.astfactory.StyleRuleFactory;
import nl.han.ica.icss.astfactory.ValueFactory;
import nl.han.ica.icss.checker.errors.DuplicateAssignmentError;
import org.junit.Before;
import org.junit.Test;

import static nl.han.ica.icss.ast.Asserts.assertContainsAllType;
import static org.junit.Assert.assertEquals;


/**
 * Created by Sijmen on 21-3-2017.
 */
public class DuplicateAssignmentCheckerTest {

    ConstantFactory constantFactory;
    LiteralFactory literalFactory;
    ValueFactory valueFactory;
    StyleRuleFactory styleRuleFactory;

    ConstantFactory factory;
    AST ast;

    @Before
    public void setUp() throws Exception {
        factory = new ConstantFactory();
        ast = new AST();

        constantFactory = new ConstantFactory();
        literalFactory = new LiteralFactory();
        valueFactory = new ValueFactory(literalFactory, constantFactory);
        styleRuleFactory = new StyleRuleFactory(valueFactory);
    }

    /**
     * $a = 50
     * $a = 60
     *
     * both give an error
     */
    @Test
    public void testDuplicateAssignments() throws Exception {
        ast.root.addChild(factory.makeAssignment("A", new PixelLiteral(50)));
        ast.root.addChild(factory.makeAssignment("A", new PixelLiteral(60)));

        ast.check();

        assertContainsAllType(DuplicateAssignmentError.class, ast.getErrors());
        assertEquals(2, ast.getErrors().size());
    }

    /**
     * #id {
     *     $a = 50px;
     *     .id2 {
     *         $a = 100px;
     *     }
     * }
     *
     * both $a assignments give an error
     */
    @Test
    public void testDuplicateAssignmentsInsideBlocks() throws Exception {
        Stylerule idBlock2 = styleRuleFactory.make(".id2");
        idBlock2.addChild(factory.makeAssignment("a", new PixelLiteral(100)));

        Stylerule idBlock = styleRuleFactory.make("#id");
        idBlock.addChild(factory.makeAssignment("a", new PixelLiteral(50)));
        idBlock.addChild(idBlock2);

        ast.root.addChild(idBlock);

        ast.check();

        assertContainsAllType(DuplicateAssignmentError.class, ast.getErrors());
        assertEquals(2, ast.getErrors().size());
    }
}
