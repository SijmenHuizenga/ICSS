package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Assignment;
import nl.han.ica.icss.ast.Value;
import nl.han.ica.icss.checker.errors.DuplicateAssignmentError;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sijmen on 21-3-2017.
 */
public class DuplicateAssignmentChecker {

    private HashMap<String, Value> symboltable;
    private ArrayList<ASTNode> vistedNodes;

    public void checkDuplicateAssignments(ASTNode ast){
        this.symboltable = new HashMap<>();
        this.vistedNodes = new ArrayList<>();
        checkDuplicateAssignmentsRecursive(ast);
    }

    private void checkDuplicateAssignmentsRecursive(ASTNode ast){
        if(vistedNodes.contains(ast))
            return;
        vistedNodes.add(ast);

        ast.getChildren().forEach(this::checkDuplicateAssignmentsRecursive);

        if (!(ast instanceof Assignment))
            return;


        Assignment as = (Assignment) ast;
        String key = as.key.name;

        if (symboltable.containsKey(key)) {
            as.addError(makeError(key));
            symboltable.get(key).addError(makeError(key));
        }else
            symboltable.put(key, as.value);
    }

    private DuplicateAssignmentError makeError(String key){
        return new DuplicateAssignmentError("Constants "+key+" is assigned multiple times.");
    }

    public HashMap<String, Value> getSymboltable() {
        return symboltable;
    }

}
