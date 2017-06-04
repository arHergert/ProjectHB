package com.game.box2d.mapobjects;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

/**
 * Eine Druckplatte aktiviert sich wenn man sie beschwert.
 * Sobald man das Gewicht entfert deaktiviert sie sich, außer
 * sie ist eingerastet, dann bleibt sie aktiviert
 */
public class Plate extends MapObjects {

	/**
	* Gibt an, ob die Druckplatte aktiviert ist. Standardweise false
	*/
	private boolean isActivated = false;

	/**
	* Gibt an, ob die Bodenplatte einrastet. Standardweise false
	*/
	private boolean lock = false;


    /**
     * Erstellt eine Druckplatte.
     * Druckplatten müssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
     * @param map Referenz auf das Level, in der sich "InteractiveObjects" Ebene befindet
     * @param lock Ob die Druckplatte einrasten soll oder nicht
     * @param mapSensorObject Name des Sensors (Userdata). MUSS einzigartig sein.
     */
    public Plate(WorldMap map, boolean lock, String mapSensorObject) {
        super(map);
        this.lock = lock;
        PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(mapSensorObject);
    }


	/**
	* Wird aufgerufen, wenn Platte beschwert wird, und aktiviert diese
	*/
	public void load() {
		isActivated = true;
	}

	/**
	* Wird aufgerufen, wenn sich kein Gewicht mehr auf der Platte befindet, und deaktivert diese, wenn sie nicht einrastet
	*/
	public void unload() {
		if(!lock) {
			isActivated = false;
		}
	}


	/**
	* @return Zustand der Druckplatte
	*/
	protected boolean isActivated() {
		return isActivated;
	}


}