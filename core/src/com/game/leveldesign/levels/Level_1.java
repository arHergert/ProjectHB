package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.mapobjects.Rock;

/**
 * Created by Artur on 06.06.2017.
 */
public class Level_1 extends Level {

	private Rock intPuzzle, stringPuzzle, booleanPuzzle, floatPuzzle;
	
    /**
     * @param
     */
    public Level_1() {
        super("level1_map.tmx");
        intPuzzle = new Rock(worldmap, "PuzzleRock001", true);
        stringPuzzle = new Rock(worldmap, "PuzzleRock002", true);
        booleanPuzzle = new Rock(worldmap, "PuzzleRock003", true);
        floatPuzzle = new Rock(worldmap, "PuzzleRock004", true);
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

            	if(keycode == Input.Keys.E) {
            		
            		
            	}

                return false;
            }
        };
    }

    @Override
    public void drawObjects(Batch batch) {
    	intPuzzle.draw(batch);
    	stringPuzzle.draw(batch);
    	booleanPuzzle.draw(batch);
    	floatPuzzle.draw(batch);
    }
}//end class Level_1
