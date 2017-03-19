package nl.han.ica.icss.checker;

public class SemanticError {
    public String description;

    public SemanticError(String description) {
        this.description = description;
    }

    public String toString() {
        return "ERROR: " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SemanticError)) return false;

        SemanticError that = (SemanticError) o;

        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }
}
