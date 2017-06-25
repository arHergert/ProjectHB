package com.game.box2d.mapobjects;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

/**
 * Ein Hebel kann aktiviert werden und deaktiviert
 * werden.
 */
public class Lever extends MapObjects {

	/**
	* Speichert den Zustand des Hebels. Standardweise false
	*/
	private boolean isActivated = false;


    /**
     * Erstellt einen neuen Hebel
     * Lever müssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
     * @param map
     */
    public Lever(WorldMap map, String mapSensorObject) {
        super( map);
        PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(mapSensorObject);
    }

	/**
	* Wird bei Benutzung des Hebels aufgerufen und ändert den Zustand
	*/
	public void use() {

        isActivated = !isActivated;
        System.out.println(isActivated);
	}


	/**
	* @return Zustand des Hebels
	*/
	public boolean isActivated() {
		return isActivated;
	}




}//end class Lever