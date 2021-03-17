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
    List<Attractor> allAttractors = new ArrayList<>();
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
    private void addAttractor() {

        double x = 0.1 * playBoard.getPrefWidth();
        double y = 0.5 * playBoard.getPrefHeight();

        double width = 40;
        double height = width;

        // create checkPoint data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 0,0);
        Vector2D acceleration = new Vector2D( 0,0);

        // create checkPoint
        Attractor attractor = new Attractor(playBoard, location, velocity, acceleration, width, height);
        allAttractors.add(attractor);
    }
    private void startGame() {

        // start game
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
                Attractor attractor = allAttractors.get(0);
                allMonsters.forEach(monster->{
                    monster.follow(attractor.getLocation());
                });
                allMonsters.forEach(Sprite::move);
                // update scene
                allMonsters.forEach(Sprite::display);
                allAttractors.forEach(Sprite::display);

            }
        };

        gameLoop.start();

    }

    public void initialize(){

        for(int i = 0; i < Monster.MONSTER_COUNT; i++) {
            addMonsters();
        }
        for(int i = 0; i < Attractor.ATTRACTOR_COUNT; i++) {
            addAttractor();
        }
        // run animation loop
        startGame();
    }
}