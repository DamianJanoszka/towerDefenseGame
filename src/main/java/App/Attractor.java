package App;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Attractor extends Sprite{
    public static final int ATTRACTOR_COUNT = 3;

    public Attractor(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public Node createView() {

        Rectangle rectangle = new Rectangle(width,height);
        rectangle.setStroke(Color.YELLOWGREEN);
        rectangle.setFill(Color.YELLOWGREEN.deriveColor(1,1,1,0.5));
        return rectangle;
    }
}
