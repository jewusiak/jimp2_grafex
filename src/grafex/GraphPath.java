package grafex;

import java.util.List;

public class GraphPath {
    private List<Integer> points;
    private double totalLength;

    public void addAtFront(int point){
        points.add(0, point);
    }

    public void addAtEnd(int point){
        points.add(point);
    }

    @Override
    public String toString() {
        if (points.size()==0) return "";
        String rs="";
        for(int i=0;i<points.size()-1;i++) rs += points.get(i) + " -> ";

        return rs+points.get(points.size()-1);
    }
}
