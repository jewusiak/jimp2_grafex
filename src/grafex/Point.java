package grafex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Point{
    private final int id;
    private final List<Relation> adjacent;

    Point(int id) {
        this.id = id;
        adjacent=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void createRelation(Point point, double weight){
        adjacent.add(new Relation(this, point, weight));
    }

    public void addRelation(Relation relation){
        adjacent.add(relation);
    }

    public List<Relation> getAdjacent() {
        return adjacent;
    }

    public Relation getUpperAdjacent(Graph graph){
        return adjacent.stream().filter((p)->p.getLast().id==id-graph.getColumns()).findFirst().orElse(null);


    }
}
