package grafex;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca ścieżkę w grafie.
 */
public class GraphPath {
    private final List<Integer> points;
    private double totalLength;

    public GraphPath(){
        points=new ArrayList<>();
        totalLength=-1;
    }

    /**
     * Dodaje id punktu na początek ścieżki.
     * @param point id punktu
     */
    public void addAtFront(int point) {
        points.add(0, point);
    }

    /**
     * Dodaje id punktu na koniec ścieżki.
     * @param point id punktu
     */
    public void addAtEnd(int point) {
        points.add(point);
    }

    public List<Integer> getPoints() {
        return points;
    }

    public double getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(double totalLength) {
        this.totalLength = totalLength;
    }

    @Override
    public String toString() {
        if (points.size() == 0) return "";
        String rs = "";
        for (int i = 0; i < points.size() - 1; i++) rs += points.get(i) + " -> ";

        return rs + points.get(points.size() - 1) + " (" + getTotalLength() + ")";
    }
}
