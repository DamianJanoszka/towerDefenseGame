package App;


import App.GameSettings.Settings;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Monster extends Sprite {

    private int monsterHealth;
    private int flag;

    public Monster(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
        setMonsterHealth(Settings.MONSTER_HEALTH);
        setFlag(Settings.MONSTER_FLAG);
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

    public void healthAfterHit(CannonMissile cannonMissile){
        int health = getMonsterHealth();
        int amount = cannonMissile.getMissileDamage();
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