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

    public static Graph graph;

    @Override
    public void start(Stage stage) throws IOException {
        // primary stage setup
        Gui.stage = stage;
        String fxmlFileName = new String("GUI");
        scene = new Scene(loadFXML(fxmlFileName));

        stage.setScene(scene);
        stage.setTitle("Grafex");
        stage.show();
        stage.setResizable(false);
        controller = loader.getController();

        generujGraf= new GenerujGraf( scene);
    }


        public static void hideGG() {
            generujGraf.hide();
         }

    public static void showGG() {
        generujGraf.show();
    }

    public static void chooseFile(){
        File initialDirectory = new File(System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open source File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(stage);
        String filename = selectedFile.getName();


        Graph g = null;

        try {
            g = new Graph(filename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setG(g);





    }

    public static void saveFile() {
        File initialDirectory = new File(System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open source File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showSaveDialog(stage);
        String filename = selectedFile.getName();

        try {
            graph.saveToFile(filename);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }


    }



    private static Parent loadFXML(String fxml) throws IOException {
        loader = new FXMLLoader(Gui.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    public static void setG(Graph g)
    {
        graph=g;
    }



    public static void main(String[] args){
        launch(args);
    }






}
