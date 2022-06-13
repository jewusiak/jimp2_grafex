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

    private javafx.scene.paint.Color tempC1;
    private javafx.scene.paint.Color tempC2;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Initializing!");
    }


    public void przycisk(javafx.event.ActionEvent actionEvent) {
        tempC1 = col1.getValue();
        tempC2 = col2.getValue();
        float g1=(float)tempC1.getGreen();
        float g2=(float)tempC2.getGreen();
        if (g1>1){
            g1=1;
        }
        if (g2>1){
            g2=1;
        }
        java.awt.Color color1 = new java.awt.Color((float)tempC1.getRed(), g1,  (float)tempC1.getBlue());
        java.awt.Color color2 = new java.awt.Color( (float)tempC2.getRed(), g2,  (float)tempC2.getBlue());

        Gui.changeColor(color1, color2);
        Gui.hideCC();
    }
}
