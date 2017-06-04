package com.game.leveldesign.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.mapobjects.Lever;

/**
 * Created by Artur on 04.06.2017.
 */
public class Level_2 extends Level {

    //IV
    private Lever hebel;

    /**
     *
     */
    public Level_2() {
        super("tilemapCave.tmx");
        hebel = new Lever(worldmap, "Hebel_1");
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


                    if (fixA.getUserData().equals("Hebel_1") || fixB.getUserData().equals("Hebel_1")){
                        Fixture doorBottomFixture = fixA.getUserData() == "Hebel_1" ? fixA : fixB;
                        Fixture player = doorBottomFixture == fixA ? fixB : fixA;

                        hebel.use();
                    }



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
}
