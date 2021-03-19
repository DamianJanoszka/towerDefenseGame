package App;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cannon extends Sprite{
    public static int CANNON_COUNT=1;
    public Cannon(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public Node createView() {
        double radius = width / 1.5;
        Circle circle = new Circle( radius);

        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.BLACK);
        circle.setFill(Color.BLACK.deriveColor(1, 1, 1, 1));
        return circle;
    }
}
