package grafex;

import java.util.List;

public class Point {
    private final int id;
    private List<Point> adjacent;

    Point(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addAdjacent(Point point){
        adjacent.add(point);
    }
}
