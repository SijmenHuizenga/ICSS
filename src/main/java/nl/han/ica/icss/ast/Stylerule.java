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
        return "Stylerule (" + selector + ")";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        return body;
    }

    @Override
    public void addChild(ASTNode child) {
        body.add(child);
    }
}
