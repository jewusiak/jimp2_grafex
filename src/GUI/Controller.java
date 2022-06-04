package GUI;

import grafex.Graph;
import grafexExceptions.GraphNotCoherentException;
import javafx.event.ActionEvent;

public class Controller {

    public void otworz(ActionEvent e){
        System.out.println("Otwórz");
        Gui.chooseFile();
    }
    public void zapisz(ActionEvent e){
     System.out.println("Zapisz");
    }
    public void zamknij(ActionEvent e){
        System.out.println("Zamknij");
    }
    public void czysc(ActionEvent e){
        Gui.setG(new Graph());
    }

    public void wybierz(ActionEvent e) throws GraphNotCoherentException {
        System.out.println(Gui.graph.findPath(1,2));
    }
    public void skala(ActionEvent e){
        System.out.println("Zmień skalę kolorów");
    }

    public void generuj(ActionEvent e){
        Gui.showGG();
    }



}
