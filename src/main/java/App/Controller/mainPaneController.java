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
    int tmp1=0;
    int tmp2=0;

    private void addMonsters() {
        // start location
        double x = 1 * playBoard.getPrefWidth() + 30;
        double y = 0.5 * playBoard.getPrefHeight();
        // enemy size
        double width = 30;
        double height = width+1;

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
        double height = width+1;

        // create checkPoint data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 0,0);
        Vector2D acceleration = new Vector2D( 0,0);

        // create checkPoint
        Attractor attractor = new Attractor(playBoard, location, velocity, acceleration, width, height);
        allAttractors.add(attractor);
    }
    private void startGame() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                monsterRoute();
            }
        };

        gameLoop.start();

    }

    public void initialize(){

        // adding checkPoints and monsters
        prepareGame();
        // setting checkPoints
        prepareMap();
        // run animation loop
        startGame();
    }

    private void prepareGame() {
        for(int i = 0; i < Monster.MONSTER_COUNT; i++) {
            addMonsters();
        }
        for(int i = 0; i < Attractor.ATTRACTOR_COUNT; i++) {
            addAttractor();
        }
    }

    private void prepareMap() {
        allAttractors.get(0).setLocation(0.5 * playBoard.getPrefWidth(),0.8 * playBoard.getPrefHeight());
        allAttractors.get(1).setLocation(0.5 * playBoard.getPrefWidth(),0.1 * playBoard.getPrefHeight());
        allAttractors.get(2).setLocation(0.1 * playBoard.getPrefWidth(),0.1 * playBoard.getPrefHeight());
    }

    private void monsterRoute() {
        for (int i = 0 ; i < Monster.MONSTER_COUNT; i++) {
            monsterMove(i);
        }

        // move monsters
        allMonsters.forEach(Sprite::move);

        // update scene
        allMonsters.forEach(Sprite::display);
        allAttractors.forEach(Sprite::display);

    }
    public void monsterMove(int i){
        Vector2D firstBase=allAttractors.get(0).getLocation();
        Vector2D secondBase=allAttractors.get(1).getLocation();
        Vector2D thirdBase=allAttractors.get(2).getLocation();

        Monster nthMonster=allMonsters.get(i);

        // monster move logic
        if( (tmp1==0 && tmp2==0) &&
                !( (nthMonster.addTolerance().isGreaterThan(firstBase)) &&
                        (nthMonster.subtractTolerance().isLessThan(firstBase))) )
            {
                nthMonster.follow(firstBase);
            }
        else if( (nthMonster.addTolerance().isGreaterThan(firstBase)) &&
                (nthMonster.subtractTolerance().isLessThan(firstBase)) )
            {
                nthMonster.follow(secondBase);
                tmp1=1;
                tmp2=0;
                System.out.println("FIRST BASE");

            }
        else if( (nthMonster.addTolerance().isGreaterThan(secondBase)) &&
                (nthMonster.subtractTolerance().isLessThan(secondBase)) )
            {
                nthMonster.follow(thirdBase);
                tmp2=1;
                tmp1=0;
                System.out.println("SECOND BASE");
            }
        else if( tmp1==1){
            nthMonster.follow(secondBase);
        }
        else if( tmp2==1 && !(nthMonster.addTolerance().isGreaterThan(thirdBase) &&
                nthMonster.subtractTolerance().isLessThan(thirdBase)) )
            {
                nthMonster.follow(thirdBase);
            }
        else if( tmp2==1 && (nthMonster.addTolerance().isGreaterThan(thirdBase) &&
                nthMonster.subtractTolerance().isLessThan(thirdBase)) )
            {
                System.out.println("LAST BASE");
                gameLoop.stop();
            }
    }
}