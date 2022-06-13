package GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class ColControl implements Initializable {

    @FXML
    private RadioButton motyw1;
    @FXML
    private RadioButton motyw2;
    @FXML
    private RadioButton motyw3;
    @FXML
    private RadioButton motyw4;
    @FXML
    private RadioButton motyw5;
    @FXML
    private RadioButton motyw6;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Initializing!");
    }
}
