package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.awt.*;

public class GraphCircle extends Circle {
    private final Point point;
    private final int id;


    public Point getPoint() {
        return point;
    }

    public GraphCircle(Point point, double radius, int id) {
        super(point.x, point.y, radius);
        this.point=point;
        this.id=id;
        setFill(Paint.valueOf("#919191"));
        setStrokeWidth(0d);
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphCircle.super.setFill(Paint.valueOf("#ff0000"));
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphCircle.super.setFill(Paint.valueOf("#919191"));
            }
        });
    }


}
