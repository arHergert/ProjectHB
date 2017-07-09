package com.game.leveldesign.levels;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.leveldesign.Score;

import static com.game.Main.scoremanager;

/**
 * Created by Artur on 04.06.2017.
 */
public class Level_Score extends Level{

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
            public void beginContact(Contact contact) {}
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

        try {
            scoremanager.printScoreScreen( batch, worldmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        door.drawBig(batch);
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

    }


}//end class Level_0
