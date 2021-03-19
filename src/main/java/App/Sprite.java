package App;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public abstract class Sprite extends Region {

    Vector2D location;
    Vector2D velocity;
    Vector2D acceleration;


    double maxSpeed=1;
    double missileSpeed=5;
    double maxForce=0.5;
    double maxForceM=0.8;


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
        return toleranceAmount(1.02);
    }
    public Vector2D subtractTolerance(){
        return toleranceAmount(0.98);
    }
    public Vector2D addMissileTolerance(){
        return toleranceAmount(1.04);
    }
    public Vector2D subtractMissileTolerance(){
        return toleranceAmount(0.96);
    }
    public Vector2D toleranceAmount(double amount){
        double x = location.x*amount;
        double y = location.y*amount;
        return new Vector2D(x,y);
    }
    public void setLocation(double x, double y){
        location.x=x;
        location.y=y;
    }
    public void applyForce(Vector2D force){
        acceleration.add(force);
    };
    public void attack(Vector2D target){
        followTarget(target,location,missileSpeed,maxForceM,velocity);
    }
    public void moveMissile() {
        moveObject(acceleration,velocity,missileSpeed,location);
    }
    public void follow(Vector2D target){
        followTarget(target,location,maxSpeed,maxForce,velocity);
    }

    public void moveMonster() {
        moveObject(acceleration,velocity,maxSpeed,location);
    }
    public void moveObject(Vector2D acceleration, Vector2D velocity, double maxSpeed, Vector2D location){
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        location.add(velocity);
        acceleration.multiply(0);
    }
    public void followTarget(Vector2D target, Vector2D location, double maxSpeed, double maxForce, Vector2D velocity){
        Vector2D coordinate = Vector2D.subtract(target,location);

        coordinate.normalize();
        coordinate.multiply(maxSpeed);

        Vector2D steer = Vector2D.subtract(coordinate, velocity);

        steer.limit(maxForce);

        applyForce(steer);
    }

}