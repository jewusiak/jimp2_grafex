package grafex;


import GUI.Gui;
import grafexExceptions.*;
import javafx.application.Platform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Klasa reprezentująca graf.
 */
public class Graph {


    private final ArrayList<Relation> relations;
    public double[] d = null;
    private List<Integer> incoherent = null;
    private int rows;
    private int columns;
    private Boolean coherency = null;
    private int[] p = null;

    /**
     * Konstruktor pustego grafu
     */
    public Graph() {
        relations = new ArrayList<>();
        rows = 0;
        columns = 0;
    }

    /**
     * Konstruktor który czyta z pliku.
     * @param filename nazwa pliku
     * @throws FileNotFoundException jeśli plik nie istnieje
     * @throws IllegalGraphFormatException jeśli w pliku jest błędne formatowanie
     */
    public Graph(String filename) throws FileNotFoundException, IllegalGraphFormatException {
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
            for (String x : relationPattern.matcher(line).results().map(MatchResult::group).toList()) {
                String[] parts = x.split(":");
                createRelation(n, Integer.parseInt(parts[0].trim()), Double.parseDouble(parts[1].trim()));
            }
            n++;
        }


    }

    /**
     * Konstruktor generujący graf z informacji pobranych z GUI.
     * @param graphGenInfo informacje o żądanym grafie
     */
    public Graph(GraphGenInfo graphGenInfo) {
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

    /**
     * @param w_min minimalna wartość wagi
     * @param w_max maksymalna wartość wagi
     * @return losową wartość wagi
     */
    private static double rand(double w_min, double w_max) {
        Random r = new Random();
        return (w_min + (w_max - w_min) * r.nextDouble());
    }

    /**
     * @param d macierz kosztów dojścia w grafie Dijkstry
     * @param s macierz S
     * @return najmniejszy koszt dojścia (nie w S)
     */
    private static int getIndexOfSmallestD(double[] d, Collection<Integer> s) {
        int ind = -1;
        for (int i = 0; i < d.length; i++)
            if (!s.contains(i)) if (ind == -1) ind = i;
            else if (d[i] < d[ind]) ind = i;
        return ind;

    }

    /**
     * Metoda ustawia wielkość grafu wg. zadanych wartości.
     *
     * @param rows    liczba wierszy
     * @param columns liczba kolumn
     */
    private void setSize(int rows, int columns) {

        if (rows < 1 || columns < 1) throw new IllegalArgumentException("Liczba rzędów i liczba kolumn muszą być >=1!");
        this.rows = rows;
        this.columns = columns;

    }

    /**
     * Metoda zapisuje otwarty graf do pliku.
     *
     * @param filename ścieżka pliku do zapisu
     * @throws IOException         wyjątek występuje w przypadku błędu zapisu
     * @throws EmptyGraphException jeśli graf jest pusty
     */
    public void saveToFile(String filename) throws IOException, EmptyGraphException {
        if (getRows() == 0 || getColumns() == 0) throw new EmptyGraphException();
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

    /**
     * Zwraca wszystkie relacje. Metoda do użycia przy generowaniu widoku.
     *
     * @return lista relacji
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

    /**
     * Tworzy relacje: first -> last o wadze weight.
     *
     * @param first  pierwszy wierzchołek
     * @param last   drugi wierzchołek
     * @param weight waga relacji
     */
    public void createRelation(int first, int last, double weight) {
        if (first < 0 || last >= getSize())
            throw new IndexOutOfBoundsException("Nie można stworzyć relacji między " + first + " " + last + ".");
        relations.add(new Relation(first, last, weight));

    }

    /**
     * Zwraca listę relacji dla danego punktu from.
     * MUSI BYĆ ZAPEWNIONE POSORTOWANIE RELACJI!!
     *
     * @param from wierzchołek
     * @return lista relacji
     */
    public List<Relation> getPointsRelations(int from) {
        List<Relation> adj = new ArrayList<>();
        for (int i = 0; i < relations.size(); i++)
            if (relations.get(i).getFirst() == from) {
                while (relations.get(i).getFirst() == from) {
                    adj.add(relations.get(i++));
                    if (i >= relations.size()) break;
                }
                break;
            }
        return adj;

    }

    /**
     * Zwraca listę id (int) sąsiadów.
     * MUSI BYĆ ZAPEWNIONE POSORTOWANIE RELACJI!!
     *
     * @param from wierzchołek
     * @return lista sąsiadów
     */
    public List<Integer> getAdjacent(int from) {
        List<Integer> adj = new ArrayList<>();
        for (int i = 0; i < relations.size(); i++)
            if (relations.get(i).getFirst() == from) {
                while (relations.get(i).getFirst() == from) {
                    adj.add(relations.get(i++).getLast());
                    if (i >= relations.size()) break;
                }
                break;
            }
        return adj;
    }

    /**
     * Zwraca listę wierzchołków do których nie udało się dotrzeć. Jeżeli nie sprawdzialiśmy spójności, to wykonujemy isCoherent().
     *
     * @return lista wierzchołków do których nie udało się dotrzeć
     */
    public List<Integer> getIncoherent() {
        if (incoherent == null) isCoherent();
        return incoherent;
    }

    /**
     * Sprawdza spójność grafu. Działa an bazie algorytmu BFS.
     *
     * @return true jeśli graf jest spójny, false w przeciwnym wypadku
     */
    public boolean isCoherent() {
        if (coherency != null) return coherency;

        Platform.runLater(() -> Gui.controller.progressBar.setProgress(0));
        double pUnit = 1d / getSize();

        relations.sort(Relation::compareTo);

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
                    Platform.runLater(() -> Gui.controller.progressBar.setProgress(Double.min(Gui.controller.progressBar.getProgress() + pUnit, 1d)));
                }
        }

        incoherent = new ArrayList<>();
        for (int i = 0; i < visited.length; i++) if (visited[i] == 0) incoherent.add(i);

        coherency = incoherent.size() == 0;

        return coherency;

    }

    /**
     * Szuka ścieżki pomiędzy punktami, tworzy macierz kosztów dojścia i poprzedników lub/i z niej korzysta.
     *
     * @param first wierzchołek startowy
     * @param last  wierzchołek końcowy
     * @return ścieżka
     */
    public GraphPath findPath(int first, int last) throws GraphException {
        if (getSize() == 0) throw new EmptyGraphException();
        if (first < 0 || last >= getSize()) throw new GraphIndexOutOfBoundsException(getSize() - 1);
        if (!isCoherent())
            throw new GraphNotCoherentException("Graf nie jest spójny. Wymagana jest spójnosć do algorytmu Dijkstry.", getIncoherent());
        if (p == null || d == null) {


            double pUnit = 1d / getSize();
            Platform.runLater(() -> Gui.controller.progressBar.setProgress(0));

            relations.sort(Relation::compareTo);

            HashSet<Integer> s = new HashSet<>();

            p = new int[getSize()];
            d = new double[getSize()];
            Arrays.fill(p, -1);
            Arrays.fill(d, Double.MAX_VALUE);
            d[first] = 0d;
            int u = getIndexOfSmallestD(d, s);
            while (u != -1) {
                s.add(u);
                Platform.runLater(() -> Gui.controller.progressBar.setProgress(Double.min(Gui.controller.progressBar.getProgress() + pUnit, 1d)));
                for (Relation r : getPointsRelations(u)) {
                    if (s.contains(r.getLast())) continue;
                    if (d[r.getLast()] > d[u] + r.getWeight()) {
                        d[r.getLast()] = d[u] + r.getWeight();
                        p[r.getLast()] = u;
                    }
                }
                u = getIndexOfSmallestD(d, s);
            }


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

    @Override
    public String toString() {
        return "Graph{" + "relations=" + relations + ", incoherent=" + incoherent + ", rows=" + rows + ", columns=" + columns + '}';
    }

    /**
     * Kalkuluje ID wierzchołka o podanych row i column.
     *
     * @param row    wiersz
     * @param column kolumna
     * @return ID wierzchołka
     */
    public int calculatePointID(int row, int column) {
        return row * getColumns() + column;
    }
}
