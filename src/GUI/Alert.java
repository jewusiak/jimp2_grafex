package GUI;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Alert {
    private final Stage stage;
    private final Parent root;
    private final FXMLLoader loader;

    private final AlertControl alertControl;

    public Alert(Scene rootScene) throws IOException {
        loader = new FXMLLoader(getClass().getResource("Ale.fxml"));
        root = loader.load();
        alertControl = loader.getController();


        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Alert");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(rootScene.getWindow());
    }

    public void hideS() {
        stage.close();
    }
    public void showS(String messege) {
        alertControl.setMessage(messege);
        stage.showAndWait();
    }

}