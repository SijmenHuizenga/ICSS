package nl.han.ica.icss.ast;

import java.util.List;

public abstract class Literal extends Value {

    @Override
    public boolean containsReferenceTo(ConstantReference ref, List<ASTNode> visitedNodes) {
        return false;
    }

}
