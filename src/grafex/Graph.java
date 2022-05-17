package grafex;


import java.util.ArrayList;
import java.util.List;

public class Graph {


    private List<Point> points;

    private int rows;
    private int columns;


    public int getSize() {
        return rows * columns;
    }

    //TODO: konstruktor jako generator z danych z gui

    public Graph(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        points=new ArrayList<>();
        for(int i=0;i<rows*columns;i++)
            points.add(new Point(i));
    }



    public static Graph readFromFile() {
        //czytamy graf z pliku i zwracamy viewable/nonviewable graph w zależności od typu grafu.
        return new Graph();
        //return new NonViewableGraph();
    }

    public List<Point> getPoints() {
        return points;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }


}
