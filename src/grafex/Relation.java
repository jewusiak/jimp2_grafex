package grafex;

/**
 * Klasa reprezentująca relację w grafie Wierzchołek a -> Wierzchołek b.
 */
public class Relation  {
    private final int first;
    private final int last;
    private final double weight;

    public Relation(int first, int last, double weight) {
        this.first = first;
        this.last = last;
        this.weight = weight;
    }

    public int getFirst() {
        return first;
    }

    public int getLast() {
        return last;
    }

    public double getWeight() {
        return weight;
    }

    public int compareTo(Relation r){
        return (this.first-r.first);
    }


}
