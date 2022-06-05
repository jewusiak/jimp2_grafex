package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import grafex.Graph;
import grafex.GraphGenInfo;
import grafexExceptions.GraphException;
import grafexExceptions.GraphNotCoherentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import GUI.Gui;

public class GenControl implements Initializable {


    @FXML
    private TextField rowsField;
    @FXML
    private TextField columnsField;
    @FXML
    private TextField minWeightField;
    @FXML
    private TextField maxWeightField;
    @FXML
    private RadioButton sspoj;
    @FXML
    private RadioButton nspoj;
    @FXML
    private RadioButton ranspoj;


    public void przyciskGen(ActionEvent event){
        int col=Integer.parseInt(columnsField.getText());
        int rows=Integer.parseInt(rowsField.getText());
        double minWeight=Double.parseDouble(minWeightField.getText());
        double maxWeight=Double.parseDouble(maxWeightField.getText());
        GraphGenInfo.Coherency coh= GraphGenInfo.Coherency.YES;
        if (sspoj.isSelected()){
            coh= GraphGenInfo.Coherency.YES;
        }
        else if (nspoj.isSelected()){
            coh= GraphGenInfo.Coherency.NO;
        }
        else if (ranspoj.isSelected()){
            coh= GraphGenInfo.Coherency.RANDOM;
        }

        Graph g = new Graph(new GraphGenInfo(rows, col, coh, minWeight, maxWeight));
        Gui.setG(g);

        Gui.hideGG();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Initializing!");
    }
}
