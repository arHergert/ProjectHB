package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Artur on 04.06.2017.
 */
public class Level_0 extends Level{


    /**
     *
     */
    public Level_0() {
        super("tilemapCave2.tmx");
    }

    @Override
    protected ContactListener levelContact() {


        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if( fixA.getUserData() != null && fixB.getUserData() != null){

                    checkDoorCollisions(fixA,fixB);

                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert


            }
            public void endContact(Contact contact) {}
            public void preSolve(Contact contact, Manifold oldManifold) {}
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        };
    }

    @Override
    public InputProcessor levelInput() {

       return new InputAdapter(){

           public boolean keyDown(int keycode) {

               if (keycode == Input.Keys.E ){
                  System.out.println("E");
               }

               return false;
           }

        };
    }

    @Override
    public void drawObjects(Batch batch) {

    }
}//end class Level_0
