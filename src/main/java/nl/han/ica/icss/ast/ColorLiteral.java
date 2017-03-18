package nl.han.ica.icss.ast;

import java.awt.*;

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
            setError("Color " + value + " is not a valid color");
        }
    }

    @Override
    public Type getType() {
        return Type.COLOR;
    }
}
