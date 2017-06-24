package com.game.box2d.mapobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.box2d.Player;
import com.game.leveldesign.WorldMap;

import static com.game.box2d.Player.PLAYER_SPEED;
import static com.game.box2d.Player.PPM;
import static com.game.box2d.Player.playerBody;
import static com.game.input.PlayerMovementInputProcessor.horizSpeed;
import static com.game.input.PlayerMovementInputProcessor.vertSpeed;

/**
 * Ein Stein kann getragen werden und runtergestellt werden
 */
public class Rock extends MapObjects {

	/** Gibt zurück, ob ein Stein gerade hochgehoben ist. Standardweise false */
	private boolean isPickedUp = false;
	private boolean isRectangle;
    //Shape shape;
    /** Texturen für jeden einzelnen Datentypen */
    private Texture intTex,stringTex,floatTex,boolText, currentTexture;
	
	/**
	 * Erstellt ein neues Stein-Objekt.
     * Das Steinobjekt ist ein Kreisobjekt.
     * Rocks müssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
	 * @param map WorldMap des Levels
     * @param mapSensorObject Name des Objektes in der TiledMap
     * @param isRectangle TRUE, wenn der Stein Rechteckig sein soll, FALSE wenn es rund sein soll
     * @param datatype Mit welchen Datentypen der Stein beschriftet sein soll
	 */
	public Rock(WorldMap map, String mapSensorObject, boolean isRectangle, String datatype) {
		super(map);


        Shape shape;
        if (isRectangle){
            shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        }else{
            shape = getCircle((CircleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        }

        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(mapSensorObject);
        shape.dispose();
        body.setType(BodyDef.BodyType.KinematicBody);

        this.isRectangle = isRectangle;

        if(datatype.equals("int")){
            intTex = new Texture(Gdx.files.internal("puzzle textures/level1/rock_rectangle_int.png"));
            currentTexture = intTex;
        }else if(datatype.equals("String")){
            stringTex = new Texture(Gdx.files.internal("puzzle textures/level1/rock_rectangle_String.png"));
            currentTexture = stringTex;
        }else if(datatype.equals("float")){
            floatTex = new Texture(Gdx.files.internal("puzzle textures/level1/rock_rectangle_float.png"));
            currentTexture = floatTex;
        }else if (datatype.equals("boolean")){
            boolText = new Texture(Gdx.files.internal("puzzle textures/level1/rock_rectangle_boolean.png"));
            currentTexture = boolText;
        }else{
            intTex = new Texture(Gdx.files.internal("puzzle textures/level1/rock_rectangle_int.png"));
            currentTexture = intTex;
        }
	}
	
	/**
	 * Hebt einen Stein hoch.
	 */
	public void pickUp() {

        System.out.println("Stein hoch -> " + fixture.getUserData());
		isPickedUp = true;
        Player.isCarryingObject = true;

	}
	
	/**
	 * Setzt einen Stein wieder ab.
	 */
	public void putDown() {
        System.out.println("Stein runter -> " + fixture.getUserData());
        Player.isCarryingObject = false;
        isPickedUp = false;



	}
	
	/**
	 * Gibt zurück, ob ein Stein gerade hochgehoben wird.
	 * @return Ist der Stein hochgehoben?
	 */
	public boolean isPickedUp() {
		return isPickedUp;
	}

	/**
	 * Gibt zurück, ob es sich um einen runden oder um einen eckigen Stein (für Puzzles) handelt.
	 * @return true, wenn der Stein eckig ist
	 */
	public boolean isRectangle() {
		return this.isRectangle;
	}


    public void draw(Batch batch) {


        if(isPickedUp()){


            if(!playerBody.getLinearVelocity().isZero()){
                positionX += horizSpeed * (PLAYER_SPEED/3.75);
                positionY += vertSpeed * (PLAYER_SPEED/3.75);
                body.setLinearVelocity(horizSpeed * (PPM * PLAYER_SPEED)   , vertSpeed * (PPM * PLAYER_SPEED) );
            }else{
                body.setLinearVelocity(0 , 0 );
            }



        }

        batch.draw(currentTexture, positionX, positionY, 32, 22);
    }
}//end class Rock