package GUI;

import grafex.Graph;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Gui extends Application {


    public static Graph graph;
    private static Stage stage;
    private static Scene scene;
    private static FXMLLoader loader;
    private static Controller controller;
    private static GenerujGraf generujGraf;
    private static Alert alert;

    public static void hideGG() {
        generujGraf.hide();
    }

    public static void showGG() {
        generujGraf.show();
    }

    public static void hideSS() {
        alert.hideS();
    }

    public static void showSS(String message) {
        alert.showS(message);
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

        alert = new Alert(scene);
        graph = new Graph();
    }


    //TODO: error message jeżeli czytanie grafu rzuci exception
    public static void chooseFile() {
        File initialDirectory = new File(System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz graf");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Pliki grafu", "*.graph"));
        fileChooser.setInitialDirectory(initialDirectory);


        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            System.out.println("Nie wybrano pliku!");
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


        System.out.println(g);


    }

    public static void drawGraph(ScrollPane paneIn) {
        Pane pane = new Pane();
        int circleRadius=2;
        int betweenCircles=1; //pomiędzy najbliższymi 2 równoległymi stycznymi każdego z okręgów

        int c = graph.getRows(); // Zamieniłem tu miejscami r i c aby generowało widok w dobrą stronę
        int r = graph.getColumns();
        int x = 10;
        int y = 10;
        for (int i = 0; i < r; i++) {
            y = 10;
            x += circleRadius*2+betweenCircles;
            for (int j = 0; j < c; j++) {
                Circle circle = new Circle(x, y, circleRadius);
                pane.getChildren().add(circle);
                y += circleRadius*2+betweenCircles;

            }
        }
        paneIn.setContent(pane);
    }

    public static void saveFile() {
        File initialDirectory = new File(System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz jako");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Plik grafu", "*.graph"));
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile == null) {
            System.out.println("Nie wybrano pliku!");
            return;
        }
        String filename = selectedFile.getPath();

        try {
            graph.saveToFile(filename);
        } catch (Exception ex) {
            showSS(ex.getMessage());
            System.out.println(ex.getMessage());
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


}
