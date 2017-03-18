package nl.han.ica.icss.ast;

public class ConstantReference extends Value {

    public String name;

    public ConstantReference(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getNodeLabel() {
        return "ConstantReference(" + name + ")";
    }
}
