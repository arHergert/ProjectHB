package com.game.box2d.mapobjects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

/**
 * Eine Tür kann geschlossen sein oder offen.
 */
public class Door extends MapObjects {

    TextureAtlas atlas;
	/** Speichert den Wert, ob die Tür geöffnet oder geschlossen ist */
	private boolean isOpen;
	
	/**
	 * Erstellt ein neues Tür-Objekt
     * Doors müssen sich im Level in der Ebene "InteractiveObjects" befinden.
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
	
	/** Gibt den Wert zurück, der aussagt, ob die Tür geöffnet oder geschlossen ist. */
	public boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * Öffnet die Tür.
	 */
	public void open() {
		isOpen = true;
        body.setActive(false);
	}
	
	/**
	 * Schließt die Tür.
	 */
	public void close() {
		isOpen = false;
        body.setActive(true);
	}

}