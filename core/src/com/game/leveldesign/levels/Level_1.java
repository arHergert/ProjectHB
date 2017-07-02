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
        intHole = new Hole(worldmap, "Hole1", "int");
        stringHole = new Hole(worldmap, "Hole2", "String");
        booleanHole = new Hole(worldmap, "Hole3", "boolean");
        floatHole = new Hole(worldmap, "Hole4", "float");
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

                    System.out.println("FIXA: " +fixA.getUserData().toString() + "   FIXB: "+ fixB.getUserData().toString());

                    //Kollisionsabfragen für die Türen
                    checkDoorCollisions(fixA,fixB);

                    //Restliches Zeug

                    // wenn irgendwas mit dem Spieler kollidiert
                    if(fixtureIs("Player")) {

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
                    }


                    if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {
                    	//System.out.println("++ Rock");
                    	//Kollision mit Loch
                    	if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {
                    		//System.out.println("++ Rock+Hole");
                			for(int i = 0; i <= 4; i++) {
                				if(fixA.getUserData().toString().startsWith("Hole") && fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().startsWith("Hole") &&  fixB.getUserData().toString().endsWith(""+i)) {
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
            	
            	fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();
            	
                if( fixturesNotNull() ) {

                    /**
                     * checkDoorCollisions muss nicht mehr in endContact abgefragt werden, da der Player sofort wegspawnt, wenn
                     * Kontakt aufgenommen wurde und das Ende der Kollision egal ist
                	checkDoorCollisions(fixA,fixB);
                     */
                	
                    // wenn irgendwas mit dem Spieler kollidiert
                    if(fixtureIs("Player")) {
                    	
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
                    	
                    } else if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {
                    	//System.out.println("--Rock");
                    	if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {
                    		//System.out.println("--Rock+Hole");
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

                    	if(intHole.collidesWithRock()) {
                    		
                    		System.out.println("Ablegen in IntLoch");
                    		try {
								intHole.putRock(carryingStone);
							} catch (Exception e) {
								e.printStackTrace();
							}
                    		carryingStone = null;
                    		
                    	} else if(stringHole.collidesWithRock()) {
                    		
                    		System.out.println("Ablegen in StringLoch");
                    		try {
								stringHole.putRock(carryingStone);
							} catch (Exception e) {
								e.printStackTrace();
							}
                    		carryingStone = null;
                    		
                    	} else if(booleanHole.collidesWithRock()) {
                    		
                    		System.out.println("Ablegen in booleanLoch");
                    		try {
								booleanHole.putRock(carryingStone);
							} catch (Exception e) {
								e.printStackTrace();
							}
                    		carryingStone = null;
                    		
                    	} else if(floatHole.collidesWithRock()) {
                    		
                    		System.out.println("Ablegen in FloatLoch");
                    		try {
								floatHole.putRock(carryingStone);
							} catch (Exception e) {
								e.printStackTrace();
							}
                    		carryingStone = null;
                    		
                    	} else {
                    		carryingStone.putDown();
                    		carryingStone = null;
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

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

    }
    
    private boolean puzzleComplete() {
    	
    	if(intHole.holdsRock() && stringHole.holdsRock() && booleanHole.holdsRock() && floatHole.holdsRock()) {
    		
    		if(intHole.datatype().equals(intHole.getRock().datatype()) && stringHole.datatype().equals(stringHole.getRock().datatype()) && booleanHole.datatype().equals(booleanHole.getRock().datatype()) && floatHole.datatype().equals(floatHole.getRock().datatype())) {
    			return true;
    		}
    		
    	}
    	
    	return false;
    	
    }
}//end class Level_1
