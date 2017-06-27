package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.box2d.Player;
import com.game.box2d.mapobjects.Door;
import com.game.box2d.mapobjects.Hole;
import com.game.box2d.mapobjects.Rock;

import static com.game.box2d.Player.carryingStone;

/**
 * Created by Max on 25.06.2017.
 */
public class Level_4 extends Level{

    private Rock intPuzzle;
    private Hole intHole;
    private Door door1;
    private Door door2;

    public Level_4() {
        super("level4_map.tmx");
        intPuzzle = new Rock(worldmap, "Rock1", true, "int");
        intHole = new Hole(worldmap, "Hole1", "int");
        door1 = new Door(worldmap,"Door1");
        door2 = new Door(worldmap,"Door2");
    }

    @Override
    protected ContactListener levelContact() {
        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if (fixturesNotNull()) {

                    //Kollisionsabfragen für die Türen
                    checkDoorCollisions(fixA, fixB);

                    //Restliches Zeug

                    // wenn irgendwas mit dem Spieler kollidiert
                    if(fixA.getUserData().equals("Player") || fixB.getUserData().equals("Player")) {

                        // wenn Spieler mit einem Stein kollidiert
                        if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {

                            for(int i = 0; i <= 4; i++) {
                                if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                                    switch(i) {
                                        case 1: intPuzzle.collideOn(); break;
                                    }
                                }
                            }

                        }
                        // wenn Spieler mit einem Loch kollidiert
                        else if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {

                            for(int i = 0; i <= 4; i++) {
                                if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                                    switch(i) {
                                        case 1: intHole.collideOn(); break;
                                    }
                                }
                            }

                        }

                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                if( fixturesNotNull() ) {

                    /**
                     * checkDoorCollisions muss nicht mehr in endContact abgefragt werden, da der Player sofort wegspawnt, wenn
                     * Kontakt aufgenommen wurde und das Ende der Kollision egal ist
                     checkDoorCollisions(fixA,fixB);
                     */

                    // wenn irgendwas mit dem Spieler kollidiert
                    if(fixA.getUserData().equals("Player") || fixB.getUserData().equals("Player")) {

                        // wenn Spieler mit einem Stein kollidiert
                        if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {


                            for(int i = 0; i <= 4; i++) {
                                if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                                    switch(i) {
                                        case 1: intPuzzle.collideOff(); break;
                                    }
                                }
                            }

                        }
                        // wenn Spieler mit einem Loch kollidiert
                        else if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {

                            for(int i = 0; i <= 4; i++) {
                                if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                                    switch(i) {
                                        case 1: intHole.collideOff(); break;
                                    }
                                }
                            }


                        }

                    }




                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        };
    }

    @Override
    public InputProcessor levelInput() {
        return new InputAdapter(){

            public boolean keyDown(int keycode) {

                if(keycode == Input.Keys.E) {


                    if(Player.isCarryingObject){

                        try{
                            carryingStone.putDown();
                            carryingStone = null;
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }else{

                        if(intPuzzle.collidesWithPlayer()) {
                            carryingStone = intPuzzle;
                            carryingStone.pickUp();
                        }

                    }

                    return true;
                }

                return false;
            }
        };
    }

    @Override
    public void drawObjects(Batch batch) {
        intPuzzle.draw(batch);
        door1.draw(batch);
        door2.draw(batch);
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {
    }
}
