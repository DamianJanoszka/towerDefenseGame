package App;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Cannon extends Sprite{

    public Cannon(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public Node createView() {
        double radius = width / 2;
        Circle circle = new Circle( radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);
        Rectangle rent = new Rectangle(radius-6,radius-6,35,14);
        rent.setFill(Color.PURPLE.deriveColor(1, 1, 1, 0.5));

        circle.setStroke(Color.BLACK);
        circle.setFill(Color.BLACK.deriveColor(1, 1, 1, 1));

        Shape shape = Shape.union(rent,circle);

        return shape;
    }
}
