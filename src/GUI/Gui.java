package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Gui extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GUI.fxml")));
        primaryStage.setTitle("Grafex");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args){launch(args);}
}
