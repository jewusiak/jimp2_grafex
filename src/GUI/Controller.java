package GUI;

import grafex.Graph;
import grafex.GraphPath;
import grafexExceptions.GraphException;
import grafexExceptions.GraphNotCoherentException;
import grafexExceptions.IllegalGraphFormatException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    public ScrollPane pane;

    @FXML
    public ProgressBar progressBar;

    public List<Integer> selectedEnds = null;

    public void otworz(ActionEvent e) {
        Gui.chooseFile();
        rysuj();
    }

    public void zapisz(ActionEvent e) {
        //System.out.println("Zapisz");
        Gui.saveFile();
    }

    public void zamknij(ActionEvent e) {
        Gui.setG(new Graph());
        System.exit(0);
    }

    public void czysc(ActionEvent e) {
        Gui.setG(new Graph());
        rysuj();

    }

    /**
     * Metoda wywoływana po kliknięciu w przycisk wyboru wierzchołków. Umożliwia wierzchołkowi dodanie swojego id do listy wybranych.
     */
    public void wybierz(ActionEvent e) {
        if (Gui.graph.getSize() == 0) Gui.showSS("Graf jest pusty!");
        else if (selectedEnds == null) selectedEnds = new ArrayList<>();
        else {
            Gui.graph.d=null;

            selectedEnds = null;
            rysuj();
        }


    }

    /**
     * Metoda wywoływana po kliknięciu w wierzchołek.
     * selectedEnds.size()==1 - kolorowany jest graf
     * selectedEnds.size()>1  - rysujemy ścieżki na grafie
     */
    public void checkNewPaths() {
        if (selectedEnds == null) return;
        if (selectedEnds.size() < 2) {
            new Thread(() -> {
                try {
                    Platform.runLater(() -> Gui.controller.progressBar.setVisible(true));
                    Gui.graph.findPath(selectedEnds.get(0), selectedEnds.get(0));

                    Platform.runLater(() -> Gui.drawGraph(pane, Gui.graph.d));

                } catch (GraphException e) {
                    Platform.runLater(() -> {

                        Gui.showSS(e.getMessage());
                        selectedEnds = null;
                        Gui.drawGraph(pane, Gui.graph.d);

                    });

                }finally {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> Gui.controller.progressBar.setVisible(false));
                }
            }).start();
        } else {
            new Thread(() -> {

                try {
                    GraphPath res = Gui.graph.findPath(selectedEnds.get(0), selectedEnds.get(selectedEnds.size() - 1));

                    Platform.runLater(() -> {
                        Gui.drawPath(pane, res);
                        Gui.showSS("Długość: " + res.getTotalLength());
                    });
                } catch (GraphException e) {
                    Platform.runLater(() -> {
                        Gui.showSS(e.getMessage());
                        selectedEnds = null;
                        Gui.drawGraph(pane, Gui.graph.d);

                    });

                }
            }).start();
        }
    }

    public void skala(ActionEvent e) {
        Gui.showCC();
    }

    public void generuj(ActionEvent e) {
        Gui.showGG();
        rysuj();
    }

    public void rysuj() {
        Gui.drawGraph(pane);
    }


}
