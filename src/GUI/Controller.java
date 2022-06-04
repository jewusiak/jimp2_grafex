package GUI;

import javafx.event.ActionEvent;

public class Controller {

    public void otworz(ActionEvent e){
        System.out.println("Otwórz");
    }
    public void zapisz(ActionEvent e){
     System.out.println("Zapisz");
    }
    public void zamknij(ActionEvent e){
        System.out.println("Zamknij");
    }
    public void czysc(ActionEvent e){
        System.out.println("Czyść");
    }

    public void wybierz(ActionEvent e){
        System.out.println("Wybierz punkty ścieżki");
    }
    public void skala(ActionEvent e){
        System.out.println("Zmień skalę kolorów");
    }

    public void generuj(ActionEvent e){
        Gui.showGG();
    }



}
