package grafexExceptions;

public abstract class GraphException extends Exception {
    public GraphException(String message) {
        super(message);
    }

    public GraphException() {
    }
}
