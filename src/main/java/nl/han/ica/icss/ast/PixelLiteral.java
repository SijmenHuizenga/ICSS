package nl.han.ica.icss.ast;

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
            setError("Pixel value cannot be smaller than 0");
    }

    @Override
    public Type getType() {
        return Type.PIXEL;
    }
}
