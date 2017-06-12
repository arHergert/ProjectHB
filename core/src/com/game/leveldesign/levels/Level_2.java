package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.mapobjects.Plate;

/**
 * Created by Artur on 06.06.2017.
 */
public class Level_2 extends Level {

    //IV
    private Plate[][] plates;

    /**
     * @param
     */
    public Level_2() {
        super("level2_map.tmx");
        plates = new Plate[2][2];
        plates[0][0] = new Plate(worldmap, true, "Plate00");
        plates[0][1] = new Plate(worldmap, true, "Plate01");
        plates[1][0] = new Plate(worldmap, true, "Plate10");
        plates[1][1] = new Plate(worldmap, true, "Plate11");
    }

    private void resetPlates() {
        for(int i=0; i<plates.length; i++) {
            for(int j=0; j<plates[i].length; j++) {
                plates[i][j].reset();
            }
        }
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

                    //Restliches Zeug
                    if (fixA.getUserData().equals("Plate00") || fixB.getUserData().equals("Plate00")){
                        Fixture doorBottomFixture = fixA.getUserData() == "Plate00" ? fixA : fixB;
                        Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                        plates[0][0].load();
                    } else if (fixA.getUserData().equals("Plate01") || fixB.getUserData().equals("Plate01")){
                        Fixture doorBottomFixture = fixA.getUserData() == "Plate01" ? fixA : fixB;
                        Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                        if(plates[0][0].isActivated()) {
                            plates[0][1].load();
                        } else {
                            resetPlates();
                        }
                    } else if (fixA.getUserData().equals("Plate10") || fixB.getUserData().equals("Plate10")){
                        Fixture doorBottomFixture = fixA.getUserData() == "Plate10" ? fixA : fixB;
                        Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                        if(plates[0][1].isActivated()) {
                            plates[1][0].load();
                        } else {
                            resetPlates();
                        }
                    } else if (fixA.getUserData().equals("Plate11") || fixB.getUserData().equals("Plate11")) {
                        Fixture doorBottomFixture = fixA.getUserData() == "Plate11" ? fixA : fixB;
                        Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                        if(plates[1][0].isActivated()) {
                            plates[1][1].load();
                        } else {
                            resetPlates();
                        }
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

    @Override
    public InputProcessor levelInput() {
        return new InputAdapter(){

            public boolean keyDown(int keycode) {

                if (keycode == Input.Keys.E ){
                    System.out.println("");
                    System.out.println("Platte00: " + plates[0][0].isActivated());
                    System.out.println("Platte01: " + plates[0][1].isActivated());
                    System.out.println("Platte10: " + plates[1][0].isActivated());
                    System.out.println("Platte11: " + plates[1][1].isActivated());
                }

                return false;
            }
        };
    }
}//end class Level_1
