package grafex;

import java.util.List;

public class Graph {


    private List<Point> points;
    //private String name;

    private Boolean viewable = false;

    private int columns;
    private int rows;

    private int size;

    public int getSize() {
        return viewable ? columns * rows : size;
    }

    public int getRows() {
        return viewable ? rows : -1;
    }

    public int getColumns() {
        return viewable ? columns : -1;
    }

    public Boolean isViewable() {
        return viewable;
    }

    public boolean readFromFile(String filename) {
        return true;
    }

    public boolean writeToFile(String filename) {
        return true;
    }

    public boolean runBFS() {
        return false;
    }


    public GraphPath findPath(int first, int last) {
        return new GraphPath();
    }

}
