package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        plates = new Plate[3][3];
        plates[0][0] = new Plate(worldmap, true, "Plate00");
        plates[0][1] = new Plate(worldmap, true, "Plate01");
        plates[0][2] = new Plate(worldmap, true, "Plate02");
        plates[1][0] = new Plate(worldmap, true, "Plate10");
        plates[1][1] = new Plate(worldmap, true, "Plate11");
        plates[1][2] = new Plate(worldmap, true, "Plate12");
        plates[2][0] = new Plate(worldmap, true, "Plate20");
        plates[2][1] = new Plate(worldmap, true, "Plate21");
        plates[2][2] = new Plate(worldmap, true, "Plate22");
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

                    /**
                     * Da nur Player_feet die Druckplatten auslösen dürfen, muss
                     * zuerst abgefragt werden ob bei einer Kollision
                     * mit den Druckplatten kein anderes Objekt mit denen kollidiert
                     */

                    if(fixA.getUserData().equals("Player_feet") || fixB.getUserData().equals("Player_feet")){

                        System.out.println("FIXA: "+ fixA.getUserData() + "   FIXB: "+ fixB.getUserData());

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
                        } else if (fixA.getUserData().equals("Plate02") || fixB.getUserData().equals("Plate02")){
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate02" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            if(plates[0][1].isActivated()) {
                                plates[0][2].load();
                            } else {
                                resetPlates();
                            }
                        } else if (fixA.getUserData().equals("Plate10") || fixB.getUserData().equals("Plate10")){
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate10" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            if(plates[0][2].isActivated()) {
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
                        } else if (fixA.getUserData().equals("Plate12") || fixB.getUserData().equals("Plate12")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate12" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            if(plates[1][1].isActivated()) {
                                plates[1][2].load();
                            } else {
                                resetPlates();
                            }
                        } else if (fixA.getUserData().equals("Plate20") || fixB.getUserData().equals("Plate20")){
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate20" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            if(plates[1][2].isActivated()) {
                                plates[2][0].load();
                            } else {
                                resetPlates();
                            }
                        } else if (fixA.getUserData().equals("Plate21") || fixB.getUserData().equals("Plate21")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate21" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            if(plates[2][0].isActivated()) {
                                plates[2][1].load();
                            } else {
                                resetPlates();
                            }
                        } else if (fixA.getUserData().equals("Plate22") || fixB.getUserData().equals("Plate22")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate22" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            if(plates[2][1].isActivated()) {
                                plates[2][2].load();
                            } else {
                                resetPlates();
                            }
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
                    System.out.println("Platte02: " + plates[0][2].isActivated());
                    System.out.println("Platte10: " + plates[1][0].isActivated());
                    System.out.println("Platte11: " + plates[1][1].isActivated());
                    System.out.println("Platte12: " + plates[1][2].isActivated());
                    System.out.println("Platte20: " + plates[2][0].isActivated());
                    System.out.println("Platte21: " + plates[2][1].isActivated());
                    System.out.println("Platte22: " + plates[2][2].isActivated());
                }

                return false;
            }
        };
    }

    @Override
    public void drawObjects(Batch batch) {
        for (int i = 0; i< plates.length; i++){
            for (int j = 0; j < plates[i].length; j++ ){

                plates[i][j].draw(batch);
            }
        }

    }
}//end class Level_1
