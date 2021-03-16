package App.Controller;

import App.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class mainPaneController {

    @FXML
    private Pane playBoard;
    List<Monster> allMonsters = new ArrayList<>();
    AnimationTimer gameLoop;

    private void addMonsters() {
        // start location
        double x = 0.5 * playBoard.getPrefWidth();
        double y = 0.5 * playBoard.getPrefHeight();
        // enemy size
        double width = 30;
        double height = width;

        // create monster data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 0,0);
        Vector2D acceleration = new Vector2D( 0,0);

        // create monster
        Monster monster = new Monster( playBoard, location, velocity, acceleration, width, height);

        // register monster
        allMonsters.add(monster);

    }
     private void startGame() {

        // start game
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // update scene
                allMonsters.forEach(Sprite::display);
            }
        };

        gameLoop.start();

    }

    public void initialize(){

        for(int i = 0; i < Monster.MONSTER_COUNT; i++) {
            addMonsters();
        }
        // run animation loop
        startGame();
    }
}