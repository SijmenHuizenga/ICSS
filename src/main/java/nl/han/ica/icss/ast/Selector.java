package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.errors.InvalidDataStateError;

public class Selector extends ASTNode {

    public String tag;
    public String cls;
    public String id;

    public Selector(String input){
        if(input.startsWith("."))
            setClass(input.substring(1));
        else if(input.startsWith("#"))
            setId(input.substring(1));
        else
            setTag(input);
    }

    @Override
    public String getNodeLabel() {
        return "Selector(" + getSelector() + ")";
    }

    @Override
    public void check() {
        if(tag == null && cls == null && id == null)
            addError(new InvalidDataStateError("TAG, CLASS and ID are all null. This should be impossible!"));
    }

    public String getSelector() {
        if (tag != null)
            return tag;
        else if (cls != null)
            return "."+cls;
        else
            return "#"+id;
    }

    public Selector setClass(String cls) {
        this.cls = cls;
        return this;
    }

    public Selector setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Selector setId(String id) {
        this.id = id;
        return this;
    }
}
