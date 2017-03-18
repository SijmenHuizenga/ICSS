package nl.han.ica.icss.ast;

import java.util.ArrayList;

public class Stylerule extends ASTNode {

    public Selector selector;
    public ArrayList<ASTNode> body;

    public Stylerule() {
        selector = new Selector();
        body = new ArrayList<>();
    }

    @Override
    public String getNodeLabel() {
        return "Stylerule(" + selector + ")";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        return body;
    }

    @Override
    public void addChild(ASTNode child) {
        body.add(child);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Stylerule(").append(selector);
        for(ASTNode node : body)
            builder.append(System.lineSeparator()).append(node);
        builder.append(")");
        return builder.toString();
    }
}
