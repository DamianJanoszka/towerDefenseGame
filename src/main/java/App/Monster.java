package App;


import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Sprite {

    public static int MONSTER_COUNT = 4;
    public static int MONSTER_HEALTH=50;
    public static int MONSTER_FLAG=0;
    private int monsterHealth;
    private int flag;

    public Monster(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
        setMonsterHealth(MONSTER_HEALTH);
        setFlag(MONSTER_FLAG);
    }

    public int getMonsterHealth() {
        return monsterHealth;
    }

    public void setMonsterHealth(int monsterHealth) {
        this.monsterHealth = monsterHealth;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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