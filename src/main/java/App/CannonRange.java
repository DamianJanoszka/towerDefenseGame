package App;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CannonRange extends Sprite{

    public CannonRange(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public Node createView() {
        double radius = width/2;
        Circle circle = new Circle( radius*2);

        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.BLACK.deriveColor(1, 1, 1, 0.25));

        circle.setFill(Color.BLACK.deriveColor(1, 1, 1, 0.03));

        return circle;
    }
}
