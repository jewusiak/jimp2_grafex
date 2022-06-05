package GUI;

import grafex.Graph;
import grafexExceptions.GraphException;
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
        Gui.setG(new Graph());
    }
    public void czysc(ActionEvent e){
        Gui.setG(new Graph());
    }

    public void wybierz(ActionEvent e) {
        try {
            //TODO: jeżeli graf nie został wczytany (czyli Gui.graph=null, to co wtedy? Wywala nullpointerexception
            System.out.println(Gui.graph.findPath(1, 2));
        } catch (GraphException ex) {
            System.out.println(ex.getMessage());
            Gui.showSS(ex.getMessage());

        }


    }
    public void skala(ActionEvent e){
        System.out.println("Zmień skalę kolorów");
    }

    public void generuj(ActionEvent e){
        Gui.showGG();
    }



}
