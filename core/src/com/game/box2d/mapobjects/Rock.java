package com.game.box2d.mapobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.leveldesign.WorldMap;
import com.sun.javafx.geom.Rectangle;

/**
 * Ein Stein kann getragen werden und runtergestellt werden
 */
public class Rock extends MapObjects {

	/** Gibt zur�ck, ob ein Stein gerade hochgehoben ist. Standardweise false */
	private boolean isPickedUp = false;
	private boolean isRectangle;

    /** Texturen f�r jeden einzelnen Datentypen */
    private Texture intTex,stringTex,floatTex,boolText, currentTexture;
	
	/**
	 * Erstellt ein neues Stein-Objekt.
     * Das Steinobjekt ist ein Kreisobjekt.
     * Rocks m�ssen sich im Level in der Ebene "InteractiveObjects" befinden.
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
        shape.dispose();
        fixture.setUserData(mapSensorObject);
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
		isPickedUp = true;
	}
	
	/**
	 * Setzt einen Stein wieder ab.
	 */
	public void putDown() {
		isPickedUp = false;
	}
	
	/**
	 * Gibt zur�ck, ob ein Stein gerade hochgehoben wird.
	 * @return Ist der Stein hochgehoben?
	 */
	public boolean isPickedUp() {
		return isPickedUp;
	}

	/**
	 * Gibt zur�ck, ob es sich um einen runden oder um einen eckigen Stein (f�r Puzzles) handelt.
	 * @return true, wenn der Stein eckig ist
	 */
	public boolean isRectangle() {
		return this.isRectangle;
	}


    public void draw(Batch batch) {

        batch.draw(currentTexture, positionX, positionY, 32, 22);
    }
}//end class Rock