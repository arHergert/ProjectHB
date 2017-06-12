package com.game.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.leveldesign.WorldMap;
import com.game.Main;

/**
 * Klasse zum Erstellen eines Spielcharakters. Pro Welt
 * darf maximal ein Spieler exisiteren.
 */
public class Player extends Sprite {

    /** Konstante Pixel per Meter. Nötig, da Box2D in Meter statt Pixel rechnet. Wird fürs Playermovement benötigt */
    public static float PLAYER_SPEED = 5f;
    public  static final float PPM = 16;

    /** Referenz auf die aktuelle Welt, in der sich der Spieler befindet **/
    private World world;
    public Body body;
    private Texture boxImg;

    /**
     * Erstellt einen neuen Spieler. Liest die Spawnpoints des Levels aus um
     * zu bestimmen wo der Spieler platziert wird.
     *
     * Um einen alten Spieler zu löschen muss mit dem aktuellen world Objekt die {@link World#destroyBody(Body)}
     * Methode aufgerufen werden.
     *
     * @param level Referenz auf das Level, welches die Spawnpoints angeben und die Welt enthält
     * @param level Referenz auf das Level, welches die Spawnpoints angeben und die Welt enthält
     * @param playerImgFile Textur des Spielers
     * @param spawnpoint Folgende Möglichkeiten gibt es:<br>
     *                   spawnStart -> Startet an der Start Position<br>
     *                   spawnBottomDoor -> Startet an der unteren Tür<br>
     *                   spawnUpperDoor -> Startet an der oberen Tür.<br><br>
     *
     *                   Wenn keine der drei angegeben wird, spawnt der Player
     *                   bei 0,0. WICHTIG: Die Spawnpoints müssen in der level-tmx-File
     *                   in der Ebene "PlayerSpawnpoints" abgelegt werden!
     */
    public Player (WorldMap level, String playerImgFile , String spawnpoint){
        this.world = level.getWorld();
        boxImg = new Texture(Gdx.files.internal(playerImgFile));


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
        body = world.createBody(def);

        //Körperform definieren (Rechteck) und dem Körper überreichen
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape pShape = new PolygonShape();
       // pShape.setAsBox(boxImg.getWidth()/2, boxImg.getHeight()/2);
        pShape.setAsBox(16, 32);

        fixtureDef.shape = pShape;

        //Namen für das Kollisionsobjekt festsetzen. Wird zur Kollisionsabfrage benötigt.
        body.createFixture(pShape,1.0f).setUserData("Player");

        /** Fußhitbox erstellen für Druckplatten etc. */
        PolygonShape feet = new PolygonShape();
        feet.setAsBox( (32/2) - 6f, 0.5f,new Vector2(boxImg.getWidth()/64, boxImg.getHeight() - 63.5f), 0f);
        fixtureDef.shape = feet;

        body.createFixture(feet,1.0f).setUserData("Player_feet");

        //Nicht mehr zugreifbares disposen
        pShape.dispose();
        feet.dispose();


    }


    public Texture getImg(){
        return boxImg;
    }

    public int getSpriteHeight(){
       return boxImg.getHeight();
    }

    public int getSpriteWidth(){
        return boxImg.getWidth();
    }


    /**
     * Zeichnet den Spieler mit seiner Grafikdatei an seiner Position
     * @param batch Benötigtes SpriteBatch aus der {@link Main}
     */
    public void draw(Batch batch){
        batch.draw(boxImg, body.getPosition().x -16 , body.getPosition().y - 32, 32,64);
    }

}//end class Player

