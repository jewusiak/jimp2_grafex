package grafex;


public abstract class Graph {

    public abstract int getSize();

    public static Graph readFromFile(){
        //czytamy graf z pliku i zwracamy viewable/nonviewable graph w zależności od typu grafu.
        return new ViewableGraph(10,2);
        //return new NonViewableGraph();
    }

}
