package grafex;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Point{
    private final int id;
    private List<Integer> adjacent;

    Point(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addAdjacent(int id){
        adjacent.add(id);
    }

    public List<Integer> getAdjacent() {
        return adjacent;
    }

    public Integer getUpperAdjacent(Graph graph){
        return adjacent.stream().filter((p)->p==id-graph.getColumns()).findFirst().orElse(null);


    }
}
