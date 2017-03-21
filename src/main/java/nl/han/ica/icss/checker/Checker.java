package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Assignment;
import nl.han.ica.icss.ast.Value;
import nl.han.ica.icss.checker.errors.DuplicateAssignmentError;

import java.util.ArrayList;
import java.util.HashMap;

public class Checker {

    public void check(AST ast) {
        DuplicateAssignmentChecker duplicationChecker = new DuplicateAssignmentChecker();
        duplicationChecker.checkDuplicateAssignments(ast.root);
        ast.symboltable = duplicationChecker.getSymboltable();

        ast.root.check();

        if (ast.getErrors().isEmpty())
            ast.checked = true;
    }

}
