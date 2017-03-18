package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.parser.ICSSParser.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sijmen on 18-3-2017.
 */
public class StyleRuleFactory {

    private ValueFactory valueFactory;

    public StyleRuleFactory(ValueFactory valueFactory) {
        this.valueFactory = valueFactory;
    }

    public Stylerule make(StyleruleContext ctx) {
        return new Stylerule(
                make(ctx.selector()),
                make(ctx.declerationpart())
        );
    }

    private ArrayList<ASTNode> make(List<DeclerationpartContext> declerationparts) {
        ArrayList<ASTNode> nodes = new ArrayList<>();
        if(declerationparts == null)
            return nodes;
        for(DeclerationpartContext dpc : declerationparts)
            nodes.add(make(dpc));
        return nodes;
    }

    private ASTNode make(DeclerationpartContext dpc) {
        if(dpc.decleration() != null)
            return make(dpc.decleration());
        else if(dpc.stylerule() != null)
            return make(dpc.stylerule());
        throw new IllegalArgumentException("DECLERATION and STYLERULE are both null. This is not possible.");
    }

    private ASTNode make(DeclerationContext decleration) {
        return new Declaration(make(decleration.attribute()),
                valueFactory.make(decleration.calculatedvalue()));
    }

    private Declaration.Type make(AttributeContext attribute) {
        if(attribute.ATTRIBUTE_BACKGROUND_COLOR() != null)
            return Declaration.Type.BACKGROUND_COLOR;
        if(attribute.ATTRIBUTE_COLOR() != null)
            return Declaration.Type.COLOR;
        if(attribute.ATTRIBUTE_HEIGHT() != null)
            return Declaration.Type.HEIGHT;
        if(attribute.ATTRIBUTE_WIDTH() != null)
            return Declaration.Type.WIDTH;
        throw new IllegalArgumentException("unknown attribte type");
    }

    private Selector make(SelectorContext selector) {
        if(selector.SELECTOR_CLASS() != null)
            return new Selector().setClass(selector.SELECTOR_CLASS().toString().substring(1));
        if(selector.SELECTOR_ELEEMNT() != null)
            return new Selector().setTag(selector.SELECTOR_ELEEMNT().toString());
        if(selector.SELECTOR_ID() != null)
            return new Selector().setId(selector.SELECTOR_ID().toString().substring(1));
        throw new IllegalArgumentException("CLASS, ELEMENT and ID are all null. This is not possible.");
    }


}
