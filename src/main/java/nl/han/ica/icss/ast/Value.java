package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class Value extends ASTNode {

    public final Type getType(){
        ArrayList<ASTNode> visitedNodes = new ArrayList<>();
        return getType(visitedNodes);
    }

    public final boolean containsReferenceTo(ConstantReference ref){
        ArrayList<ASTNode> visitedNodes = new ArrayList<>();
        return containsReferenceTo(ref, visitedNodes);
    }

    /**
     * Get the type of this value.
     * @param vistitedNodes the list of visited nodes to overcome recursion problems
     * @return the type of this value.
     */
    protected abstract Type getType(List<ASTNode> vistitedNodes);

    /**
     * Check if this value contains somewhere a reference to the given reference.
     * @param vistitedNodes the list of visited nodes to overcome recursion problems
     * @return true when the reference is found or if a recursion problem occered, else false
     */
    protected abstract boolean containsReferenceTo(ConstantReference ref, List<ASTNode> vistitedNodes);


    public enum Type {
        PIXEL,
        PERCENTAGE,
        COLOR,
        MIXED,  // the result of a calculation existing of mulitiple types
        UNKNOWN // not (yet) known because the value is not yet found.
    }

}
