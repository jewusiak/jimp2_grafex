package GUI;

import grafex.Graph;
import grafexExceptions.GraphException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class Controller {
    @FXML
    private ScrollPane pane;


    public void otworz(ActionEvent e) {
        Gui.chooseFile();
        rysuj();
    }

    public void zapisz(ActionEvent e) {
        System.out.println("Zapisz");
        Gui.saveFile();
    }

    public void zamknij(ActionEvent e) {
        Gui.setG(new Graph());
    }

    public void czysc(ActionEvent e) {
        Gui.setG(new Graph());
        rysuj();

    }

    public void wybierz(ActionEvent e) {
        try {
            //TODO: jeżeli graf nie został wczytany (czyli Gui.graph=null, to co wtedy? Wywala nullpointerexception
            System.out.println(Gui.graph.findPath(1, 2));
        } catch (GraphException ex) {
            System.out.println(ex.getMessage());
            Gui.showSS(ex.getMessage());

        } catch (NullPointerException ex) {

            Gui.showSS("Graf pusty. Wybierz graf");
        }


    }

    public void skala(ActionEvent e) {
        System.out.println("Zmień skalę kolorów");
    }

    public void generuj(ActionEvent e) {
        Gui.showGG();
        rysuj();
    }

    public void rysuj() {
        Gui.drawGraph(pane);
    }


    public void wybierzWierz(ActionEvent e) {
    }


}
