package GUI;

import grafex.Graph;
import grafex.GraphPath;
import grafex.Relation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Gui extends Application {


    private static final int circleRadius = 5;
    private static final int betweenCircles = 3;
    public static Graph graph;
    public static Controller controller;
    public static Scene scene;

    private static Stage stage;
    private static FXMLLoader loader;
    private static GenerujGraf generujGraf;

    private static ColorChange colorChange;
    private static Alert alert;
    //TODO: wybór kolorów w gui
    public static Color c1 = new Color(50/255d, 172/255d, 236/255d, 1);
    public static Color c2 = new Color(1, 0, 0,1);



    public static void changeColor(Color cc1, Color cc2){
        c1=cc1;
        c2=cc2;
    }

    public static void hideGG() {
        generujGraf.hide();
    }

    public static void showGG() {
        generujGraf.show();

    }

    public static void hideCC() {
        colorChange.hideC();
    }
    public static void showCC() {
        colorChange.showC();

    }

    public static void hideSS() {
        alert.hideS();
    }

    public static void showSS(String message) {
        alert.showS(message);
    }


    public static void chooseFile() {
        File initialDirectory = new File(System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz graf");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Pliki grafu", "*.graph"));
        fileChooser.setInitialDirectory(initialDirectory);


        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            //System.out.println("Nie wybrano pliku!");
            showSS("Nie wybrano pliku!");
            setG(new Graph());
            return;
        }
        String filename = selectedFile.getPath();


        Graph g = new Graph();

        try {
            g = new Graph(filename);
        } catch (Exception e) {
            showSS(e.getMessage());

        }
        setG(g);


        //System.out.println(g);


    }

    /**
     * Kalkuluje pozycję wierzchołka na planszy w oparciu o promień, odległość między nimi, oraz ID wierzchołka
     * @param id ID wierzchołka
     * @return pozycja wierzchołka
     */
    private static Point calculateCirclePosition(int id) {
        int xStart = 10;
        int yStart = 10;
        int xID = id % graph.getColumns();
        int yID = id / graph.getColumns();


        return new Point(xStart + xID * (circleRadius * 2 + betweenCircles), yStart + yID * (circleRadius * 2 + betweenCircles));

    }

    /**
     * Kalkuluje kolor pomiędzy dwoma, p=0 - kolor c1, p=1 - kolor c2
     * @param p współczynnik [0,1]
     * @return kolor
     */
    private static Color getColorBetween(double p) {
        p=Double.min(p, 1d);
        return new Color((c2.getRed() * p + c1.getRed() * (1d - p)) , (c2.getGreen() * p + c1.getGreen() * (1d - p)) , (c2.getBlue() * p + c1.getBlue() * (1d - p)) , 1);
    }


    public static void drawGraph(ScrollPane paneIn) {
        drawGraph(paneIn, null);
    }

    /**
     * Rysuje graf na podanym pane, jeżeli d!=null to również koloruje.
     * @param paneIn pane do rysowania
     * @param d macierz kosztów dojścia
     */
    public static void drawGraph(ScrollPane paneIn, double[] d) {
        Pane pane = new Pane();
        pane.setPadding(new Insets(10, 10, 10, 0));

        int r = graph.getRows();
        int c = graph.getColumns();

        for (Relation rel : graph.getRelations()) {
            Point first = calculateCirclePosition(rel.getFirst());
            Point last = calculateCirclePosition(rel.getLast());
            Line l = new Line(first.x, first.y, last.x, last.y);
            pane.getChildren().add(l);
        }

        Double maxLength = null;
        Integer selPid = null;
        if (d != null) {
            maxLength = Arrays.stream(d).max().orElse(1);
            selPid = Gui.controller.selectedEnds.get(0);
        }


        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int pid = graph.calculatePointID(i, j);

                Color color = (Color) Paint.valueOf("#919191");
                if (maxLength != null) {
                    if (pid == selPid)
                        color = (Color) Paint.valueOf("#00ff00");//"#ff0000");
                    else
                        color = getColorBetween(d[graph.calculatePointID(i, j)] / maxLength);
                }
                Circle circle = new GraphCircle(calculateCirclePosition(pid), circleRadius, pid, color);

                pane.getChildren().add(circle);

            }
        }


        paneIn.setContent(pane);

    }


    /**
     * Rysuje ścieżkę na widoku grafu
     * @param pane pane do rysowania
     * @param path ścieżka
     */
    public static void drawPath(ScrollPane pane, GraphPath path) {
        Pane p = new Pane();
        p.getChildren().add(pane.getContent());
        for (int i = 0; i < path.getPoints().size() - 1; i++) {
            Point first = calculateCirclePosition(path.getPoints().get(i));
            Point last = calculateCirclePosition(path.getPoints().get(i + 1));
            Line l = new Line(first.x, first.y, last.x, last.y);
            l.setFill(Paint.valueOf("#000000"));
            l.setStroke(Paint.valueOf("#000000"));
            l.setStrokeWidth(4);
            p.getChildren().add(l);
        }
        pane.setContent(p);
    }

    public static void saveFile() {
        File initialDirectory = new File(System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz jako");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Plik grafu", "*.graph"));
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile == null) {
            //System.out.println("Nie wybrano pliku!");
            return;
        }
        String filename = selectedFile.getPath();

        try {
            graph.saveToFile(filename);
        } catch (Exception ex) {
            showSS(ex.getMessage());
            //System.out.println(ex.getMessage());
        }


    }

    private static Parent loadFXML(String fxml) throws IOException {
        loader = new FXMLLoader(Gui.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    //TODO: zmiana na private
    public static void setG(Graph g) {
        graph = g;
    }

    //TODO: czy przenosimy to do main w grafex?
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        // primary stage setup
        Gui.stage = stage;
        String fxmlFileName = "GUI";
        scene = new Scene(loadFXML(fxmlFileName));

        stage.setScene(scene);
        stage.setTitle("Grafex");
        stage.show();
        stage.setResizable(true);
        controller = loader.getController();

        generujGraf = new GenerujGraf(scene);

        colorChange=new ColorChange(scene);

        alert = new Alert(scene);
        graph = new Graph();
    }


}
