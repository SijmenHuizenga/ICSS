package nl.han.ica.icss.ast;

public abstract class Value extends ASTNode {

    public abstract Type getType();

    public enum Type {
        PIXEL,
        PERCENTAGE,
        COLOR,
        MIXED
    }

}
