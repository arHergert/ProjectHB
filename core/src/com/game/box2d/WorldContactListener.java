package com.game.box2d;

import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.mapobjects.Sensor;

/**
 * Created by Artur on 21.05.2017.
 */
public class WorldContactListener implements ContactListener {

    private Sensor doorUpListener;
    private Sensor doorBottomListener;

    public WorldContactListener (Sensor doorUp, Sensor doorBottom){
        this.doorUpListener = doorUp;
        this.doorBottomListener = doorBottom;
    }

    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if( fixA.getUserData() != null && fixB.getUserData() != null){

            if (fixA.getUserData().equals("doorUp") || fixB.getUserData().equals("doorUp")){
                Fixture doorUp = fixA.getUserData() == "doorUp" ? fixA : fixB;
                Fixture player = doorUp == fixA ? fixB : fixA;

                doorUpListener.activate();

            }

            if (fixA.getUserData().equals("doorBottom") || fixB.getUserData().equals("doorBottom")){
                Fixture doorBottom = fixA.getUserData() == "doorBottom" ? fixA : fixB;
                Fixture player = doorBottom == fixA ? fixB : fixA;

                doorBottomListener.activate();

            }





        }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert




    }


    public void endContact(Contact contact) {

    }


    public void preSolve(Contact contact, Manifold oldManifold) {

    }


    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}//end class WorldContactListener
