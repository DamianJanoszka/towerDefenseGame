package App.Models;

import App.GameObjects.Attractor;
import App.GameObjects.Cannon;
import App.GameObjects.CannonMissile;
import App.GameObjects.Monster;
import App.Models.Vector2D;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpriteTest{
    private Cannon cannon;
    private CannonMissile missile;
    private Attractor attractor;
    private Monster monster;

    Pane layer =new Pane();

    @BeforeEach
    public void setUp(){
        cannon = new Cannon(layer, new Vector2D( 0,0), new Vector2D( 0,0), new Vector2D( 0,0), 0, 0);
        missile = new CannonMissile(layer, new Vector2D( 0,0), new Vector2D( 0,0), new Vector2D( 0,0), 0, 0);
        attractor = new Attractor(layer, new Vector2D( 0,0), new Vector2D( 0,0), new Vector2D( 0,0), 0, 0);
        monster = new Monster(layer, new Vector2D( 0,0), new Vector2D( 0,0), new Vector2D( 0,0), 0, 0);
        layer.setLayoutX(500);
        layer.setLayoutY(500);
    }

    @Test
    void shouldSubtractToleranceAmount() {
        monster.setLocation(10,10);
        monster.setLocation(monster.subtractTolerance().getX(),monster.subtractTolerance().getY());

        Vector2D expected = new Vector2D(5,5);

        assertEquals(expected.getX(),monster.getLocation().getX());
        assertEquals(expected.getY(),monster.getLocation().getY());


        monster.setLocation(1,1);
        monster.setLocation(monster.subtractTolerance().getX(),monster.subtractTolerance().getY());

        expected = new Vector2D(0,0);

        assertEquals(expected.getX(),monster.getLocation().getX());
        assertEquals(expected.getY(),monster.getLocation().getY());
    }

    @Test
    void shouldAddToleranceAmount() {
        monster.setLocation(1,1);
        monster.setLocation(monster.addTolerance().getX(),monster.addTolerance().getY());

        Vector2D expected = new Vector2D(6,6);

        assertEquals(expected.getX(),monster.getLocation().getX());
        assertEquals(expected.getY(),monster.getLocation().getY());
    }

    @Test
    void shouldBeInRange() {
        monster.setLocation(14,14);
        missile.setLocation(5,5);
        assertTrue(monster.isInRange(monster,missile));
    }
    @Test
    void shouldntBeInRange() {
        monster.setLocation(16,16);
        missile.setLocation(5,5);
        assertFalse(monster.isInRange(monster,missile));
    }

    @Test
    void shouldBeInCheckPoint() {
        attractor.setLocation(5,5);
        monster.setLocation(9,9);
        assertTrue(attractor.isInCheckPoint(monster,attractor.getLocation()));
    }
    @Test
    void shouldntBeInCheckPoint() {
        attractor.setLocation(5,5);
        monster.setLocation(11,11);
        assertFalse(attractor.isInCheckPoint(monster,attractor.getLocation()));
    }

    @Test
    void shouldBeInFieldOnFire() {
        cannon.setLocation(0,0);
        missile.setLocation(100,100);
        assertTrue(missile.isInFieldOnFire(cannon.getLocation()));
    }
    @Test
    void shouldntBeInFieldOnFire() {
        cannon.setLocation(0,0);
        missile.setLocation(300,300);
        assertFalse(missile.isInFieldOnFire(cannon.getLocation()));
    }

    @Test
    void shouldLostTheWay() {
        cannon.setLocation(0,0);
        missile.setLocation(300,300);
        assertTrue(missile.ifMissileLostTheWay(cannon.getLocation()));
    }
    @Test
    void shouldntLostTheWay() {
        cannon.setLocation(0,0);
        missile.setLocation(100,100);
        assertFalse(missile.ifMissileLostTheWay(cannon.getLocation()));
    }
}