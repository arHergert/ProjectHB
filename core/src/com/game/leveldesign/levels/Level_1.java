package com.game.leveldesign.levels;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Artur on 04.06.2017.
 */
public class Level_1 extends Level{


    /**
     *
     */
    public Level_1() {
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

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        };
    }
}//end class Level_1
