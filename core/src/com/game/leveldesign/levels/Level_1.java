package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.Player;
import com.game.box2d.mapobjects.Hole;
import com.game.box2d.mapobjects.Rock;

/**
 * Das erste Level
 * @author Florian Osterberg
 */
public class Level_1 extends Level {

	private Rock intPuzzle, stringPuzzle, booleanPuzzle, floatPuzzle;
	private Hole intHole, stringHole, booleanHole, floatHole;
	
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

                    // wenn irgendwas mit dem Spieler kollidiert
                    if(fixA.getUserData().equals("Player") || fixB.getUserData().equals("Player")) {
                    	
                    	// wenn Spieler mit einem Stein kollidiert
                    	if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {
                    		
                    		if(Player.isCarryingObject == false) {
                    			
                    			
                    			Player.isCarryingObject = true;
                    			for(int i = 0; i < 4; i++) {
                    				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                    					switch(i) {
                    					case 1: intPuzzle.collideOn(); break;
                    					case 2: stringPuzzle.collideOn(); break;
                    					case 3: booleanPuzzle.collideOn(); break;
                    					case 4: floatPuzzle.collideOn(); break;
                    					}
                    				}
                    			}
                    			
                    			
                    		} else {
                    			// Spieler kann keine 2 Steine tragen
                    		}
                    		
                    	}
                    	// wenn Spieler mit einem Loch kollidiert
                    	else if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {
                    		
                    		if(Player.isCarryingObject == true) {
                    			// Stein ins Loch ablegen
                    			Player.isCarryingObject = false;
                    			for(int i = 0; i < 4; i++) {
                    				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                    					switch(i) {
                    					case 1: intHole.collideOn(); break;
                    					case 2: stringHole.collideOn(); break;
                    					case 3: booleanHole.collideOn(); break;
                    					case 4: floatHole.collideOn(); break;
                    					}
                    				}
                    			}
                    			
                    			
                    		} else {
                    			// nichts
                    		}
                    		
                    	}
                    	
                    }
                    


                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert
            }

            @Override
            public void endContact(Contact contact) {
            	
            	Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();
            	
                if( fixA.getUserData() != null && fixB.getUserData() != null) {
                	
                	checkDoorCollisions(fixA,fixB);
                	
                    // wenn irgendwas mit dem Spieler kollidiert
                    if(fixA.getUserData().equals("Player") || fixB.getUserData().equals("Player")) {
                    	
                    	// wenn Spieler mit einem Stein kollidiert
                    	if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {
                    		
                			
                			for(int i = 0; i < 4; i++) {
                				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                					switch(i) {
                					case 1: intPuzzle.collideOff(); break;
                					case 2: stringPuzzle.collideOff(); break;
                					case 3: booleanPuzzle.collideOff(); break;
                					case 4: floatPuzzle.collideOff(); break;
                					}
                				}
                			}
                    		
                    	}
                    	// wenn Spieler mit einem Loch kollidiert
                    	else if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {
                    		
                			for(int i = 0; i < 4; i++) {
                				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                					switch(i) {
                					case 1: intHole.collideOff(); break;
                					case 2: stringHole.collideOff(); break;
                					case 3: booleanHole.collideOff(); break;
                					case 4: floatHole.collideOff(); break;
                					}
                				}
                			}
                    			
                    		
                    	}
                    	
                    }

                	
                	
                	
                }
            	
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
            		
            		if(intPuzzle.collidesWithPlayer()) {
            			intPuzzle.pickUp();
            		} else if(stringPuzzle.collidesWithPlayer()) {
            			stringPuzzle.pickUp();
            		} else if(booleanPuzzle.collidesWithPlayer()) {
            			booleanPuzzle.pickUp();
            		} else if(floatPuzzle.collidesWithPlayer()) {
            			floatPuzzle.pickUp();
            		} else if(intHole.collidesWithPlayer()) {
            			try {
							intHole.putRock(intPuzzle);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			intPuzzle.putDown();
            		} else if(stringHole.collidesWithPlayer()) {
            			try {
							stringHole.putRock(stringPuzzle);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			stringPuzzle.putDown();
            		} else if(booleanHole.collidesWithPlayer()) {
            			try {
							booleanHole.putRock(booleanPuzzle);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			booleanPuzzle.putDown();
            		} else if(floatHole.collidesWithPlayer()) {
            			try {
							floatHole.putRock(floatPuzzle);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			floatPuzzle.putDown();
            		}
            		
            		return true;
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
