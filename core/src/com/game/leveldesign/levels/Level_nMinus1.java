package com.game.leveldesign.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.game.box2d.mapobjects.Collectibles;
import com.game.box2d.mapobjects.Plate;

import static com.game.Main.assetManager;

/**
 * Created by Max on 08.07.2017.
 */
public class Level_nMinus1 extends Level {

    //IV
    private Plate[] plates;
    private Collectibles coll1, coll2, coll3;


    public Level_nMinus1() {
        super("levelN-1_map.tmx");
        plates = new Plate[5];
        plates[0] = new Plate(worldmap, false, "Plate1");
        plates[1] = new Plate(worldmap, false, "Plate2");
        plates[2] = new Plate(worldmap, false, "Plate3");
        plates[3] = new Plate(worldmap, false, "Plate4");
        plates[4] = new Plate(worldmap, false, "Plate5");

        coll1 = new Collectibles(worldmap,"Coll1");
        coll2 = new Collectibles(worldmap,"Coll2");
        coll3 = new Collectibles(worldmap,"Coll3");

    }

    @Override
    protected ContactListener levelContact() {
        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if( fixturesNotNull()){

                    //Kollisionsabfragen für die Türen
                    checkDoorCollisions(fixA,fixB);

                    //Restliches Zeug

                    /**
                     * Da nur Player_feet die Druckplatten auslösen dürfen, muss
                     * zuerst abgefragt werden ob bei einer Kollision
                     * mit den Druckplatten kein anderes Objekt mit denen kollidiert
                     */


                    if(fixtureIs("Player") || fixtureIs("Player_feet")) {


                        if (fixtureStartsWith("Coll")) {

                            if (fixtureIs("Coll1")) {

                                Gdx.app.postRunnable(() -> coll1.collect());

                            } else if (fixtureIs("Coll2")) {
                                Gdx.app.postRunnable(() -> coll2.collect());

                            } else if (fixtureIs("Coll3")) {
                                Gdx.app.postRunnable(() -> coll3.collect());
                            }

                            increaseGarneredCollectiblesCount();
                        }
                    }



                    if(fixA.getUserData().equals("Player_feet") || fixB.getUserData().equals("Player_feet")){
                        if (fixtureIs("Plate1")) {
                            plates[0].reset();
                            if(!door.isOpen()){
                                Timer.schedule(new Timer.Task(){

                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);
                            }
                            Gdx.app.postRunnable(() -> door.open());
                        } else if (fixtureIs("Plate2")) {
                            plates[1].reset();
                            if(!door.isOpen()){
                                Timer.schedule(new Timer.Task(){

                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);
                            }
                            Gdx.app.postRunnable(() -> door.open());
                        } else if (fixtureIs("Plate3")) {
                            plates[2].reset();
                            if(!door.isOpen()){
                                Timer.schedule(new Timer.Task(){

                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);
                            }
                            Gdx.app.postRunnable(() -> door.open());
                        } else if (fixtureIs("Plate4")) {
                            plates[3].reset();
                            if(!door.isOpen()){
                                Timer.schedule(new Timer.Task(){

                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);
                            }
                            Gdx.app.postRunnable(() -> door.open());
                        } else if (fixtureIs("Plate5")) {
                            plates[4].reset();
                            if(!door.isOpen()){
                                Timer.schedule(new Timer.Task(){

                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);
                            }
                            Gdx.app.postRunnable(() -> door.open());
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

                if (keycode == Input.Keys.E || keycode == Input.Keys.SPACE ){
                    //
                }

                return false;
            }
        };
    }

    @Override
    public void drawObjects(Batch batch) {
        for (int j = 0; j < plates.length; j++ ){
            plates[j].draw(batch);
        }

        coll1.draw(batch);
        coll2.draw(batch);
        coll3.draw(batch);

    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {
        door.draw(batch);
    }
}
