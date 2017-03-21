package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.errors.InvalidDataStateError;

import java.util.List;

public class PixelLiteral extends Literal {

    public int value;

    public PixelLiteral(int value) {
        this.value = value;
    }

    public PixelLiteral(String text) {
        this.value = Integer.parseInt(text.substring(0, text.length() - 2));
    }

    @Override
    public String getNodeLabel() {
        return "PixelLiteral (" + value + ")";
    }

    @Override
    public String toString() {
        return getNodeLabel();
    }

    @Override
    public void check() {
        if(value < 0)
            addError(new InvalidDataStateError("Pixel value " + value + " is smaller than 0"));
    }

    @Override
    public Type getType(List<ASTNode> visited) {
        return Type.PIXEL;
    }
}
