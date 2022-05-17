package grafex;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {


    private final List<Relation> relations;

    private final int rows;
    private final int columns;


    /*
     * Prywatny konstruktor do użycia przy generowniu i wczytywaniu grafu z pliku.
     */
    private Graph(int rows, int columns) {
        if (rows < 1 || columns < 1)
            throw new IllegalArgumentException("Liczba rzędów i liczba kolumn muszą być >=1!");
        this.rows = rows;
        this.columns = columns;
        relations = new ArrayList<>();
    }

    /*
     * Konstruktor który czyta z pliku.
     */
    public Graph(String filename) {
        //TODO: napisanie konstruktora czytającego z pliku
        this(0, 1);
    }

    /*
     * Konstruktor generujący graf z informacji pobranych z GUI.
     */
    public Graph(GraphGenInfo graphGenInfo) {
        //TODO: napisanie konstruktora grafu z GUI
        this(0, 1);
    }

    /*
     * Metoda zapisuje otwarty graf do pliku.
     */
    public void saveToFile(String filename) {

    }

    /*
     * Zwraca wszystkie relacje. Metoda do użycia przy generowaniu widoku.
     */
    public List<Relation> getRelations() {
        return relations;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getSize() {
        return rows * columns;
    }

    /*
     * Tworzy relacje: first -> last o wadze weight.
     */
    public void createRelation(int first, int last, double weight) {
        relations.add(new Relation(first, last, weight));
    }

    /*
     * Zwraca listę relacji dla danego punktu from.
     */
    public List<Relation> getPointsRelations(int from) {
        return relations.stream().filter((p) -> p.getFirst() == from).collect(Collectors.toList());
    }

    /*
     * Zwraca listę id (int) sąsiadów.
     */
    public List<Integer> getAdjacent(int from) {
        return relations.stream().filter((p) -> p.getFirst() == from).map(Relation::getLast).collect(Collectors.toList());
    }
}
