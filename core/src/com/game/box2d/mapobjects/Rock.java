package com.game.box2d.mapobjects;

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

	/** Gibt zurück, ob ein Stein gerade hochgehoben ist. Standardweise false */
	private boolean isPickedUp = false;
	private boolean isRectangle;
	
	/**
	 * Erstellt ein neues Stein-Objekt.
     * Das Steinobjekt ist ein Kreisobjekt.
     * Rocks müssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
	 * @param map WorldMap des Levels
     * @param mapSensorObject Name des Objektes in der TiledMap
     * @param isRectangle TRUE, wenn der Stein Rechteckig sein soll, FALSE wenn es rund sein soll
	 */
	public Rock(WorldMap map, String mapSensorObject, boolean isRectangle) {
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
	
}