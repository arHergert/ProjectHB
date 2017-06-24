package com.game.leveldesign.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.Player;
import com.game.box2d.mapobjects.Hole;
import com.game.box2d.mapobjects.Rock;

import static com.game.box2d.Player.carryingStone;

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
        intPuzzle = new Rock(worldmap, "Rock1", true, "int");
        stringPuzzle = new Rock(worldmap, "Rock2", true, "String");
        booleanPuzzle = new Rock(worldmap, "Rock3", true,"boolean");
        floatPuzzle = new Rock(worldmap, "Rock4", true, "float");
        intHole = new Hole(worldmap, "Hole1");
        stringHole = new Hole(worldmap, "Hole2");
        booleanHole = new Hole(worldmap, "Hole3");
        floatHole = new Hole(worldmap, "Hole4");
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

                    			for(int i = 0; i <= 4; i++) {
                    				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                    					switch(i) {
                    					case 1: intPuzzle.collideOn(); break;
                    					case 2: stringPuzzle.collideOn(); break;
                    					case 3: booleanPuzzle.collideOn(); break;
                    					case 4: floatPuzzle.collideOn(); break;
                    					}
                    				}
                    			}
                    		
                    	}
                    	// wenn Spieler mit einem Loch kollidiert
                    	else if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {

                    			for(int i = 0; i <= 4; i++) {
                    				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                    					switch(i) {
                    					case 1: intHole.collideOn(); break;
                    					case 2: stringHole.collideOn(); break;
                    					case 3: booleanHole.collideOn(); break;
                    					case 4: floatHole.collideOn(); break;
                    					}
                    				}
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
                    		
                			
                			for(int i = 0; i <= 4; i++) {
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
                    		
                			for(int i = 0; i <= 4; i++) {
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


                    if(Player.isCarryingObject){

                        try{
                            carryingStone.putDown();
                            carryingStone = null;
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }else{

                        if(intPuzzle.collidesWithPlayer()) {
                            carryingStone = intPuzzle;
                            carryingStone.pickUp();
                        } else if(stringPuzzle.collidesWithPlayer()) {
                            carryingStone = stringPuzzle;
                            carryingStone.pickUp();
                        } else if(booleanPuzzle.collidesWithPlayer()) {
                            carryingStone = booleanPuzzle;
                            carryingStone.pickUp();
                        } else if(floatPuzzle.collidesWithPlayer()) {
                            carryingStone = floatPuzzle;
                            carryingStone.pickUp();
                        }

                    }


                    if(stringHole.collidesWithPlayer()) {
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
