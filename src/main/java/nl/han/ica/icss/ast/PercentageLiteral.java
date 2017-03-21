package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.errors.InvalidDataStateError;

import java.util.List;

public class PercentageLiteral extends Literal {
    public int value;

    public PercentageLiteral(int value) {
        this.value = value;
    }

    public PercentageLiteral(String text) {
        this.value = Integer.parseInt(text.substring(0, text.length() - 1));
    }

    @Override
    public String getNodeLabel() {
        return "PercentageLiteral (" + value + ")";
    }

    @Override
    public void check() {
        if(value < 0)
            addError(new InvalidDataStateError("Percentage "+value+" is smaller than 0"));
    }

    @Override
    public Type getType(List<ASTNode> visited) {
        return Type.PERCENTAGE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PercentageLiteral)) return false;

        PercentageLiteral that = (PercentageLiteral) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
