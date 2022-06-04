package grafex;


import grafexExceptions.GraphNotCoherentException;
import grafexExceptions.IllegalGraphFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.MatchResult;
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

        if (!Pattern.compile("\\.graph$").matcher(filename).find())
            throw new FileNotFoundException("Nieprawidłowe rozszerzenie / nazwa pliku");

        Scanner s = new Scanner(new File(filename));

        if (s.hasNextLine()) {
            String sline = s.nextLine();
            String[] slines = sline.trim().split("\\s+");
            if (!Pattern.matches("\\s*\\d+\\s+\\d+\\s*", sline) || slines.length != 2)
                throw new IllegalGraphFormatException("Zły format rozmiaru grafu.", 1);
            setSize(Integer.parseInt(slines[0]), Integer.parseInt(slines[1]));
        }

        int n = 0;
        Pattern relationPattern = Pattern.compile("\\s*\\d+\\s*:\\s*\\d+.?\\d*");
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (!line.matches("^(\\s*\\d+\\s*:\\s*\\d+.?\\d*\\s*)*$"))
                throw new IllegalGraphFormatException("Zły format w pliku grafu!", n + 2);
            for (String x : relationPattern.matcher(line).results().map(MatchResult::group).collect(Collectors.toList())) {
                String[] parts = x.split(":");
                createRelation(n, Integer.parseInt(parts[0].trim()), Double.parseDouble(parts[1].trim()));
            }
            n++;
        }


    }

    /*
     * Konstruktor generujący graf z informacji pobranych z GUI. - Filip DONE?
     */
    public Graph(GraphGenInfo graphGenInfo) {
        //TODO: napisanie konstruktora grafu z GUI
        this();
        //ustwaiamy wielkość grafu jak wyżej i dalej generujemmy relacje
        double w_min = graphGenInfo.getWeightBottom();
        double w_max = graphGenInfo.getWeightTop();
        int rows = graphGenInfo.getRows();
        int columns = graphGenInfo.getColumns();
        int probability = 14; // procentowa szansa na wygenerowanie niespójnego wierzchołka

        setSize(rows, columns);

        if (graphGenInfo.getCoherency() != GraphGenInfo.Coherency.RANDOM) {

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    if (r == 0 && c == 0 && graphGenInfo.getCoherency() == GraphGenInfo.Coherency.NO) {

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

    private static double rand(double w_min, double w_max) {
        Random r = new Random();
        return (w_min + (w_max - w_min) * r.nextDouble());
    }

    private static int getIndexOfSmallestD(double[] d, List<Integer> s) {
        int ind = -1;
        for (int i = 0; i < d.length; i++)
            if (!s.contains(i)) if (ind == -1) ind = i;
            else if (d[i] < d[ind]) ind = i;
        return ind;

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
     * Metoda zapisuje otwarty graf do pliku. -Filip
     */
    public void saveToFile(String filename) throws IOException {
        //TODO: zapis grafu do pliku
        relations.sort(Relation::compareTo);
        FileWriter writer = new FileWriter(filename);
        writer.write("" + getRows() + " " + getColumns() + "\n");
        int ile_rel = 0;
        for (int i = 0; i < getSize(); i++) {
            while (i == relations.get(ile_rel).getFirst()) {
                writer.write(relations.get(ile_rel).getLast() + " :" + relations.get(ile_rel).getWeight() + " ");
                ile_rel++;
                if (ile_rel >= relations.size()) break;
            }
            writer.write("\n");
        }
        writer.close();
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
        if (first < 0 || last >= getSize())
            throw new IndexOutOfBoundsException("Nie można stworzyć relacji między " + first + " " + last + ".");
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

    public GraphPath findPath(int first, int last) throws GraphNotCoherentException {
        if (first < 0 || last >= getSize())
            throw new IndexOutOfBoundsException("ID wierzchołków musi zawierać się między 0 a " + (getSize() - 1));
        if (!isCoherent())
            throw new GraphNotCoherentException("Graf nie jest spójny. Wymagana jest spójnosć do algorytmu Dijkstry.", getIncoherent());
        List<Integer> s = new ArrayList<>();
        Integer[] p = new Integer[getSize()];
        double[] d = new double[getSize()];
        Arrays.fill(p, -1);
        Arrays.fill(d, Double.MAX_VALUE);
        d[first] = 0;
        int u = getIndexOfSmallestD(d, s);
        while (u != -1) {
            s.add(u);
            for (Relation r : getPointsRelations(u)) {
                if (s.contains(r.getLast())) continue;
                if (d[r.getLast()] > d[u] + r.getWeight()) {
                    d[r.getLast()] = d[u] + r.getWeight();
                    p[r.getLast()] = u;
                }
            }
            u = getIndexOfSmallestD(d, s);
        }

        GraphPath gp = new GraphPath();
        int lt = last;
        while (lt != -1) {
            gp.addAtFront(lt);
            lt = p[lt];
        }
        gp.setTotalLength(d[last]);

        return gp;
    }
}
