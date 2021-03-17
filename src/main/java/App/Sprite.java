package App;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public abstract class Sprite extends Region {

    Vector2D location;
    Vector2D velocity;
    Vector2D acceleration;


    double maxSpeed=2.0;
    double maxForce=0.08;


    Node view;

    // view dimensions
    double width;
    double height;
    double centerX;
    double centerY;

    Pane layer = null;

    public Sprite(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {

        this.layer = layer;
        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.width = width;
        this.height = height;
        this.centerX = width / 2;
        this.centerY = height / 2;

        this.view = createView();

        setPrefSize(width, height);

        // add view to this node
        getChildren().add( view);

        // add this node to layer
        layer.getChildren().add( this);

    }
    public abstract Node createView();

     // Update position

    public void display() {

       relocate(location.x - centerX, location.y - centerY);
    }

    public Vector2D getLocation(){
        return location;
    }
    public void applyForce(Vector2D force){
        acceleration.add(force);
    };

    public void follow(Vector2D target){
       Vector2D coordinate = Vector2D.subtract(target,location);

       double distance = coordinate.magnitude();
       coordinate.normalize();
       coordinate.multiply(maxSpeed);

       Vector2D steer = Vector2D.subtract(coordinate, velocity);

        steer.limit(maxForce);

        applyForce(steer);
    }

    public void move() {
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        location.add(velocity);
        //angle=velocity.heading2D();
        acceleration.multiply(0);
    }

}