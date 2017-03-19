package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.Assignment;

import java.util.HashMap;

public class Checker {

    public void check(AST ast) {
        ast.symboltable = new HashMap<>();

        ast.root.body.stream()
            .filter(astNode -> astNode instanceof Assignment)
            .map(c -> (Assignment) c)
            .forEach(a -> {
                if (ast.symboltable.containsKey(a.key.name))
                    a.setError("This is a duplicate assignment and thus ignored.");
                else
                    ast.symboltable.put(a.key.name, a.value);
            });

        ast.root.check();

        if (ast.getErrors().isEmpty())
            ast.checked = true;
    }

}
