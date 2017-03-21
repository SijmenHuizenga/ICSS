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
        return makeReference(getConstantName(constantreference));
    }

    public ConstantReference makeReference(String reference) {
        ConstantReference sameNameRef = findFirstRef(reference);

        ConstantReference ref = new ConstantReference(reference,
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


    public Assignment makeAssignment(ICSSParser.ConstantreferenceContext ctx, Value value) {
        return makeAssignment(getConstantName(ctx), value);
    }

    public Assignment makeAssignment(String name, Value value) {
        ConstantReference ref = new ConstantReference(name, null);

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
