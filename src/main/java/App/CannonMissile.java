package App;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CannonMissile extends Sprite{

    private int missileDamage;
    private int cannonID;


    public CannonMissile(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
        setMissileDamage(10);
        setCannonID(0);
    }

    public int getMissileDamage() {
        return missileDamage;
    }

    public void setMissileDamage(int missileDamage) {
        this.missileDamage = missileDamage;
    }

    public void setCannonID(int cannonID) {
        this.cannonID = cannonID;
    }

    public int getCannonID() {
        return cannonID;
    }

    @Override
    public Node createView() {
        double radius = width / 2;
        Circle circle = new Circle( radius);

        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.PURPLE);
        circle.setFill(Color.PURPLE.deriveColor(1, 1, 1, 0.7));
        return circle;
    }
}
