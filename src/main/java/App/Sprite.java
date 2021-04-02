package App;

import App.GameSettings.Settings;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public abstract class Sprite extends Region {

    Vector2D location;
    Vector2D velocity;
    Vector2D acceleration;

    Node view;

    // view dimensions
    double width;
    double height;
    double centerX;
    double centerY;
    double angle;
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

        setRotate(Math.toDegrees( angle));
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
        return toleranceAmount(1.05);
    }
    public Vector2D subtractMissileTolerance(){
        return toleranceAmount(0.95);
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
    public void followMonster(Vector2D target){
        Vector2D range = Vector2D.subtract(target,location);
        double distance = range.magnitude();
        if(distance < Settings.CANNON_RANGE){
        follow(target,location,Settings.MISSILE_SPEED,Settings.MISSILE_FORCE,velocity);}
    }
    public void moveMissile() {
        moveObject(acceleration,velocity,Settings.MISSILE_SPEED,location);
    }
    public void followRoute(Vector2D target){
        follow(target,location,Settings.MONSTER_SPEED,Settings.MONSTER_FORCE,velocity);
    }
    public void moveMonster() {
        moveObject(acceleration,velocity,Settings.MONSTER_SPEED,location);
    }
    public void moveCannon(){
        velocity.add(acceleration);
        velocity.limit(Settings.MONSTER_SPEED);
        angle = velocity.heading2D();
        acceleration.multiply(0);
    }
    public void moveObject(Vector2D acceleration, Vector2D velocity, double maxSpeed, Vector2D location){
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        location.add(velocity);
        acceleration.multiply(0);
    }
    public void follow(Vector2D target, Vector2D location, double maxSpeed, double maxForce, Vector2D velocity){
        Vector2D coordinate = Vector2D.subtract(target,location);

        coordinate.normalize();
        coordinate.multiply(maxSpeed);

        Vector2D steer = Vector2D.subtract(coordinate, velocity);

        steer.limit(maxForce);

        applyForce(steer);
    }
    public boolean isInRange(Monster monster, CannonMissile missile){
        return monster.addMissileTolerance().isGreaterThan(missile.getLocation()) &&
                        (monster.subtractMissileTolerance().isLessThan(missile.getLocation()));
    }
    public boolean isInCheckPoint(Monster monster, Vector2D location){
        return monster.addTolerance().isGreaterThan(location) &&
                (monster.subtractTolerance().isLessThan(location));
    }
    public boolean isInFieldOnFire(Vector2D target){
        Vector2D range = Vector2D.subtract(target,location);
        double distance = range.magnitude();
        return distance<Settings.CANNON_RANGE;
    }

    public void setLocationOffset(double offsetX, double offsetY) {
        location.x += offsetX;
        location.y += offsetY;
    }
    public boolean ifMissileLostTheWay(Cannon cannon){
        Vector2D range = Vector2D.subtract(cannon.getLocation(),location);
        double distance = range.magnitude();
        return distance > 300;
    }


    public Monster getNearestMonsterV2(List<Monster> listOfMonster, Monster alternativeMonster, Vector2D canonLocation){

        Stream<Monster> monsters = listOfMonster.stream();

        return monsters
                .filter(monster -> monster.isInFieldOnFire(canonLocation))
                .min(Comparator.comparing(Monster::getMonsterID))
                .orElse(alternativeMonster);

    }



}