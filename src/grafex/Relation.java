package grafex;

public class Relation {
    private final Point first;
    private final Point last;
    private final double weight;

    public Relation(Point first, Point last, double weight) {
        this.first = first;
        this.last = last;
        this.weight = weight;
    }

    public Point getFirst() {
        return first;
    }

    public Point getLast() {
        return last;
    }

    public double getWeight() {
        return weight;
    }
}
