package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.mapobjects.Lever;

/**
 * Created by Artur on 04.06.2017.
 */
public class Level_0 extends Level{

    private Lever lever1 = new Lever(worldmap,"Lever1", "Boden");
    private boolean  playerOverLever = true;

    /**
     *
     */
    public Level_0() {
        super("level0_map.tmx");
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

                       if(lever1.isActivated()){
                           door.open();
                       }else {
                           door.close();
                       }
                   }

               }

               return false;
           }

        };
    }

    @Override
    public void drawObjects(Batch batch) {
        if( playerOverLever){
            lever1.draw(batch);
        }
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

        if( !playerOverLever){
            lever1.draw(batch);
        }

        door.draw(batch);
    }


}//end class Level_0
