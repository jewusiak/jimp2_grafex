package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ColorChange {
    private Stage colorStage;
    private Parent colorRoot;
    private FXMLLoader colorLoader;

    public ColorChange(Scene rootScene) throws IOException {
        colorLoader = new FXMLLoader(getClass().getResource("Gen.fxml"));
        colorRoot = colorLoader.load();


        colorStage = new Stage();
        colorStage.setScene(new Scene(colorRoot));
        colorStage.setTitle("Zmień motyw");
        colorStage.initModality(Modality.APPLICATION_MODAL);
        colorStage.initOwner(rootScene.getWindow());
    }

    public void hide() {
        colorStage.close();
    }

    public void show() {
        colorStage.showAndWait();
    }
}
