package App;


import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Sprite {

    public static int MONSTER_COUNT = 3;
    public static int MONSTER_HEALTH=50;

    private int monsterHealth;

    public Monster(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
        setMonsterHealth(MONSTER_HEALTH);
    }

    public int getMonsterHealth() {
        return monsterHealth;
    }

    public void setMonsterHealth(int monsterHealth) {
        this.monsterHealth = monsterHealth;
    }

    public void healthAfterHit(Missile missile){
        int health = getMonsterHealth();
        int amount = missile.getMissileDamage();
        setMonsterHealth(health-amount);
    }

    @Override
    public Node createView() {
        double radius = width / 2;

        Circle circle = new Circle( radius);

        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.BLUE);
        circle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.4));
        return circle;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "location=" + location +
                '}';
    }
}