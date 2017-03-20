package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;

public class InlineConstants implements Transform {
    @Override
    public void apply(AST ast) {
        apply(ast.root);
    }

    public void apply(ASTNode node){
        if(node instanceof Operation){
            Operation operation = (Operation) node;
            operation.lhs = getTransformedValue(operation.lhs);
            operation.rhs = getTransformedValue(operation.rhs);
        }
        if(node instanceof Declaration){
            Declaration declaration = (Declaration) node;
            declaration.value = getTransformedValue(declaration.value);
        }

        for(ASTNode child : node.getChildren())
            apply(child);
    }

    private Value getTransformedValue(Value rhs) {
        if(rhs == null)
            return null;
        if(rhs instanceof ConstantReference)
            return ((ConstantReference) rhs).assignment.value;
        return rhs;
    }
}
