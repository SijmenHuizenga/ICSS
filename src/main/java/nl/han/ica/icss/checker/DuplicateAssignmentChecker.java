/*
 * Alle code zoals aangeleverd door ICA HAN is eigendom van ICA HAN. Deze code is te zien in het eerste commit van deze repository. Alle wijzigingen en uitbreidingen ná het eerste commit vallen onder de MIT Licentie:
 *
 * Copyright 2017 Sijmen Huizenga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
        if(Value.containsreal(vistedNodes, ast))
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
