package grafex;

import java.util.List;

public class GraphPath {
    private List<Integer> points;
    private double totalLength;

    /*
     * Dodaje id punktu na początek ścieżki.
     */
    public void addAtFront(int point) {
        points.add(0, point);
    }

    /*
     * Dodaje id punktu na koniec ścieżki.
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

    @Override
    public String toString() {
        if (points.size() == 0) return "";
        String rs = "";
        for (int i = 0; i < points.size() - 1; i++) rs += points.get(i) + " -> ";

        return rs + points.get(points.size() - 1);
    }
}
