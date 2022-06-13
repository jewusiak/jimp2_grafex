package grafexExceptions;


public class EmptyGraphException extends GraphException {
    public EmptyGraphException() {
        super("Graf jest pusty!");
    }
}
