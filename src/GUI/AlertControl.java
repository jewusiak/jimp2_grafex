package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class AlertControl implements Initializable {
    @FXML
    private Label message;
    @FXML
    private Button okButton;

    public void okButtonController(ActionEvent event) {
        Gui.hideSS();
    }

    public void setMessage(String messageText) {
        message.setText(messageText);
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //System.out.println("Initializing!");
    }
}
