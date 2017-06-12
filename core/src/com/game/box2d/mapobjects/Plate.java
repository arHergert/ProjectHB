package com.game.box2d.mapobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Timer;
import com.game.leveldesign.WorldMap;

/**
 * Eine Druckplatte aktiviert sich wenn man sie beschwert.
 * Sobald man das Gewicht entfert deaktiviert sie sich, außer
 * sie ist eingerastet, dann bleibt sie aktiviert
 */
public class Plate extends MapObjects {

    private Texture status_neutral, status_true, status_false, currentTexture;

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

        status_neutral = new Texture(Gdx.files.internal("plate/pressureplate_default.png"));
        status_true = new Texture(Gdx.files.internal("plate/pressureplate_true.png"));
        status_false = new Texture(Gdx.files.internal("plate/pressureplate_false.png"));

        currentTexture = status_neutral;
    }


	/**
	* Wird aufgerufen, wenn Platte beschwert wird, und aktiviert diese
	*/
	public void load() {
        currentTexture = status_true;
		isActivated = true;
	}

	/**
	* Wird aufgerufen, wenn sich kein Gewicht mehr auf der Platte befindet, und deaktivert diese, wenn sie nicht einrastet
	*/
	public void unload() {
        currentTexture = status_false;
		if(!lock) {
			isActivated = false;
		}
	}


	/**
	* @return Zustand der Druckplatte
	*/
	public boolean isActivated() {
		return isActivated;
	}


	public void reset() {
        currentTexture = status_neutral;
		isActivated = false;

	}


    @Override
    public void draw(Batch batch) {

        batch.draw(currentTexture, positionX, positionY, 16,16 );
    }
}//end class Plate