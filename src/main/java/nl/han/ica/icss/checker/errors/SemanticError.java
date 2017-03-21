package nl.han.ica.icss.checker.errors;

public abstract class SemanticError {

    public String description;

    public SemanticError(String description) {
        this.description = description;
    }

    public String toString() {
        return getClass().getSimpleName() + "@" + hashCode() + ": " + description;
    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }

}
