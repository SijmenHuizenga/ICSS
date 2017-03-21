package nl.han.ica.icss.ast;


import nl.han.ica.icss.checker.errors.InvalidDataStateError;

import java.awt.Color;
import java.util.List;

public class ColorLiteral extends Literal {

    public String value;

    public ColorLiteral(String value) {
        this.value = value.substring(1);
    }

    @Override
    public String getNodeLabel() {
        return "Colorliteral(" + value + ")";
    }

    @Override
    public void check() {
        try{
            new Color(
                    Integer.valueOf(value.substring( 0, 2 ), 16 ),
                    Integer.valueOf(value.substring( 2, 4 ), 16 ),
                    Integer.valueOf(value.substring( 4, 6 ), 16 ) );
        }catch (Exception e){
            addError(new InvalidDataStateError("Color " + value + " is not a valid color"));
        }
    }

    @Override
    public Type getType(List<ASTNode> visited) {
        return Type.COLOR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColorLiteral)) return false;

        ColorLiteral that = (ColorLiteral) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
