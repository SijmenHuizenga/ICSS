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
