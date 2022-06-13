package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ColorChange {
    private final Stage colorStage;
    private final Parent colorRoot;
    private final FXMLLoader colorLoader;

    public ColorChange(Scene rootScene) throws IOException {
        colorLoader = new FXMLLoader(getClass().getResource("Color.fxml"));
        colorRoot = colorLoader.load();


        colorStage = new Stage();
        colorStage.setScene(new Scene(colorRoot));
        colorStage.setTitle("Zmień motyw");
        colorStage.initModality(Modality.APPLICATION_MODAL);
        colorStage.initOwner(rootScene.getWindow());

    }

    public void hideC() {
        colorStage.close();
    }

    public void showC() {
        colorStage.showAndWait();
    }
}
