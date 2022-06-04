package grafexExceptions;

public class GraphIndexOutOfBoundsException extends GraphException {

    private final int max;

    public GraphIndexOutOfBoundsException(int max) {
        super("Indeks pobieranego punktu grafu powinien: 0 <= id <= " + max);
        this.max = max;
    }

    public int getMax() {
        return max;
    }
}
