package grafex;

public class ViewableGraph extends Graph {
    private int rows;
    private int columns;

    //TODO: delete
    public ViewableGraph(int s1, int s2){
        rows=s1;
        columns=s2;

    }

    public int getSize(){
        return rows*columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }


}
