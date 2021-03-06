package com.game.box2d.mapobjects;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

/**
 * Ein Eimer, welches mit einem Gegenstand gefuellt werden kann
 * und aus dem man diesen wieder herausnehmen kann.
 *
 * @param <T> Inhalt des Eimers. Muss MapObject extenden.
 */
public class Bucket<T extends MapObjects> extends MapObjects {

	/** MapObject, das der Eimer enthaelt */
	private T contents;

    /**
     * Erstellt einen Eimer/Box, in der man ein anderes MapObject hineinlegen kann.
     * Buckets muessen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
     * @param map Referenz auf das Level, in der sich "Door" Ebene befindet
     * @param mapSensorObject Name des Sensors (Userdata). MUSS einzigartig sein.
     */
	public Bucket(WorldMap map, String mapSensorObject) {
		super(map);
        PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(mapSensorObject);
	}
	
	/**
	 * Fuegt ein MapObject als Inhalt des Eimers hinzu.
	 * Ein Eimer kann maximal ein Object enthalten
     *
	 * @param mapObject das MapObject, das in den Eimer gelegt werden soll
	 * @throws Exception wenn der Eimer bereits ein MapObject enthaelt
	 */
	public void fill(T mapObject) throws Exception {
		if(contents == null) {
			contents = mapObject;
		} else {
			throw new Exception("The bucket already holds the maximum amount of objects!");
		}
	}
	
	/**
	 * Leert den Eimer und gibt das enthaltene MapObject zurueck.
	 * @return das MapObject, das vorher im Eimer war
	 */
	public T empty() {
		T t = contents;
		if(contents != null) {
			contents = null;
		}
		return t;
	}
	
	/**
	 * Gibt das MapObject zurueck, das im Eimer enthalten ist.
	 * @return
	 */
	public T contents() {
		return contents;
	}
	
	/**
	 * Prueft, ob der Eimer leer ist.
	 * @return Ist der Eimer leer?
	 */
	public boolean isEmpty() {
		return (contents == null);
	}
	
}