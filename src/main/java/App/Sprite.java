package App;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public abstract class Sprite extends Region {

    Vector2D location;
    Vector2D velocity;
    Vector2D acceleration;


    double maxSpeed=0.5;
    double maxForce=0.05;


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
    public Vector2D addTolerance(){
        double x = location.x*1.02;
        double y = location.y*1.02;
        return new Vector2D(x,y);
    }
    public Vector2D subtractTolerance(){
        double x = location.x*0.98;
        double y = location.y*0.98;
        return new Vector2D(x,y);
    }
    public void setLocation(double x, double y){
        location.x=x;
        location.y=y;
    }
    public void applyForce(Vector2D force){
        acceleration.add(force);
    };

    public void follow(Vector2D target){
       Vector2D coordinate = Vector2D.subtract(target,location);

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
        acceleration.multiply(0);
    }

}