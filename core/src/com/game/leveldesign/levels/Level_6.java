package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.box2d.mapobjects.Door;
import com.game.box2d.mapobjects.Lever;

/**
 * Created by Max on 08.07.2017.
 */
public class Level_6 extends Level {

    //IV
    private Lever lever1 = new Lever(worldmap,"Lever1", "Boden");
    private Lever lever2 = new Lever(worldmap,"Lever2", "Boden");
    private Door door0 = new Door(worldmap, "Door0");
    private Door door1 = new Door(worldmap, "Door1");
    private Door door2 = new Door(worldmap, "Door2");
    private Door door3 = new Door(worldmap, "Door3");
    private boolean  playerOverLever = true;

    public Level_6() {
        super("level6_map.tmx");
        updateDoors();
    }

    @Override
    protected ContactListener levelContact() {
        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if( fixturesNotNull() && (fixtureIs("Player") || fixtureIs("Player_feet")) ){

                    checkDoorCollisions(fixA,fixB);

                    // Kollision mit Hebel abfragen
                    if(fixtureIs("Lever1")) {
                        lever1.collideOn();
                    } else if(fixtureIs("Lever2")) {
                        lever2.collideOn();
                    }



                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert


                //Wenn der Spieler mit seinen Füßen über den Steinen geht, wird die Variable akiviert
                if(fixturesNotNull() && fixtureIs("Player_feet")){

                    if(fixA.getUserData().toString().startsWith("Lever") || fixB.getUserData().toString().startsWith("Lever")){

                        playerOverLever = false;

                    }
                }


            }

            public void endContact(Contact contact) {
                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if( fixturesNotNull() && fixtureIs("Player")){

                    // Kollision mit Hebel abfragen
                    if(fixtureIs("Lever1")) {
                        lever1.collideOff();

                    } else if(fixtureIs("Lever2")) {
                        lever2.collideOff();

                    }




                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert

                if(fixturesNotNull() && fixtureIs("Player_feet")){

                    if(fixA.getUserData().toString().startsWith("Lever") || fixB.getUserData().toString().startsWith("Lever")){

                        playerOverLever = true;

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

                if (keycode == Input.Keys.E || keycode == Input.Keys.SPACE ){

                    if(lever1.collidesWithPlayer()){
                        lever1.use();
                        updateDoors();
                    } else if(lever2.collidesWithPlayer()){
                        lever2.use();
                        updateDoors();
                    }

                }

                return false;
            }

        };
    }

    private void updateDoors() {
        door0.close();
        door1.close();
        door2.close();
        door3.close();
        if(!lever1.isActivated() && !lever2.isActivated()) {
            door0.open();
        } else if(lever1.isActivated() && !lever2.isActivated()) {
            door1.open();
        } else if(!lever1.isActivated() && lever2.isActivated()) {
            door2.open();
        } else if(lever1.isActivated() && lever2.isActivated()) {
            door3.open();
        }
    }

    @Override
    public void drawObjects(Batch batch) {
        if( playerOverLever){
            lever1.draw(batch);
            lever2.draw(batch);
        }
        door0.draw(batch);
        door1.draw(batch);
        door2.draw(batch);
        door3.draw(batch);
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {
        if( !playerOverLever){
            lever1.draw(batch);
            lever2.draw(batch);
        }

        door.draw(batch);
    }
}
