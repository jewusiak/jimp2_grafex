package GUI;

import grafex.Graph;
import grafexExceptions.GraphNotCoherentException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


import java.io.File;
import java.io.IOException;

public class Gui extends Application{

    private static Stage stage;
    private static Scene scene;
    private static FXMLLoader loader;
    private static Controller controller;
    private static GenerujGraf generujGraf;
    private static Alert alert;

    public static Graph graph = new Graph();

    @Override
    public void start(Stage stage) throws IOException {
        // primary stage setup
        Gui.stage = stage;
        String fxmlFileName = "GUI";
        scene = new Scene(loadFXML(fxmlFileName));

        stage.setScene(scene);
        stage.setTitle("Grafex");
        stage.show();
        stage.setResizable(false);
        controller = loader.getController();

        generujGraf= new GenerujGraf( scene);

        alert=new Alert(scene);
    }


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

            return;
        }
        String filename = selectedFile.getPath();


        Graph g = null;

        try {
            g = new Graph(filename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showSS(e.getMessage());

        }
        setG(g);


        System.out.println(g.toString());


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
