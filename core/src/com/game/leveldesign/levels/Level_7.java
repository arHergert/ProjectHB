package com.game.leveldesign.levels;

import static com.game.Main.assetManager;
import static com.game.box2d.Player.carryingStone;
import static com.game.box2d.Player.playerBody;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Timer;
import com.game.box2d.Player;
import com.game.box2d.mapobjects.Hole;
import com.game.box2d.mapobjects.Rock;

/**
 * 
 * Level 7
 * Funktioniert in dieser Version noch nicht ganz,
 * wollte durch Arrays eine Menge an Codeduplikation vermeiden
 * 
 * @author Florian Osterberg
 *
 */
public class Level_7 extends Level {

	private Rock[] rocks;
	private Hole[] holes;
	
	private boolean playerOverRocks = false;
	
	public Level_7() {
		super("level7_map.tmx");
		rocks = new Rock[8];
		rocks[0] = new Rock(worldmap, "Rock0", true, "List");
		rocks[1] = new Rock(worldmap, "Rock1", true, "<String>");
		rocks[2] = new Rock(worldmap, "Rock2", true, "list");
		rocks[3] = new Rock(worldmap, "Rock3", true, "=");
		rocks[4] = new Rock(worldmap, "Rock4", true, "new");
		rocks[5] = new Rock(worldmap, "Rock5", true, "ArrayList");
		rocks[6] = new Rock(worldmap, "Rock6", true, "<>");
		rocks[7] = new Rock(worldmap, "Rock7", true, "();");
		holes = new Hole[8];
		holes[0] = new Hole(worldmap, "Hole0", "List");
		holes[1] = new Hole(worldmap, "Hole1", "<String>");
		holes[2] = new Hole(worldmap, "Hole2", "list");
		holes[3] = new Hole(worldmap, "Hole3", "=");
		holes[4] = new Hole(worldmap, "Hole4", "new");
		holes[5] = new Hole(worldmap, "Hole5", "ArrayList");
		holes[6] = new Hole(worldmap, "Hole6", "<>");
		holes[7] = new Hole(worldmap, "Hole7", "();");
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

                    			for(int i = 0; i < rocks.length; i++) {
                    				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                    					rocks[i].collideOn();
                    				}
                    			}
                    		
                    	}
                    }


                    if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {
                    	//Kollision mit Loch
                    	if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {
                    		
                    		for(int i = 0; i < rocks.length; i++) {
                    			if(fixA.getUserData().toString().startsWith("Hole") && fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().startsWith("Hole") &&  fixB.getUserData().toString().endsWith(""+i)) {
                    				holes[i].collideOn();
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

                			for(int i = 0; i < rocks.length; i++) {
                				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                					rocks[i].collideOff();
                				}
                			}
                    		
                    	}
                    	
                    } else if(fixA.getUserData().toString().startsWith("Rock") || fixB.getUserData().toString().startsWith("Rock")) {
                    	
                    	if(fixA.getUserData().toString().startsWith("Hole") || fixB.getUserData().toString().startsWith("Hole")) {
                    		
                			for(int i = 0; i < holes.length; i++) {
                				if(fixA.getUserData().toString().endsWith(""+i) || fixB.getUserData().toString().endsWith(""+i)) {
                					holes[i].collideOff();
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
            		
                    if(Player.isCarryingObject) {
                    	
                    	//clear holes
                    	for(int i = 0; i < rocks.length; i++) {
                    		
                    		if(holes[i].holdsRock()) {
                    			if(holes[i].getRock().equals(carryingStone)) {
                    				holes[i].removeRock();
                    			}
                    		}
                    		
                    	}

                        if(playerBody.getLinearVelocity().isZero()){

                        	for(int i = 0; i < holes.length; i++) {
                        		if(holes[i].collidesWithRock() && !holes[i].holdsRock()) {
                        			try {
                        				holes[i].putRock(carryingStone);
                        			} catch(Exception e) {
                        				e.printStackTrace();
                        			}
                        			carryingStone = null;
                        		}
                        	}
                        	
                        	boolean holeCollidesWithRock = false;
                        	
                        	for(int i = 0; i < holes.length;i++) {
                        		if(holes[i].collidesWithRock() && !holes[i].holdsRock()) {
                        			holeCollidesWithRock = true;
                        		}
                        	}
                        	
                        	if(holeCollidesWithRock == false && carryingStone!=null) {
                        		carryingStone.putDown();
                                carryingStone = null;
                        	}
                        	
                        	//Puzzle fertig?
                        	int puzzlesComplete = 0;
                        	for(int i = 0; i < rocks.length; i++) {
                        		
                        		if(holes[i].holdsRock() && holes[i].datatype().equals(holes[i].getRock().datatype())) {
                        			puzzlesComplete++;
                        		}
                        		
                        	}
                        	if(puzzlesComplete==8) {
                        		Timer.schedule(new Timer.Task(){


                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);

                    			Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        door.open();
                                    }
                                });
                        	}
                        	
                        } else {
                            System.err.println("Bitte stehen bleiben um Stein abzulegen!");
                        }

                    } else {

                    	for(int i = 0; i < rocks.length; i++) {
                    		if(rocks[i].collidesWithPlayer()) {
                    			carryingStone = rocks[i];
                    			carryingStone.pickUp();
                    		}
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
		
		for(int i = 0; i < holes.length; i++) {
			holes[i].draw(batch);
		}
		
		if(!playerOverRocks) {
			for(int i = 0; i < rocks.length; i++) {
				rocks[i].draw(batch);
			}
		}
		
	}

	@Override
	public void drawObjectsOverPlayer(Batch batch) {
		
		//Zeichnet nur wenn der Player mit seinen Füßen über den Steinen clippt
        if(playerOverRocks){
        	for(int i = 0; i < rocks.length; i++) {
				rocks[i].draw(batch);
			}
        }

        door.draw(batch);
		
	}

}
