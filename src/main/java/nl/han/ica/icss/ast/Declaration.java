package nl.han.ica.icss.ast;

import java.util.ArrayList;

/*
 * A Declaration defines a style property. Declarations are things like "width: 100px"
 */
public class Declaration extends ASTNode {

    public Type property;
    public Value value;

    public Declaration(Type property, Value value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public String getNodeLabel() {
        return "Declaration(" + property + ")";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(value);
        return children;
    }

    @Override
    public String toString() {
        return "Declaration(" + property + ":" + value + ")";
    }

    @Override
    public void check() {
        //CH04
        if(!property.accepts(value.getType()))
            setError("Property " + property + " does not accept valuetype " + value.getType());
    }

    @Override
    public void addChild(ASTNode child) {
        value = (Value) child;
    }

    public static enum Type {

        COLOR(Value.Type.COLOR),
        BACKGROUND_COLOR(Value.Type.COLOR),
        WIDTH(Value.Type.PERCENTAGE, Value.Type.PIXEL),
        HEIGHT(Value.Type.PERCENTAGE, Value.Type.PIXEL);

        private Value.Type[] acceptedtypes;

        Type(Value.Type... acceptedtypes) {
            this.acceptedtypes = acceptedtypes;
        }

        public boolean accepts(Value.Type type) {
            for(Value.Type accepted : acceptedtypes)
                if(type == accepted)
                    return true;
            return false;
        }
    }
}
