package GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ColControl implements Initializable {

    @FXML
    private ColorPicker col1;
    @FXML
    private ColorPicker col2;

    private Color tempC1;
    private Color tempC2;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //System.out.println("Initializing!");
        col1.setValue(Gui.c1);
        col2.setValue(Gui.c2);
    }


    public void przycisk(javafx.event.ActionEvent actionEvent) {
        tempC1 = col1.getValue();
        tempC2 = col2.getValue();
        Color color1 = new Color(Double.min( tempC1.getRed(), 1), Double.min( tempC1.getGreen(), 1), Double.min( tempC1.getBlue(), 1), 1);

        Color color2 = new Color(Double.min( tempC2.getRed(),1),Double.min( tempC2.getGreen(),1),Double.min( tempC2.getBlue(),1),1);

        Gui.changeColor(color1, color2);
        Gui.hideCC();
    }
}
