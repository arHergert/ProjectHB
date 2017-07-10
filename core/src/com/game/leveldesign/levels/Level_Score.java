package com.game.leveldesign.levels;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Timer;
import com.game.box2d.mapobjects.Sensor;

import static com.game.Main.assetManager;
import static com.game.Main.scoremanager;

/**
 * Created by Artur on 04.06.2017.
 */
public class Level_Score extends Level{

    private Sensor scoreSensor = new Sensor(worldmap,"ScoreSensor");
    private boolean endWasStarted = false;
    private boolean line1 = false,line2 = false, line3 = false;


    /**
     *
     */
    public Level_Score() {
        super("level_score_map.tmx");

        gameCompleted = true;


    }

    @Override
    protected ContactListener levelContact() {


        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                if (fixturesNotNull()) {

                     checkDoorCollisions(fixA,fixB);

                    // wenn irgendwas mit dem Spieler kollidiert
                    if (fixtureIs("Player") || fixtureIs("Player_feet")) {

                        if(!endWasStarted){
                            endWasStarted = true;

                            Timer.schedule(new Timer.Task(){

                                public void run() {
                                    door.open();
                                }
                            }, 7f);

                            Timer.schedule(new Timer.Task(){

                                public void run() {
                                    line1 = true;
                                    assetManager.get("sounds/hole_true.wav", Sound.class).play();
                                }
                            }, 1f);

                            Timer.schedule(new Timer.Task(){

                                public void run() {
                                    line2 = true;
                                    assetManager.get("sounds/hole_true.wav", Sound.class).play();
                                }
                            }, 3f);

                            Timer.schedule(new Timer.Task(){

                                public void run() {
                                    line3 = true;
                                    assetManager.get("sounds/hole_true.wav", Sound.class).play();
                                }
                            }, 5f);

                        }

                    }
                }


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

                return false;
            }

        };
    }

    @Override
    public void drawObjects(Batch batch) {

        if(line1){
            scoremanager.drawFirstLine( batch, worldmap);
        }

        if(line2){
            scoremanager.drawSecondLine( batch, worldmap);
        }

        if(line3){
            scoremanager.drawThirdLine( batch, worldmap);
        }


        door.drawBig(batch);
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

    }


}//end class Level_0
