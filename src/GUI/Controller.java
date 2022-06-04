package GUI;

import grafex.Graph;
import grafexExceptions.GraphNotCoherentException;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Controller {

    public void otworz(ActionEvent e){
        Gui.chooseFile();
    }
    public void zapisz(ActionEvent e){
     System.out.println("Zapisz");
     Gui.saveFile();
    }
    public void zamknij(ActionEvent e){
        System.out.println("Zamknij");
    }
    public void czysc(ActionEvent e){
        Gui.setG(new Graph());
    }

    public void wybierz(ActionEvent e) {

        try {
            System.out.println(Gui.graph.findPath(1,2));
        } catch (GraphNotCoherentException ex) {
            System.out.println(ex.getMessage());
        }


    }
    public void skala(ActionEvent e){
        System.out.println("Zmień skalę kolorów");
    }

    public void generuj(ActionEvent e){
        Gui.showGG();
    }



}
