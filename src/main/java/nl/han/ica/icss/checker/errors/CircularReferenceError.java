package nl.han.ica.icss.checker.errors;

/**
 * Created by Sijmen on 21-3-2017.
 */
public class CircularReferenceError extends SemanticError {
    public CircularReferenceError(String description) {
        super(description);
    }
}
