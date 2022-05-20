package grafex;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

public class Graph {


    private final List<Relation> relations;

    private int rows;
    private int columns;


    /*
     * Prywatny konstruktor do użycia przy generowniu i wczytywaniu grafu z pliku.
     */
    private Graph() {
        relations = new ArrayList<>();
    }

    /*
     * Konstruktor który czyta z pliku.
     */
    public Graph(String filename) {
        //TODO: napisanie konstruktora czytającego z pliku
        this();
        //tutaj czytanie wielkosci grafu z pliku
        setSize(0, 1); //ustawienie wielkości grafu
        //czytanie grafu dalej - dodawanie relacji
    }

    /*
     * Konstruktor generujący graf z informacji pobranych z GUI.
     */
    public Graph(GraphGenInfo graphGenInfo) {
        //TODO: napisanie konstruktora grafu z GUI
        this();
        //ustwaiamy wielkość grafu jak wyżej i dalej generujemmy relacje
        double w_min= graphGenInfo.getWeightBottom();
        double w_max= graphGenInfo.getWeightTop();
        int rows = graphGenInfo.getRows();
        int columns= graphGenInfo.getColumns();
        int probability =3; // procentowa szansa na wygenerowanie niespójnego wierzchołka

        setSize(rows,columns);

        if(graphGenInfo.getCoherency()==GraphGenInfo.Coherency.YES) {

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {

                    if (r != rows - 1 && c != columns - 1 ) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    }
                    else if (r == rows - 1 && c != columns - 1) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));

                    }
                    else if (r != rows - 1 && c == columns - 1) {
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    }
                }

            }
        } else if (graphGenInfo.getCoherency()==GraphGenInfo.Coherency.NO) {

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {

                    if(r==0 && c==0){
                        continue;
                    }
                    else if (r != rows - 1 && c != columns - 1 ) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    }
                    else if (r == rows - 1 && c != columns - 1) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));

                    }
                    else if (r != rows - 1 && c == columns - 1) {
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    }
                }

            }

        }
        else if(graphGenInfo.getCoherency()==GraphGenInfo.Coherency.RANDOM) {

            Random coh=new Random();

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {

                    int prob =coh.nextInt(100);

                    if (r != rows - 1 && c != columns - 1 && prob > probability) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    }
                    else if (r == rows - 1 && c != columns - 1 && prob > probability) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));

                    }
                    else if (r != rows - 1 && c == columns - 1 && prob > probability) {
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    }
                }

            }
        }

    }


    private void setSize(int rows, int columns) {

        if (rows < 1 || columns < 1)
            throw new IllegalArgumentException("Liczba rzędów i liczba kolumn muszą być >=1!");
        this.rows = rows;
        this.columns = columns;

    }

    /*
     * Metoda zapisuje otwarty graf do pliku.
     */
    public void saveToFile(String filename) {
        //TODO: zapis grafu do pliku
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


    private double rand (double w_min, double w_max) {
        Random r=new Random();
        return( w_min + (w_max - w_min) * r.nextDouble());
    }


}
