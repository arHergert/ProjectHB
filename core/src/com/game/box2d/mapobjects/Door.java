package com.game.box2d.mapobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.game.leveldesign.WorldMap;

import java.util.Arrays;

import static com.game.Main.spritesheet;

/**
 * Eine T�r kann geschlossen sein oder offen.
 */
public class Door extends MapObjects {

    private enum Door_State{CLOSED, OPENING,CLOSING}
    private Door_State currentState;
    private Door_State previousState;
    private float stateTimer;
    private boolean doorWasOpened = false;

    private Animation<TextureRegion> wallOpening;
    private Animation<TextureRegion> wallClosing;
    private TextureRegion wallClosed;
    private TextureRegion currentFrame;

	/** Speichert den Wert, ob die T�r ge�ffnet oder geschlossen ist */
	private boolean isOpen;
	
	/**
	 * Erstellt ein neues T�r-Objekt
     * Doors m�ssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
	 * @param map
	 */
	public Door( WorldMap map, String mapSensorObject) {
		super( map);
		isOpen = false;
        PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(mapSensorObject);
        currentState = Door_State.CLOSING;
        previousState = Door_State.CLOSING;
        stateTimer = 0f;

        wallClosing = new Animation<>(0.020f, spritesheet.findRegions("door"), Animation.PlayMode.REVERSED);
        wallOpening = new Animation<>(0.020f, spritesheet.findRegions("door"));
        wallClosed = new TextureRegion(spritesheet.findRegions("door").get(0));

	}
	
	/** Gibt den Wert zur�ck, der aussagt, ob die T�r ge�ffnet oder geschlossen ist. */
	public boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * �ffnet die T�r.
	 */
	public void open() {
		isOpen = true;
        doorWasOpened = true;

        try{
            body.setActive(false);
        }catch (Exception e){
            e.printStackTrace();
        }

	}
	
	/**
	 * Schlie�t die T�r.
	 */
	public void close() {
		isOpen = false;
        body.setActive(true);
	}

    private TextureRegion getFrame(){
        currentState = getState();

        //System.out.println(getState());
        TextureRegion region;
        switch (currentState){

            case OPENING: {
                region = wallOpening.getKeyFrame(stateTimer);
            }break;

            case CLOSING:{
                region = wallClosing.getKeyFrame(stateTimer);
            }break;

            case CLOSED:
            default:{
                region = wallClosed;
            }break;
        }//end switch case region

        stateTimer = currentState == previousState ? stateTimer + Gdx.graphics.getDeltaTime() : 0;
        previousState = currentState;
        return region;
    }

    private Door_State getState(){
        if(!doorWasOpened){
            return Door_State.CLOSED;

        }else if(isOpen()){
            return Door_State.OPENING;

        }else {
            return Door_State.CLOSING;

        }
    }
    @Override
    public void draw(Batch batch) {

        currentFrame = getFrame();

        batch.draw(currentFrame,positionX,positionY);
    }
}//end class Door