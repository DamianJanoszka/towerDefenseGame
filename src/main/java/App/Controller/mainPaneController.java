package App.Controller;

import App.*;
import App.ExternalLibraries.PausableAnimationTimer;
import App.GameSettings.Settings;
import App.MouseEvents.MouseActions;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class mainPaneController {

    @FXML
    private Pane playBoard;
    @FXML
    private ToggleButton pauseButton_v2;
    @FXML
    private Button playButton;
    @FXML
    private Button restartButton;


    List<Monster> allMonsters = new ArrayList<>();
    List<Attractor> allAttractors = new ArrayList<>();
    List<Cannon> allCannons = new ArrayList<>();
    List<CannonRange> allCannonsRange = new ArrayList<>();
    List<CannonMissile> allMissiles = new ArrayList<>();

    MouseActions mouseActions = new MouseActions();

    PausableAnimationTimer gameLoop = new PausableAnimationTimer() {
        @Override
        public void tick(long animationTime) {
        }
    };

    private void addMonsters() {

        // start location
        double x = 1 * playBoard.getPrefWidth() + 150;
        double y = 0.5 * playBoard.getPrefHeight();

        // enemy size
        double width = Settings.MONSTER_SIZE;
        double height = Settings.MONSTER_SIZE;

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
        double width = 45;
        double height = width;

        // create cannon data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 0,0);
        Vector2D acceleration = new Vector2D( 0,0);

        // create cannon
        Cannon cannon = new Cannon( playBoard, location, velocity, acceleration, width, height);
        CannonRange cannonRange = new CannonRange( playBoard, location, velocity, acceleration, Settings.CANNON_RANGE, Settings.CANNON_RANGE);

        // register monster
        allCannons.add(cannon);
        allCannonsRange.add(cannonRange);
    }

    private void addMissile(int i){

        // missile size
        double width = Settings.MISSILE_SIZE;
        double height = Settings.MISSILE_SIZE;

        // missile start location
        double  x= allCannons.get(i).getLocation().getX();
        double  y= allCannons.get(i).getLocation().getY();

        // create missile data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 0,0);
        Vector2D acceleration = new Vector2D( 0,0);

        // create missile
        CannonMissile missile = new CannonMissile( playBoard, location, velocity, acceleration, width, height);
        missile.setCannonID(i);

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
        playBoard.setOpacity(0.5);
        playBoard.toBack();
        // adding checkPoints, monsters and cannons
        prepareGame();

        // setting checkPoints and cannons
        prepareMap();

        // update scene
        updateScene();

        // add mouse listeners
        addListeners();

        // run animation loop
        startGame();
    }
    private void prepareGame() {
        for(int i = 0; i < Settings.MONSTER_COUNT; i++) {
            addMonsters();
        }
        for(int i = 0; i < Settings.ATTRACTOR_COUNT; i++) {
            addAttractor();
        }
        for (int i = 0; i < Settings.CANNON_COUNT; i++) {
            addCannon();
        }
        for (int i = 0; i < Settings.CANNON_COUNT; i++) {
            addMissile(i);
        }
    }
    private void prepareMap() {
        allAttractors.get(0).setLocation(0.5 * playBoard.getPrefWidth(),0.8 * playBoard.getPrefHeight());
        allAttractors.get(1).setLocation(0.5 * playBoard.getPrefWidth(),0.2 * playBoard.getPrefHeight());
        allAttractors.get(2).setLocation(0.1 * playBoard.getPrefWidth(),0.2 * playBoard.getPrefHeight());
        allCannons.get(1).setLocation(0.8 * playBoard.getPrefWidth(),0.3 * playBoard.getPrefHeight());
        allMissiles.get(1).setLocation(0.8 * playBoard.getPrefWidth(),0.3 * playBoard.getPrefHeight());
        allCannons.get(2).setLocation(0.5 * playBoard.getPrefWidth(),1.0 * playBoard.getPrefHeight());
        allMissiles.get(2).setLocation(0.5 * playBoard.getPrefWidth(),1.0 * playBoard.getPrefHeight());
    }
    private void startAttack(int i){
        if(!allMonsters.isEmpty()){
           Monster nthMonster=allMonsters.iterator().next();
            Stream<CannonMissile> missiles = allMissiles.stream();
            Stream<CannonMissile> correctMissiles = missiles.filter(missile -> missile.getCannonID()==i);
            List<CannonMissile> nthMissiles = correctMissiles.collect(Collectors.toList());

           CannonMissile nthMissile=nthMissiles.iterator().next();
           if(!(nthMonster.isInRange(nthMonster,nthMissile)) ) {
               nthMissile.attack(nthMonster.getLocation());}
           else if(nthMonster.isInRange(nthMonster,nthMissile))
           {
               nthMissile.setVisible(false);
               allMissiles.remove(nthMissile);
               addMissile(i);
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
        for (int i = 0; i < Settings.CANNON_COUNT; i++) {
            startAttack(i);
        }
        // blocking the missile from firing while moving the cannons
        blockMissile();

        // rotate the cannon
        CannonRotate();

        // move monsters and missiles
        moveObjects();

        // update scene
        updateScene();

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
                } else if (nthMonster.isInCheckPoint(nthMonster,secondBase)) {
                    nthMonster.follow(thirdBase);
                    nthMonster.setFlag(2);
                } else if (nthMonster.getFlag() == 1) {
                    nthMonster.follow(secondBase);
                } else if (nthMonster.getFlag() == 2 && !nthMonster.isInCheckPoint(nthMonster,thirdBase)) {
                    nthMonster.follow(thirdBase);
                } else if (nthMonster.getFlag() == 2 && nthMonster.isInCheckPoint(nthMonster,thirdBase)) {
                    gameLoop.stop();
                    nthMonster.setVisible(false);
                    allMonsters.clear();
                    playBoard.setOpacity(0.5);
                }
            }


    }
    public void CannonRotate(){
        if (allMonsters.iterator().hasNext()) {
            Monster nthMonster = allMonsters.get(0);
            Cannon nthCannon=null;
            for (int i = 0; i < allCannons.size(); i++) {
                nthCannon=allCannons.get(i);
                nthCannon.follow(nthMonster.getLocation());
            }
        }
    }

    public void addListeners(){

        for(int i=0; i<allCannons.size();i++) {
            mouseActions.makeDraggable(allCannonsRange.get(i));
        }

        playButton.setOnAction(event -> {
            playBoard.setOpacity(1);
            gameLoop.start();
        });

        restartButton.setOnAction(event -> {
        });

        playPauseSwitchPressed();
    }
    public void playPauseSwitchPressed() {

        pauseButton_v2.setOnAction(event -> {
            if(pauseButton_v2.isSelected()){
                gameLoop.stop();
                playBoard.setOpacity(0.5);}
            else {
                if (!gameLoop.isActive()) {
                    gameLoop.start();
                    playBoard.setOpacity(1);
                } else {
                    if (gameLoop.isPaused()) {
                        gameLoop.play();
                    } else {
                        gameLoop.pause();
                    }
                }
            }
        });
    }
    public void updateScene(){
        allMonsters.forEach(Sprite::display);
        allAttractors.forEach(Sprite::display);
        allMissiles.forEach(Sprite::display);
        allCannons.forEach(Sprite::display);
        allCannonsRange.forEach(Sprite::display);
    }
    public void blockMissile(){
        for (int i = 0; i < allCannons.size(); i++) {
            Stream<CannonMissile> missiles = allMissiles.stream();
            int id = i;
            Stream<CannonMissile> correctMissiles = missiles.filter(missile -> missile.getCannonID()== id);
            List<CannonMissile> nthMissiles = correctMissiles.collect(Collectors.toList());
            CannonMissile nthMissile=nthMissiles.iterator().next();
            if(allCannonsRange.get(i).isPressed()){
            nthMissile.setLocation(allCannons.get(i).getLocation().getX(), allCannons.get(i).getLocation().getY());
            }
        }

    }
    public void moveObjects(){
        allMonsters.forEach(Sprite::moveMonster);
        allMissiles.forEach(Sprite::moveMissile);
        allCannons.forEach(Sprite::moveCannon);
    }
}