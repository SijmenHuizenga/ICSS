package nl.han.ica.icss.astfactory;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Assignment;
import nl.han.ica.icss.ast.ConstantReference;
import nl.han.ica.icss.ast.Value;
import nl.han.ica.icss.parser.ICSSParser;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sijmen on 17-3-2017.
 */
public class ConstantFactory {

    private List<ConstantReference> references = new LinkedList<>();

    public String getConstantName(ICSSParser.ConstantreferenceContext constant) {
        return constant.getText().substring(1);
    }

    public ConstantReference makeReference(ICSSParser.ConstantreferenceContext constantreference) {
        String constantName = getConstantName(constantreference);

        ConstantReference sameNameRef = findFirstRef(constantName);

        ConstantReference ref = new ConstantReference(constantName,
                sameNameRef != null ? sameNameRef.assignment : null);
        references.add(ref);

        return ref;
    }

    protected ConstantReference findFirstRef(String name){
        for(ConstantReference reference : references)
            if(reference.name.equals(name))
                return reference;
        return null;
    }


    public ASTNode makeAssignment(ICSSParser.ConstantreferenceContext ctx, Value value) {
        String constantName = getConstantName(ctx);

        ConstantReference ref = new ConstantReference(constantName, null);

        Assignment ass = new Assignment(ref, value);
        ref.assignment = ass;

        references.stream()
                .filter(reference -> reference.name.equals(ref.name))
                .filter(reference -> reference.assignment == null)
                .forEach(reference -> reference.assignment = ass);

        references.add(ref);

        return ass;
    }


}
