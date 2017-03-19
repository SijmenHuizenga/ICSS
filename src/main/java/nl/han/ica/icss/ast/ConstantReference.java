package nl.han.ica.icss.ast;

import java.util.List;

public class ConstantReference extends Value {

    public String name;
    public Assignment assignment; // the assignment that makes this constantReference

    public ConstantReference(String name, Assignment assignment) {
        super();
        this.name = name;
        this.assignment = assignment;
    }

    @Override
    public String getNodeLabel() {
        return "ConstantReference(" + name + ")";
    }

    @Override
    public void check() {
        if(assignment == null)
            setError("Reference is referencing an unassigned constant.");
    }

    @Override
    public Type getType(List<ASTNode> vistitedNodes) {
        if(vistitedNodes.contains(this))
            return Type.UNKNOWN;
        vistitedNodes.add(this);
        return assignment == null ? Type.UNKNOWN : assignment.value.getType(vistitedNodes);
    }

    @Override
    public boolean containsReferenceTo(ConstantReference ref, List<ASTNode> vistitedNodes) {
        if(name.equals(ref.name))
            return true;
        if(vistitedNodes.contains(this))
            return true;
        vistitedNodes.add(this);
        if(assignment != null)
            return assignment.value.containsReferenceTo(ref ,vistitedNodes);
        return false;
    }
}
