package grafex;


import org.jetbrains.annotations.NotNull;
import org.junit.platform.engine.support.discovery.SelectorResolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Graph {


    private final List<Relation> relations;

    private List<Integer> incoherent = null;

    private int rows;
    private int columns;


    /*
     * Prywatny konstruktor do użycia przy generowniu i wczytywaniu grafu z pliku.
     */
    //TODO: czy ten konstuktor ma być private?
    public Graph() {
        relations = new ArrayList<>();
    }

    /*
     * Konstruktor który czyta z pliku. - DONE
     */
    public Graph(String filename) throws Exception {
        this();

        if (!Pattern.compile("\\.graph$").matcher(filename).find()) throw new FileNotFoundException("Nieprawidłowe rozszerzenie / nazwa pliku");

        Scanner s = new Scanner(new File(filename));

        if (s.hasNextLine()) {
            String sline = s.nextLine();
            String[] slines = sline.trim().split("\\s+");
            if (!Pattern.matches("\\s*\\d+\\s+\\d+\\s*", sline) || slines.length != 2)
                throw new IllegalArgumentException("Zły format rozmiaru grafu w linii 1.");
            setSize(Integer.parseInt(slines[0]), Integer.parseInt(slines[1]));
        }

        int n = 0;
        Pattern relationPattern = Pattern.compile("\\s*\\d+\\s*:\\s*\\d+.?\\d*");
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (!line.matches("^(\\s*\\d+\\s*:\\s*\\d+.?\\d*\\s*)*$"))
                throw new IllegalFormatFlagsException("Zły format w linii " + (n + 2) + "!");
            for (String x : relationPattern.matcher(line).results().map(MatchResult::group).collect(Collectors.toList())) {
                String[] parts = x.split(":");
                createRelation(n, Integer.parseInt(parts[0].trim()), Double.parseDouble(parts[1].trim()));
            }
            n++;
        }


    }

    /*
     * Konstruktor generujący graf z informacji pobranych z GUI. - DONE?
     */
    public Graph(GraphGenInfo graphGenInfo) {
        //TODO: napisanie konstruktora grafu z GUI
        this();
        //ustwaiamy wielkość grafu jak wyżej i dalej generujemmy relacje
        double w_min = graphGenInfo.getWeightBottom();
        double w_max = graphGenInfo.getWeightTop();
        int rows = graphGenInfo.getRows();
        int columns = graphGenInfo.getColumns();
        int probability = 3; // procentowa szansa na wygenerowanie niespójnego wierzchołka

        setSize(rows, columns);

        if (graphGenInfo.getCoherency() == GraphGenInfo.Coherency.YES) {

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {

                    if (r != rows - 1 && c != columns - 1) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    } else if (r == rows - 1 && c != columns - 1) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));

                    } else if (r != rows - 1 && c == columns - 1) {
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    }
                }

            }
        } else if (graphGenInfo.getCoherency() == GraphGenInfo.Coherency.NO) {

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    if (r == 0 && c == 0) {
                        continue;
                    } else if (r != rows - 1 && c != columns - 1) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    } else if (r == rows - 1 && c != columns - 1) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));

                    } else if (r != rows - 1 && c == columns - 1) {
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    }
                }

            }

        } else if (graphGenInfo.getCoherency() == GraphGenInfo.Coherency.RANDOM) {

            Random coh = new Random();

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {

                    int prob = coh.nextInt(100);

                    if (r != rows - 1 && c != columns - 1 && prob > probability) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    } else if (r == rows - 1 && c != columns - 1 && prob > probability) {
                        createRelation(c + r * columns, c + 1 + r * columns, rand(w_min, w_max));
                        createRelation(c + 1 + r * columns, c + r * columns, rand(w_min, w_max));

                    } else if (r != rows - 1 && c == columns - 1 && prob > probability) {
                        createRelation(c + r * columns, c + columns + r * columns, rand(w_min, w_max));
                        createRelation(c + columns + r * columns, c + r * columns, rand(w_min, w_max));
                    }
                }

            }
        }

    }

    /*
     * Metoda ustawia wielkość grafu wg. zadanych wartości.
     */
    private void setSize(int rows, int columns) {

        if (rows < 1 || columns < 1) throw new IllegalArgumentException("Liczba rzędów i liczba kolumn muszą być >=1!");
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

    private double rand(double w_min, double w_max) {
        Random r = new Random();
        return (w_min + (w_max - w_min) * r.nextDouble());
    }

    /*
     * Zwraca listę wierzchołków do których nie udało się dotrzeć. Jeżeli nie sprawdzialiśmy spójności, to wykonujemy isCoherent().
     */
    public List<Integer> getIncoherent() {
        if (incoherent == null) isCoherent();
        return incoherent;
    }

    /*
     * Sprawdza spójność grafu. Działa an bazie algorytmu BFS.
     */
    public boolean isCoherent() {
        Queue<Integer> q = new ArrayDeque<>();


        Integer[] visited = new Integer[getSize()];
        Arrays.fill(visited, 0);
        visited[0] = 1;
        q.add(0);
        while (!q.isEmpty()) {
            int v = q.poll();

            for (int x : getAdjacent(v))
                if (visited[x] != 1) {
                    q.add(x);
                    visited[x] = 1;
                }
        }

        incoherent = new ArrayList<>();
        for (int i = 0; i < visited.length; i++) if (visited[i] == 0) incoherent.add(i);

        return incoherent.size() == 0;

    }


}
