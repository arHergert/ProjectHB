package com.game.leveldesign.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Timer;
import com.game.box2d.Player;
import com.game.box2d.mapobjects.*;
import com.game.leveldesign.TryAndCatchFont;

import static com.game.Main.assetManager;
import static com.game.box2d.Player.carryingStone;
import static com.game.box2d.Player.playerBody;

/**
 * Created by Artur on 04.06.2017.
 */
public class Level_Boss extends Level{

    private boolean playerOverRocks = false;
    private boolean golemActivated = false;
    private boolean doorWasOpened = false;
    private int positionX, positionY;
    private TryAndCatchFont font;
    private TryAndCatchFont smallerFont;
    private TryAndCatchFont biggerFont;
    private TryAndCatchFont consolefont;
    private TryAndCatchFont redfont;
    private Door doorFinal;
    private Door door2;
    private Hole hole1;
    private Hole hole2;
    private Hole hole3;
    private Lever lever;
    private Rock rock;
    private Button button;
    private Sensor golemSensor;


    private TextureAtlas golemAtlas;
    private enum GolemState{CLOSED, OPENING}
    private GolemState currentState;
    private GolemState previousState;
    private float stateTimer;

    private Animation<TextureRegion> golemOpening;
    private TextureRegion golemClosed;
    private TextureRegion currentFrame;

    /** Puzzle Varibalen*/
    String stone = "1337";

    String visibility = "private";

    /**
     *
     */
    public Level_Boss() {
        super("level_boss_map.tmx");

        positionX = worldmap.getMapLeft();
        positionY = worldmap.getMapTop();
        font = new TryAndCatchFont(10);
        consolefont = new TryAndCatchFont(17, "008080");
        smallerFont = new TryAndCatchFont(7);
        biggerFont = new TryAndCatchFont(15);
        redfont = new TryAndCatchFont(10, "c80d4a");

        doorFinal = new Door(worldmap,"DoorFinal");
        door2 = new Door(worldmap,"Door2");

        hole1 = new Hole(worldmap,"Hole1", "String");
        hole2= new Hole(worldmap,"Hole2","String");
        hole3= new Hole(worldmap,"Hole3","String");
        lever = new Lever(worldmap,"Lever", "Oben");
        rock = new Rock(worldmap,"Rock",true,"normal");
        button = new Button(worldmap, "Button");
        golemSensor = new Sensor(worldmap,"GolemSensor");

        golemAtlas = new TextureAtlas("spritesheet/golemanim.atlas");

        stateTimer = 0f;

        golemOpening = new Animation<TextureRegion>(0.239f, golemAtlas.findRegions("golem"));
        golemClosed = new TextureRegion(golemAtlas.findRegions("golem").get(0));

    }

    @Override
    protected ContactListener levelContact() {


        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                if (fixturesNotNull()) {

                    checkDoorCollisions(fixA,fixB);
                    // wenn irgendwas mit dem Spieler kollidiert
                    if(fixtureIs("Player") || fixtureIs("Player_feet")) {

                        // wenn Spieler mit einem Stein kollidiert
                        if(fixtureIs("Rock")) {

                           rock.collideOn();
                        }

                        if(fixtureIs("Lever")) {
                            lever.collideOn();
                        }

                        if (fixtureIs("Button")) {
                            button.collideOn();

                        }

                        // wenn irgendwas mit dem Spieler kollidiert
                        if (fixtureIs("GolemSensor") ) {

                            if(!golemActivated){
                                golemActivated = true;

                                doorWasOpened = true;
                                assetManager.get("sounds/golem.wav", Sound.class).play();

                                Timer.schedule(new Timer.Task(){


                                    public void run() {
                                        door.openWithoutSound();
                                    }
                                }, 5f);
                            }



                        }

                    }

                    if(fixtureIs("Rock")) {
                        //System.out.println("++ Rock");
                        //Kollision mit Loch
                        if(fixtureStartsWith("Hole")) {
                            //System.out.println("++ Rock+Hole");
                            for(int i = 0; i <= 3; i++) {
                                if(fixA.getUserData().toString().startsWith("Hole") && fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().startsWith("Hole") &&  fixB.getUserData().toString().endsWith(""+i)) {
                                    switch(i) {
                                        case 1: hole1.collideOn(); break;
                                        case 2: hole2.collideOn(); break;
                                        case 3: hole3.collideOn(); break;
                                    }
                                }
                            }

                        }

                    }

                    //Wenn der Spieler mit seinen Füßen über den Steinen geht, wird die Variable akiviert
                    if(fixtureIs("Player_feet")){

                        if(fixtureIs("Rock")){

                            playerOverRocks = true;

                        }
                    }




                }


            }

            public void endContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                if (fixturesNotNull()) {


                    // wenn irgendwas mit dem Spieler kollidiert
                    if(fixtureIs("Player") || fixtureIs("Player_feet")) {

                        // wenn Spieler mit einem Stein kollidiert
                        if(fixtureIs("Rock")) {

                            rock.collideOff();
                        }

                        if(fixtureIs("Lever")) {
                            lever.collideOff();
                        }

                        if (fixtureIs("Button")) {
                            button.collideOff();

                        }


                    }

                    if(fixtureIs("Rock")) {
                        //System.out.println("++ Rock");
                        //Kollision mit Loch
                        if(fixtureStartsWith("Hole")) {
                            //System.out.println("++ Rock+Hole");
                            for(int i = 0; i <= 3; i++) {
                                if(fixA.getUserData().toString().startsWith("Hole") && fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().startsWith("Hole") &&  fixB.getUserData().toString().endsWith(""+i)) {
                                    switch(i) {
                                        case 1: hole1.collideOff(); break;
                                        case 2: hole2.collideOff(); break;
                                        case 3: hole3.collideOff(); break;
                                    }
                                }
                            }

                        }

                    }

                    //Wenn der Spieler mit seinen Füßen über den Steinen geht, wird die Variable akiviert
                    if(fixtureIs("Player_feet")){

                        if(fixtureIs("Rock")){

                            playerOverRocks = false;

                        }
                    }




                }


            }
            public void preSolve(Contact contact, Manifold oldManifold) {}
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        };
    }


    @Override
    public InputProcessor levelInput() {

        return new InputAdapter(){

            public boolean keyDown(int keycode) {

                if(keycode == Input.Keys.E|| keycode == Input.Keys.SPACE) {

                    if(lever.collidesWithPlayer()){
                        assetManager.get("sounds/plate_true.wav", Sound.class).play();
                        lever.use();

                        visibility = visibility.equals("private") ? "public" : "private";

                    }

                    if(button.collidesWithPlayer()){
                        button.use();

                        if(visibility.equals("private")){
                            assetManager.get("sounds/error.wav", Sound.class).play();
                            door2.close();

                        }else {
                            door2.open();
                        }
                    }


                    if(Player.isCarryingObject){

                        if(playerBody.getLinearVelocity().isZero()){

                            if(hole1.collidesWithRock()) {

                                //Das Hole mit dem man die Finale tür öffnet

                                if(stone.equals("open")){
                                    hole1.setCurrentTexture("true");
                                    assetManager.get("sounds/hole_true.wav", Sound.class).play();
                                    doorFinal.open();

                                    Timer.schedule(new Timer.Task(){


                                        public void run() {
                                            assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                        }
                                    }, 1f);
                                }else {
                                    hole1.setCurrentTexture("false");
                                    assetManager.get("sounds/hole_false.wav", Sound.class).play();
                                }

                                carryingStone.putDown();
                                carryingStone = null;

                            } else if(hole2.collidesWithRock()) {
                                assetManager.get("sounds/plate_unload.wav", Sound.class).play();
                                stone = "close";
                                carryingStone.putDown();
                                carryingStone = null;

                            } else if(hole3.collidesWithRock()) {

                                if(stone.equals("close")){
                                    assetManager.get("sounds/plate_unload.wav", Sound.class).play();
                                    stone = "open";
                                }else {
                                    hole3.setCurrentTexture("false");
                                    assetManager.get("sounds/hole_false.wav", Sound.class).play();
                                }

                                carryingStone.putDown();
                                carryingStone = null;

                            } else {
                                carryingStone.putDown();
                                carryingStone = null;
                            }


                        }else{
                            System.err.println("Bitte stehen bleiben um Stein abzulegen!");
                        }





                    }else{

                        if(rock.collidesWithPlayer()) {
                            hole1.setCurrentTexture("neutral");
                            hole2.setCurrentTexture("neutral");
                            hole3.setCurrentTexture("neutral");
                            carryingStone = rock;
                            carryingStone.pickUp();
                        }

                    }


                    return true;
                }


                if(keycode == Input.Keys.Z){

                    doorFinal.open();
                }

                return false;
            }

        };
    }

    @Override
    public void drawObjects(Batch batch) {

        doorFinal.draw(batch);
        hole1.draw(batch);
        hole2.draw(batch);
        hole3.draw(batch);
        lever.draw(batch);
        button.draw(batch);


        font.draw(batch,"public void openDoor (String stone){\n" +
                        "   if (stone.equals(\"open\"){\n" +
                        "        door.open();\n" +
                        "   }\n" +
                        "}", positionX+175,positionY-70);


        font.draw(batch,"System.out.println(stone);", positionX+485, positionY-64);

        smallerFont.draw(batch,"Console:", positionX+500, positionY-75);
        consolefont.draw(batch, stone, positionX+525,positionY-94);


        biggerFont.draw(batch,"level.openDoor(", positionX+230, positionY-145);
        biggerFont.draw(batch,");", positionX+386, positionY-145);

        font.draw(batch,"stone_close = \"close\";\n\n\n" +
                        "stone_open  = \"open\";\n\n\n" +
                        "            = stone_close;", positionX+110, positionY-193);

        redfont.draw(batch, visibility, positionX+328,positionY-298);
        font.draw(batch, "void use(){", positionX+375,positionY-298);

        font.draw(batch,"   door.open();\n" +
                        "}", positionX+328,positionY-315);
        font.draw(batch,"if(stone.equals(\"close\"){ ", positionX+331,positionY-361);
        font.draw(batch," = stone_open;", positionX+388,positionY-380);
        font.draw(batch," }", positionX+328,positionY-390);

        redfont.draw(batch,visibility, positionX+711,positionY-263);
        font.draw(batch,"void use {...}", positionX+758,positionY-263);

        font.draw(batch,"public", positionX+765, positionY-280);
        font.draw(batch,"private", positionX+765, positionY-310);

        font.draw(batch,"button.use();", positionX+485,positionY-241);




        currentFrame = getFrame();

        batch.draw(currentFrame,door.getX()-100,door.getY());



        if(!playerOverRocks){
            rock.draw(batch);
            font.draw(batch,"stone", rock.getX()+2, rock.getY()+15);
        }


    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

        door2.draw(batch);


        if(playerOverRocks){
            rock.draw(batch);
            font.draw(batch,"stone", rock.getX()+2, rock.getY()+15);
        }
    }




    private TextureRegion getFrame(){
        currentState = getState();

        //System.out.println(getState());
        TextureRegion region;
        switch (currentState){

            case OPENING: {
                region = golemOpening.getKeyFrame(stateTimer);
            }break;

            case CLOSED:
            default:{
                region = golemClosed;
            }break;
        }//end switch case region

        stateTimer = currentState == previousState ? stateTimer + Gdx.graphics.getDeltaTime() : 0;
        previousState = currentState;
        return region;
    }

    private GolemState getState(){
        if(!doorWasOpened){
            return GolemState.CLOSED;
        }else {
            return  GolemState.OPENING;
        }

    }




}//end class Level_0
