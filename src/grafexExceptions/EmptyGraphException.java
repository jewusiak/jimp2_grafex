package grafexExceptions;

import grafex.Graph;

public class EmptyGraphException extends GraphException {
    public EmptyGraphException() {
        super("Graf jest pusty!");
    }
}
