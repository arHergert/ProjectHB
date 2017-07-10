package com.game.leveldesign.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.game.box2d.Player;
import com.game.box2d.mapobjects.Hole;
import com.game.box2d.mapobjects.Rock;

import static com.game.Main.assetManager;
import static com.game.box2d.Player.carryingStone;
import static com.game.box2d.Player.playerBody;

/**
 * Level 7
 * --
 * �hnlich codiert wie Level 1,
 * funktioniert zu 95%, nur leider noch zu viel Codeduplikation...
 * @author Florian Osterberg
 */
public class Level_7neu extends Level {

	private Rock rock0,rock1,rock2,rock3,rock4,rock5,rock6,rock7;
	private Hole hole0,hole1,hole2,hole3,hole4,hole5,hole6,hole7;

    private boolean playerOverRocks = false;

    public Level_7neu() {
        super("level7_map.tmx");
	    rock0 = new Rock(worldmap,"Rock0",true,"List");
	    rock1 = new Rock(worldmap,"Rock1",true,"<String>");
	    rock2 = new Rock(worldmap,"Rock2",true,"list");
	    rock3 = new Rock(worldmap,"Rock3",true,"=");
	    rock4 = new Rock(worldmap,"Rock4",true,"new");
	    rock5 = new Rock(worldmap,"Rock5",true,"ArrayList");
	    rock6 = new Rock(worldmap,"Rock6",true,"<>");
	    rock7 = new Rock(worldmap,"Rock7",true,"();");
	    hole0 = new Hole(worldmap,"Hole0","List");
	    hole1 = new Hole(worldmap,"Hole1","<String>");
	    hole2 = new Hole(worldmap,"Hole2","list");
	    hole3 = new Hole(worldmap,"Hole3","=");
	    hole4 = new Hole(worldmap,"Hole4","new");
	    hole5 = new Hole(worldmap,"Hole5","ArrayList");
	    hole6 = new Hole(worldmap,"Hole6","<>");
	    hole7 = new Hole(worldmap,"Hole7","();");
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

                    // wenn irgendwas mit dem Spieler kollidiert
                    if(fixtureIs("Player") || fixtureIs("Player_feet")) {

                    	// wenn Spieler mit einem Stein kollidiert
                    	if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {

                    			for(int i = 0; i < 9; i++) {
                    				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                    					switch(i) {
                    					case 0: rock0.collideOn(); break;
                    					case 1: rock1.collideOn(); break;
                    					case 2: rock2.collideOn(); break;
                    					case 3: rock3.collideOn(); break;
                    					case 4: rock4.collideOn(); break;
                    					case 5: rock5.collideOn(); break;
                    					case 6: rock6.collideOn(); break;
                    					case 7: rock7.collideOn(); break;
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
                			for(int i = 0; i < 9; i++) {
                				if(fixA.getUserData().toString().startsWith("Hole") && fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().startsWith("Hole") &&  fixB.getUserData().toString().endsWith(""+i)) {
                					switch(i) {
                					case 0: hole0.collideOn(); break;
                					case 1: hole1.collideOn(); break;
                					case 2: hole2.collideOn(); break;
                					case 3: hole3.collideOn(); break;
                					case 4: hole4.collideOn(); break;
                					case 5: hole5.collideOn(); break;
                					case 6: hole6.collideOn(); break;
                					case 7: hole7.collideOn(); break;
                					}
                				}
                			}
                    		
                    	}
                    	
                    }

                    //Wenn der Spieler mit seinen Füßen über den Steinen geht, wird die Variable akiviert
                    if(fixtureIs("Player_feet")){

                        if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")){

                            playerOverRocks = true;

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
                    if(fixtureIs("Player")|| fixtureIs("Player_feet")) {

                    	// wenn Spieler mit einem Stein kollidiert
                    	if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {

                			for(int i = 0; i < 9; i++) {
                				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                					switch(i) {
                					case 0: rock0.collideOff(); break;
                					case 1: rock1.collideOff(); break;
                					case 2: rock2.collideOff(); break;
                					case 3: rock3.collideOff(); break;
                					case 4: rock4.collideOff(); break;
                					case 5: rock5.collideOff(); break;
                					case 6: rock6.collideOff(); break;
                					case 7: rock7.collideOff(); break;
                					}
                				}
                			}
                    		
                    	}
                    	
                    } else if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {
                    	
                    	if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {
                    		
                			for(int i = 0; i < 9; i++) {
                				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                					switch(i) {
                					case 0: hole0.collideOff(); break;
                					case 1: hole1.collideOff(); break;
                					case 2: hole2.collideOff(); break;
                					case 3: hole3.collideOff(); break;
                					case 4: hole4.collideOff(); break;
                					case 5: hole5.collideOff(); break;
                					case 6: hole6.collideOff(); break;
                					case 7: hole7.collideOff(); break;
                					}
                				}
                			}
                    		
                    	}
                    	
                    }

                    if(fixtureIs("Player_feet")){

                        if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")){

                            playerOverRocks = false;

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

            	if(keycode == Input.Keys.E|| keycode == Input.Keys.SPACE) {


                    if(Player.isCarryingObject){
                    	
                    	clearHoles(carryingStone);

                        if(playerBody.getLinearVelocity().isZero()){

                            if(hole0.collidesWithRock() && !hole0.holdsRock()) {

                                try {
                                    hole0.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(hole1.collidesWithRock() && !hole1.holdsRock()) {

                                try {
                                	hole1.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(hole2.collidesWithRock() && !hole2.holdsRock()) {

                                try {
                                	hole2.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(hole3.collidesWithRock() && !hole3.holdsRock()) {

                                try {
                                	hole3.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(hole4.collidesWithRock() && !hole4.holdsRock()) {

                                try {
                                	hole4.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(hole5.collidesWithRock() && !hole5.holdsRock()) {

                                try {
                                	hole5.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(hole6.collidesWithRock() && !hole6.holdsRock()) {

                                try {
                                	hole6.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(hole7.collidesWithRock() && !hole7.holdsRock()) {

                                try {
                                	hole7.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else {
                                carryingStone.putDown();
                                carryingStone = null;
                            }
                            puzzleComplete();


                        }else{
                            System.err.println("Bitte stehen bleiben um Stein abzulegen!");
                        }



                    	

                    }else{

                        if(rock0.collidesWithPlayer()) {
                            carryingStone = rock0;
                            carryingStone.pickUp();
                        } else if(rock1.collidesWithPlayer()) {
                            carryingStone = rock1;
                            carryingStone.pickUp();
                        } else if(rock2.collidesWithPlayer()) {
                            carryingStone = rock2;
                            carryingStone.pickUp();
                        } else if(rock3.collidesWithPlayer()) {
                            carryingStone = rock3;
                            carryingStone.pickUp();
                        } else if(rock4.collidesWithPlayer()) {
                            carryingStone = rock4;
                            carryingStone.pickUp();
                        } else if(rock5.collidesWithPlayer()) {
                            carryingStone = rock5;
                            carryingStone.pickUp();
                        }else if(rock6.collidesWithPlayer()) {
                            carryingStone = rock6;
                            carryingStone.pickUp();
                        } else if(rock7.collidesWithPlayer()) {
                            carryingStone = rock7;
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

        hole0.draw(batch);
        hole1.draw(batch);
        hole2.draw(batch);
        hole3.draw(batch);
        hole4.draw(batch);
        hole5.draw(batch);
        hole6.draw(batch);
        hole7.draw(batch);

        //Zeichnet nur wenn der Player mit seinen Füßen nicht über den Steinen clippt
        if(!playerOverRocks){
            rock0.draw(batch);
            rock1.draw(batch);
            rock2.draw(batch);
            rock3.draw(batch);
            rock4.draw(batch);
            rock5.draw(batch);
            rock6.draw(batch);
            rock7.draw(batch);
        }


    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

        //Zeichnet nur wenn der Player mit seinen Füßen über den Steinen clippt
        if(playerOverRocks){
        	rock0.draw(batch);
            rock1.draw(batch);
            rock2.draw(batch);
            rock3.draw(batch);
            rock4.draw(batch);
            rock5.draw(batch);
            rock6.draw(batch);
            rock7.draw(batch);
        }

        door.draw(batch);

    }
    
    /**
     * Testet ob das Puzzle richtig gel�st wurde.
     * Also alle Steine in den richtigen L�chern liegen.
     * @return
     */
    private boolean puzzleComplete() {
    	
    	if(hole0.holdsRock() && hole1.holdsRock() && hole2.holdsRock() && hole3.holdsRock() && hole4.holdsRock() && hole5.holdsRock() && hole6.holdsRock() && hole7.holdsRock()) {
    		
    		if(hole0.datatype().equals(hole0.getRock().datatype()) && hole1.datatype().equals(hole1.getRock().datatype()) && hole2.datatype().equals(hole2.getRock().datatype()) && hole3.datatype().equals(hole3.getRock().datatype()) && hole4.datatype().equals(hole4.getRock().datatype()) && hole5.datatype().equals(hole5.getRock().datatype()) && hole6.datatype().equals(hole6.getRock().datatype()) && hole7.datatype().equals(hole7.getRock().datatype())) {
    			System.out.println("Level 7: Puzzle geloest!");

                Timer.schedule(new Timer.Task(){


                    public void run() {
                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                    }
                }, 0.8f);

    			Gdx.app.postRunnable(() -> door.open());
    			return true;
    		}
    		
    	}
    	
    	return false;
    	
    }
    
    /**
     * entfernt einen Stein aus den L�chern
     * @param rock der Stein, der entfernt werden soll
     */
    private void clearHoles(Rock rock) {
    	
    	if(hole0.holdsRock()) {
    		
    		if(hole0.getRock().equals(rock)) {
    			hole0.removeRock();
    		}
    		
    	} if(hole1.holdsRock()) {
    		
    		if(hole1.getRock().equals(rock)) {
    			hole1.removeRock();
    		}
    		
    	} if(hole2.holdsRock()) {
    		
    		if(hole2.getRock().equals(rock)) {
    			hole2.removeRock();
    		}
    		
    	} if(hole3.holdsRock()) {
    		
    		if(hole3.getRock().equals(rock)) {
    			hole3.removeRock();
    		}
    		
    	} if(hole4.holdsRock()) {
    		
    		if(hole4.getRock().equals(rock)) {
    			hole4.removeRock();
    		}
    		
    	} if(hole5.holdsRock()) {
    		
    		if(hole5.getRock().equals(rock)) {
    			hole5.removeRock();
    		}
    		
    	} if(hole6.holdsRock()) {
    		
    		if(hole6.getRock().equals(rock)) {
    			hole6.removeRock();
    		}
    		
    	} if(hole7.holdsRock()) {
    		
    		if(hole7.getRock().equals(rock)) {
    			hole7.removeRock();
    		}
    		
    	}
    	
    }

}
