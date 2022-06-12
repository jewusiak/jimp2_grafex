package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.awt.*;

public class GraphCircle extends Circle {
    private final Point point;
    private final int id;

    private final Color defaultColor;


    public Point getPoint() {
        return point;
    }





    public GraphCircle(Point point, double radius, int id, Color color) {
        super(point.x, point.y, radius);
        this.point=point;
        this.id=id;
        defaultColor=color;
        setFill(defaultColor);
        setStrokeWidth(0d);
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphCircle.super.setFill(Paint.valueOf("#00ff00"));//"#ff0000"));
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphCircle.super.setFill(defaultColor);
            }
        });

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Gui.controller.selectedEnds==null) return;

                Gui.controller.selectedEnds.add(id);
                setOnMouseEntered(mouseEvent1 -> {});
                setOnMouseExited(mouseEvent1 -> {});
                Gui.controller.checkNewPaths();
                System.out.println("clicked");
            }
        });
    }


}
