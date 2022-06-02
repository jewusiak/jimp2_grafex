package grafexExceptions;

import java.util.List;

public class GraphNotCoherentException extends GraphException{

    List<Integer> notCoherent;

    public GraphNotCoherentException(String message, List<Integer> notCoherent) {
        super(message);
        this.notCoherent=notCoherent;
    }
}
