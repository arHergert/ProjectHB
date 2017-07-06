package com.game.leveldesign.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.game.box2d.Player;
import com.game.box2d.mapobjects.Hole;
import com.game.box2d.mapobjects.Rock;
import com.game.leveldesign.TryAndCatchFont;

import static com.game.Main.assetManager;
import static com.game.box2d.Player.carryingStone;
import static com.game.box2d.Player.playerBody;

/**
 * Das erste Level
 * @author Florian Osterberg
 */
public class Level_1 extends Level {

	private Rock intPuzzle, stringPuzzle, booleanPuzzle, floatPuzzle;
	private Hole intHole, stringHole, booleanHole, floatHole;
	private Rock[] rocks = { intPuzzle, stringPuzzle, booleanPuzzle, floatPuzzle };
	private Hole[] holes = { intHole, stringHole, booleanHole, floatHole };

    private TryAndCatchFont font;
    private boolean playerOverRocks = false;

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
        font = new TryAndCatchFont(12);


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

                            if(intHole.collidesWithRock() && !intHole.holdsRock()) {

                               // System.out.println("Ablegen in IntLoch");
                                try {
                                    intHole.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(stringHole.collidesWithRock() && !stringHole.holdsRock()) {

                               // System.out.println("Ablegen in StringLoch");
                                try {
                                    stringHole.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(booleanHole.collidesWithRock() && !booleanHole.holdsRock()) {

                               // System.out.println("Ablegen in booleanLoch");
                                try {
                                    booleanHole.putRock(carryingStone);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                carryingStone = null;

                            } else if(floatHole.collidesWithRock() && !floatHole.holdsRock()) {

                              //  System.out.println("Ablegen in FloatLoch");
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
                            puzzleComplete();


                        }else{
                            System.err.println("Bitte stehen bleiben um Stein abzulegen!");
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
                        clearHoles(carryingStone);

                    }
                    

                return true;
            }
            	
            	return false;
        }
    };
    }

    @Override
    public void drawObjects(Batch batch) {

        booleanHole.draw(batch);
        floatHole.draw(batch);
        intHole.draw(batch);
        stringHole.draw(batch);

        font.draw(batch, "name;", worldmap.getMapLeft() + 55, worldmap.getMapHeight() - 80);
        font.draw(batch, "alter;", worldmap.getMapLeft() + 240, worldmap.getMapHeight() - 230);
        font.draw(batch, "maennlich;", worldmap.getMapLeft() + 240, worldmap.getMapHeight() - 80);
        font.draw(batch, "groesse;", worldmap.getMapLeft() + 55, worldmap.getMapHeight() - 230);

        //Zeichnet nur wenn der Player mit seinen Füßen nicht über den Steinen clippt
        if(!playerOverRocks){
            intPuzzle.draw(batch);
            stringPuzzle.draw(batch);
            booleanPuzzle.draw(batch);
            floatPuzzle.draw(batch);

        }


    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

        //Zeichnet nur wenn der Player mit seinen Füßen über den Steinen clippt
        if(playerOverRocks){
            intPuzzle.draw(batch);
            stringPuzzle.draw(batch);
            booleanPuzzle.draw(batch);
            floatPuzzle.draw(batch);
        }

        door.draw(batch);

    }
    
    /**
     * Testet ob das Puzzle richtig gel�st wurde.
     * Also alle Steine in den richtigen L�chern liegen.
     * @return
     */
    private boolean puzzleComplete() {
    	
    	if(intHole.holdsRock() && stringHole.holdsRock() && booleanHole.holdsRock() && floatHole.holdsRock()) {
    		
    		if(intHole.datatype().equals(intHole.getRock().datatype()) && stringHole.datatype().equals(stringHole.getRock().datatype()) && booleanHole.datatype().equals(booleanHole.getRock().datatype()) && floatHole.datatype().equals(floatHole.getRock().datatype())) {
    			System.out.println("Level 1: Puzzle geloest!");

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
    	
    	if(intHole.holdsRock()) {
    		
    		if(intHole.getRock().equals(rock)) {
    			intHole.removeRock();
    		}
    		
    	} else if(stringHole.holdsRock()) {
    		
    		if(stringHole.getRock().equals(rock)) {
    			stringHole.removeRock();
    		}
    		
    	} else if(booleanHole.holdsRock()) {
    		
    		if(booleanHole.getRock().equals(rock)) {
    			booleanHole.removeRock();
    		}
    		
    	} else if(floatHole.holdsRock()) {
    		
    		if(floatHole.getRock().equals(rock)) {
    			floatHole.removeRock();
    		}
    		
    	}
    	
    }
    
}//end class Level_1
