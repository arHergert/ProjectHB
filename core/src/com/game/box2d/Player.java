package com.game.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.Main;
import com.game.box2d.mapobjects.Rock;
import com.game.leveldesign.WorldMap;

import static com.game.Main.assetManager;
import static com.game.Main.spritesheet;

/**
 * Klasse zum Erstellen eines Spielcharakters. Pro Welt
 * darf maximal ein Spieler exisiteren.
 */
public class Player extends Sprite {

    /** Konstante Pixel per Meter. Nötig, da Box2D in Meter statt Pixel rechnet. Wird fürs Playermovement benötigt */
    public static float PLAYER_SPEED = 5.3f;
    public static  float PPM = 16;

    /** Inventarslot für den Spieler. Platz für 1 Stein */
    public static Rock carryingStone;

    /** Abfrage ob der Spieler aktuell etwas in seinem Inventarslot besitzt*/
    public static boolean isCarryingObject = false;

    /** Referenz auf die aktuelle Welt, in der sich der Spieler befindet **/
    private World world;
    public static Body playerBody;


    private boolean walkSoundStarted = false;


    /** Zeigt an in welche Richtung der Spieler zuletzt bzw. gerade geht. Standardweise schaut er immer nach rechts */
    public static int lastMovedDirection = Input.Keys.D;

    /** Status die der Player einnehmen kann*/
    private enum PlayerState{STANDING_LEFT,STANDING_RIGHT,STANDING_UP, WALKING_LEFT, WALKING_RIGHT, WALKING_UP, WALKING_DOWN}

    /** Texturen und Animationen des Spielers */
    private TextureRegion playerStand_right;
    private TextureRegion playerStand_left;
    private TextureRegion playerStand_up;
    private Animation<TextureRegion> playerWalk_right;
    private Animation<TextureRegion> playerWalk_left;
    private Animation<TextureRegion> playerWalk_up;

    /** Aktueller und vorheriger Status des Spielers */
    private  PlayerState currentState;
    private PlayerState previousState;
    private float stateTimer;

    /** Speichert den aktuellsten Frame, welches gezeichnet wird*/
    private TextureRegion currentFrame;

    /**
     * Erstellt einen neuen Spieler. Liest die Spawnpoints des Levels aus um
     * zu bestimmen wo der Spieler platziert wird.
     *
     * Um einen alten Spieler zu löschen muss mit dem aktuellen world Objekt die {@link World#destroyBody(Body)}
     * Methode aufgerufen werden.
     *
     * @param level Referenz auf das Level, welches die Spawnpoints angeben und die Welt enthält
     * @param level Referenz auf das Level, welches die Spawnpoints angeben und die Welt enthält
     * @param spawnpoint Folgende Möglichkeiten gibt es:<br>
     *                   spawnStart -> Startet an der Start Position<br>
     *                   spawnBottomDoor -> Startet an der unteren Tür<br>
     *                   spawnUpperDoor -> Startet an der oberen Tür.<br><br>
     *
     *                   Wenn keine der drei angegeben wird, spawnt der Player
     *                   bei 0,0. WICHTIG: Die Spawnpoints müssen in der level-tmx-File
     *                   in der Ebene "PlayerSpawnpoints" abgelegt werden!
     */
    public Player (WorldMap level, String spawnpoint){
        this.world = level.getWorld();

        /** Textur bzew Animationen definieren */
        playerStand_right = spritesheet.findRegions("playerwalk").get(0);
        playerStand_left = spritesheet.findRegions("leftwalk").get(0);
        playerStand_up = spritesheet.findRegions("upwalk").get(0);


        playerWalk_right = new Animation<>(0.090f, spritesheet.findRegions("playerwalk"));
        playerWalk_left = new Animation<>(0.090f, spritesheet.findRegions("leftwalk"));
        playerWalk_up = new Animation<>(0.060f, spritesheet.findRegions("upwalk"));


        float startX = 0;
        float startY = 0;

        //Überprüft den eingebenen String nach dem Wunsch-Spawnpoint. Wenn keiner gefunden wurde, spawnt der Spieler bei 0,0
        if (spawnpoint.equals("spawnStart")){
            startX = level.getSpawnStartX();
            startY = level.getSpawnStartY();

        } else if( spawnpoint.equals("spawnBottomDoor")){
            startX = level.getSpawnBottomDoorX();
            startY = level.getSpawnBottomDoorY();

        } else if (spawnpoint.equals("spawnUpperDoor")){
            startX = level.getSpawnUpperDoorX();
            startY = level.getSpawnUpperDoorY();
        }

        //Kollisionsbox des Players definieren
        definePlayer(startX, startY);

    }

    /**
     * Definiert die Position und die Kollisionsbox
     * des Spielers
     *
     * @param startX Startposition X
     * @param startY Startposition Y
     */
    private void definePlayer(float startX, float startY){

        //Eigenschaften für den Körper festsetzen -> Statisch, Spawnpoints, dreht sich nicht
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(startX , startY );
        def.fixedRotation = true;

        //Körper in der Welt erstellen mit den zuvor definierten Eigenschaften
        playerBody = world.createBody(def);

        //Körperform definieren (Rechteck) und dem Körper überreichen
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape pShape = new PolygonShape();
       // pShape.setAsBox(boxImg.getWidth()/2, boxImg.getHeight()/2);
        pShape.setAsBox(11, 9);

        fixtureDef.shape = pShape;
        //Namen für das Kollisionsobjekt festsetzen. Wird zur Kollisionsabfrage benötigt.
        playerBody.createFixture(pShape,1.0f).setUserData("Player");

        /** Fußhitbox erstellen für Druckplatten etc. */
        PolygonShape feet = new PolygonShape();
        feet.setAsBox( (32/2) - 6f, 0.5f,new Vector2(16/64, 32 - 63.5f), 0f);
        fixtureDef.shape = feet;
        fixtureDef.isSensor = true;
        Fixture fixture = playerBody.createFixture(fixtureDef);
        fixture.setUserData("Player_feet");

        //playerBody.createFixture(feet,1.0f).setUserData("Player_feet");

        //Nicht mehr zugreifbares disposen
        pShape.dispose();
        feet.dispose();


    }


    public TextureRegion getImg(){
        return playerStand_right;
    }

    public int getSpriteHeight(){
       return playerStand_right.getRegionHeight();
    }

    public int getSpriteWidth(){
        return playerStand_right.getRegionWidth();
    }

    /**
     * Zeichnet den Spieler mit seiner Grafikdatei an seiner Position
     * @param batch Benötigtes SpriteBatch aus der {@link Main}
     */
    public void draw(Batch batch){

        currentFrame = getFrame();

        if(!playerBody.getLinearVelocity().isZero()){

            if(!walkSoundStarted){
                assetManager.get("sounds/walk.wav", Sound.class).loop();
                walkSoundStarted = true;
            }

        }else{
            assetManager.get("sounds/walk.wav", Sound.class).stop();
            walkSoundStarted=false;
        }

        batch.draw(currentFrame, playerBody.getPosition().x -16 , playerBody.getPosition().y - 32, 32,64);

    }

    private TextureRegion getFrame() {
        currentState = getState();

        TextureRegion region;
        switch (currentState){

            case WALKING_LEFT:{

                region = playerWalk_left.getKeyFrame(stateTimer, true);

            }break;

            case WALKING_RIGHT:{

                region = playerWalk_right.getKeyFrame(stateTimer, true);

            }break;

            case WALKING_UP:{

                region = playerWalk_up.getKeyFrame(stateTimer, true);

            }break;

            case WALKING_DOWN:{

                region = playerWalk_right.getKeyFrame(stateTimer, true);

            }break;

            case STANDING_LEFT:{
                region = playerStand_left;

            }break;

            case STANDING_UP:{
                region = playerStand_up;

            }break;

            case STANDING_RIGHT:
            default:{
                region = playerStand_right;
            }break;

        }//end switch case currentState

        stateTimer = currentState == previousState ? stateTimer + Gdx.graphics.getDeltaTime() : 0;
        previousState = currentState;
        return region;
    }

    private PlayerState getState() {


        if(playerBody.getLinearVelocity().x > 0){
            return PlayerState.WALKING_RIGHT;

        }else if(playerBody.getLinearVelocity().x < 0){
            return PlayerState.WALKING_LEFT;

        }else if (playerBody.getLinearVelocity().y > 0) {
            return PlayerState.WALKING_UP;

        }else if (playerBody.getLinearVelocity().y < 0) {
        return PlayerState.WALKING_DOWN;

        }else if(playerBody.getLinearVelocity().isZero() && (lastMovedDirection == Input.Keys.A || lastMovedDirection == Input.Keys.LEFT) ){
            return  PlayerState.STANDING_LEFT;

        }else if(playerBody.getLinearVelocity().isZero() && (lastMovedDirection == Input.Keys.W|| lastMovedDirection == Input.Keys.UP) ) {
            return PlayerState.STANDING_UP;

        }else {
            return  PlayerState.STANDING_RIGHT;

        }

    }


}//end class Player

