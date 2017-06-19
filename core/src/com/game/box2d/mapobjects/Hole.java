package com.game.box2d.mapobjects;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

public class Hole extends MapObjects {

	private Rock contents;
	
	public Hole(WorldMap map, String mapSensorObject) {
		super(map);
		PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(mapSensorObject);
        fixture.setSensor(true);
	}
	
	public boolean holdsRock() {
		return (contents != null);
	}
	
	public void putRock(Rock rock) throws Exception {
		if(contents == null) {
			if(rock.isRectangle()) {
				contents = rock;
			} else {
				throw new Exception("This rock doesn't fit in that kind of hole.");
			}
		} else {
			throw new Exception("Hole already contains a puzzle rock.");
		}
	}
	
	/**
	 * Nimmt einen Puzzlestein aus einem Loch
	 * und gibt das gel�schte Objekt wieder zur�ck.
	 * @return der Stein, der aus dem Loch genommen wurde.
	 */
	public Rock removeRock() {
		if(contents != null) {
			Rock r = contents;
			contents = null;
			return r;
		} else {
			return null;
		}
	}
	
}
