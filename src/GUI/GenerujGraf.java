package GUI;



import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GenerujGraf {
    private final Stage stage;
    private final Parent root;
    private final FXMLLoader loader;
    private GenControl genControl;


    public GenerujGraf(Scene rootScene) throws IOException {
        loader = new FXMLLoader(getClass().getResource("Gen.fxml"));
        root = loader.load();


        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Generuj graf");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(rootScene.getWindow());
    }
    public void hide() {
        stage.close();
    }
    public void show() {
        stage.showAndWait();
    }






}