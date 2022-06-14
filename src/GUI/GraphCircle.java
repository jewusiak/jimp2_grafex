package GUI;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.awt.*;

/**
 * Klasa reprezentująca wierzchołek grafu w widoku.
 */
public class GraphCircle extends Circle {
    private final Point point;

    private final Color defaultColor;


    public GraphCircle(Point point, double radius, int id, Color color) {
        super(point.x, point.y, radius);
        this.point = point;
        defaultColor = color;
        setFill(defaultColor);
        setStrokeWidth(0d);
        setOnMouseEntered(mouseEvent -> {
            GraphCircle.super.setFill(Paint.valueOf("#00ff00"));//"#ff0000"));
        });

        setOnMouseExited(mouseEvent -> GraphCircle.super.setFill(defaultColor));

        setOnMouseClicked(mouseEvent -> {
            if (Gui.controller.selectedEnds == null) return;

            Gui.controller.selectedEnds.add(id);
            setOnMouseEntered(mouseEvent1 -> {
            });
            setOnMouseExited(mouseEvent1 -> {
            });
            Gui.controller.checkNewPaths();
            //System.out.println("clicked");
        });
    }

    public Point getPoint() {
        return point;
    }


}
