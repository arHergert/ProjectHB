package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.mapobjects.Lever;

/**
 * Created by Artur on 04.06.2017.
 */
public class ExampleLevel2 extends Level {

    //IV
    private Lever hebel;

    /**
     *
     */
    public ExampleLevel2() {
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

                    //Kollisionsabfragen für die Türen
                    checkDoorCollisions(fixA,fixB);


                    if (fixA.getUserData().equals("Hebel_1") || fixB.getUserData().equals("Hebel_1")){
                        Fixture doorBottomFixture = fixA.getUserData() == "Hebel_1" ? fixA : fixB;
                        Fixture player = doorBottomFixture == fixA ? fixB : fixA;

                        hebel.collideOn();
                    }



                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert


            }

            @Override
            public void endContact(Contact contact) {

                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if( fixA.getUserData() != null && fixB.getUserData() != null){


                    if (fixA.getUserData().equals("Hebel_1") || fixB.getUserData().equals("Hebel_1")){
                        Fixture doorBottomFixture = fixA.getUserData() == "Hebel_1" ? fixA : fixB;
                        Fixture player = doorBottomFixture == fixA ? fixB : fixA;

                        hebel.collideOff();
                    }



                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert

            }

            public void preSolve(Contact contact, Manifold oldManifold) {}
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        };
    }


    public InputProcessor levelInput(){

       return new InputAdapter(){

            public boolean keyDown(int keycode) {

                if (keycode == Input.Keys.E && hebel.collidesWithPlayer()){
                    hebel.use();

                }

                return false;
            }
        };
    }



}//end class ExampleLevel2
