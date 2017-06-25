package com.game.box2d.mapobjects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

/**
 * Eine T�r kann geschlossen sein oder offen.
 */
public class Door extends MapObjects {

    TextureAtlas atlas;
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
        body.setActive(false);
	}
	
	/**
	 * Schlie�t die T�r.
	 */
	public void close() {
		isOpen = false;
        body.setActive(true);
	}

}