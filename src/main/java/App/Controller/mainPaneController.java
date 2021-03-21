package App.Controller;

import App.*;
import App.ExternalLibraries.PausableAnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class mainPaneController {

    @FXML
    private Pane playBoard;
    @FXML
    private ToggleButton pauseButton_v2;
    @FXML
    private Button playButton;


    List<Monster> allMonsters = new ArrayList<>();
    List<Attractor> allAttractors = new ArrayList<>();
    List<Cannon> allCannons = new ArrayList<>();
    List<Missile> allMissiles = new ArrayList<>();


    PausableAnimationTimer gameLoop = new PausableAnimationTimer() {
        @Override
        public void tick(long animationTime) {
        }
    };

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
    private void addCannon(){
        // start location
        double x = 0.2 * playBoard.getPrefWidth();
        double y = 0.4 * playBoard.getPrefHeight();
        // cannon size
        double width = 35;
        double height = width;

        // create cannon data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 0,0);
        Vector2D acceleration = new Vector2D( 0,0);

        // create cannon
        Cannon cannon = new Cannon( playBoard, location, velocity, acceleration, width, height);

        // register monster
        allCannons.add(cannon);
    }
    private void addMissile(){

        // missile size
        double width = 10;
        double height = 10;

        // missile start location
        double  x= allCannons.get(0).getLocation().getX();
        double  y= allCannons.get(0).getLocation().getY();
        // create missile data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 0,0);
        Vector2D acceleration = new Vector2D( 0,0);
        // create missile
        Missile missile = new Missile( playBoard, location, velocity, acceleration, width, height);

        // register missile
        allMissiles.add(missile);
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
        gameLoop = new PausableAnimationTimer() {
            @Override
            public void handle(long now) {
                monsterRoute();
            }

            @Override
            public void tick(long relativeNow) {

            }
        };

       // gameLoop.start();

    }

    public void initialize(){
        playBoard.setVisible(false);

        playPauseSwitchPressed();
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
        for (int i = 0; i < Cannon.CANNON_COUNT; i++) {
            addCannon();
        }
        for (int i = 0; i < Missile.MISSILE_COUNT; i++) {
            addMissile();
        }
    }
    private void prepareMap() {
        allAttractors.get(0).setLocation(0.5 * playBoard.getPrefWidth(),0.8 * playBoard.getPrefHeight());
        allAttractors.get(1).setLocation(0.5 * playBoard.getPrefWidth(),0.1 * playBoard.getPrefHeight());
        allAttractors.get(2).setLocation(0.1 * playBoard.getPrefWidth(),0.1 * playBoard.getPrefHeight());
    }
    private void startAttack(){
        if(!allMonsters.isEmpty()){
           Monster nthMonster=allMonsters.iterator().next();
           Missile nthMissile=allMissiles.iterator().next();
           if(!(nthMonster.isInRange(nthMonster,nthMissile)) ) {
               nthMissile.attack(nthMonster.getLocation());}
           else if(nthMonster.isInRange(nthMonster,nthMissile))
           {
               nthMissile.setVisible(false);
               allMissiles.remove(nthMissile);
               addMissile();
               nthMonster.healthAfterHit(nthMissile);
               if(nthMonster.getMonsterHealth()<=0){
                   allMonsters.remove(nthMonster);
                   nthMonster.setFlag(0);
                   nthMonster.setVisible(false);
                   System.out.println("Zabiles potwora");
               }
               System.out.println(nthMonster.getMonsterHealth());
           }
        }
    }
    private void monsterRoute() {
        for (int i = 0 ; i < allMonsters.size(); i++) {
            monsterMove(i);
        }
        startAttack();

        // move monsters and missiles
        allMonsters.forEach(Sprite::moveMonster);
        allMissiles.forEach(Sprite::moveMissile);

        // update scene
        allMonsters.forEach(Sprite::display);
        allAttractors.forEach(Sprite::display);
        allMissiles.forEach(Sprite::display);
        allCannons.forEach(Sprite::display);

    }
    public void monsterMove(int i) {

        Vector2D firstBase = allAttractors.get(0).getLocation();
        Vector2D secondBase = allAttractors.get(1).getLocation();
        Vector2D thirdBase = allAttractors.get(2).getLocation();

            if (allMonsters.iterator().hasNext()) {
                Monster nthMonster = allMonsters.get(i);

                // monster move logic
                if (nthMonster.getFlag() == 0 &&
                        !(nthMonster.isInCheckPoint(nthMonster,firstBase))) {
                    nthMonster.follow(firstBase);
                } else if (nthMonster.isInCheckPoint(nthMonster,firstBase)) {
                    nthMonster.follow(secondBase);
                    nthMonster.setFlag(1);
                    System.out.println("FIRST BASE");

                } else if (nthMonster.isInCheckPoint(nthMonster,secondBase)) {
                    nthMonster.follow(thirdBase);
                    nthMonster.setFlag(2);
                    System.out.println("SECOND BASE");
                } else if (nthMonster.getFlag() == 1) {
                    nthMonster.follow(secondBase);
                } else if (nthMonster.getFlag() == 2 && !nthMonster.isInCheckPoint(nthMonster,thirdBase)) {
                    nthMonster.follow(thirdBase);
                } else if (nthMonster.getFlag() == 2 && nthMonster.isInCheckPoint(nthMonster,thirdBase)) {
                    System.out.println("LAST BASE");
                    gameLoop.stop();
                    nthMonster.setVisible(false);
                }
            }


    }


    public void playPauseSwitchPressed() {
        playButton.setOnAction(event -> {
            playBoard.setVisible(true);
            gameLoop.start();
        });
        pauseButton_v2.setOnAction(event -> {
            if(pauseButton_v2.isSelected()){
                gameLoop.stop();}
            else {
                if (!gameLoop.isActive()) {
                    gameLoop.start();
                    pauseButton_v2.getStyleClass().add("pause1");
                } else {
                    if (gameLoop.isPaused()) {
                        gameLoop.play();
                        pauseButton_v2.getStyleClass().remove("play");
                        pauseButton_v2.getStyleClass().add("pause2");
                    } else {
                        gameLoop.pause();
                    }
                }
            }
        });
    }
}