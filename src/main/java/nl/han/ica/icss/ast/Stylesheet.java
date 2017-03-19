package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A stylesheet is the root node of the AST, it consists of one or more statements
 */
public class Stylesheet extends ASTNode {

    public ArrayList<ASTNode> body;

    public Stylesheet() {
        this.body = new ArrayList<>();
    }

    public Stylesheet(ArrayList<ASTNode> body) {
        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "Stylesheet";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        return this.body;
    }

    @Override
    public void addChild(ASTNode child) {
        body.add(child);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Sylesheet(");
        for(ASTNode node : body)
            builder.append(System.lineSeparator()).append(node);
        builder.append(")");
        return builder.toString();
    }

    public void check() {
        body.forEach(ASTNode::check);
    }
}
