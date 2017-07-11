package com.game.leveldesign.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.box2d.mapobjects.Sensor;
import com.game.leveldesign.TryAndCatchFont;

import static com.game.Main.assetManager;
import static com.game.box2d.Player.PLAYER_SPEED;

/**
 * Created by Artur on 04.06.2017.
 */
public class Level_Boss extends Level{



    /**
     *
     */
    public Level_Boss() {
        super("level_boss_map.tmx");


    }

    @Override
    protected ContactListener levelContact() {


        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                checkDoorCollisions(fixA,fixB);

                if (fixturesNotNull()) {



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


    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

    }


}//end class Level_0
