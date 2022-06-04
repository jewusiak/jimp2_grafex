package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
public class GenControl implements Initializable {


    public void przyciskGen(ActionEvent event) {
        Gui.hideGG();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Initializing!");
    }
}
