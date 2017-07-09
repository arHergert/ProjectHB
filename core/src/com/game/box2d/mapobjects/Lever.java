package com.game.box2d.mapobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

import static com.game.Main.spritesheet;
import static com.game.box2d.Player.PLAYER_SPEED;
import static com.game.box2d.Player.PPM;
import static com.game.box2d.Player.playerBody;
import static com.game.input.PlayerMovementInputProcessor.horizSpeed;
import static com.game.input.PlayerMovementInputProcessor.vertSpeed;

/**
 * Ein Hebel kann aktiviert werden und deaktiviert
 * werden.
 */
public class Lever extends MapObjects {

	/**
	* Speichert den Zustand des Hebels. Standardweise false
	*/
	private boolean isActivated = false;

    /** Region der aktuellen Hebeltextur aus der Spritesheet*/
    private TextureRegion activatedTexture, deactiveatedTexture, currentTexture;

    /**
     * Erstellt einen neuen Hebel
     * Lever müssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
     * @param map
     * @param mapSensorObject
     * @param direction Richtung in welche man den Hebel platziert (Oben, Unten, Links, Rechts, Boden)
     */
    public Lever(WorldMap map, String mapSensorObject , String direction) {
        super( map);
        PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(mapSensorObject);


        switch (direction) {
            case "Oben":
                deactiveatedTexture = spritesheet.findRegions("leveroben").get(0);
                activatedTexture = spritesheet.findRegions("leveroben").get(1);

                break;
            case "Unten":
                deactiveatedTexture = spritesheet.findRegions("leverunten").get(0);
                activatedTexture = spritesheet.findRegions("leverunten").get(1);

                break;
            case "Links":
                deactiveatedTexture = spritesheet.findRegions("leverlinks").get(0);
                activatedTexture = spritesheet.findRegions("leverlinks").get(1);

                break;
            case "Rechts":
                deactiveatedTexture = spritesheet.findRegions("leverrechts").get(0);
                activatedTexture = spritesheet.findRegions("leverrechts").get(1);

                break;
            default:
                deactiveatedTexture = spritesheet.findRegions("leverboden").get(0);
                activatedTexture = spritesheet.findRegions("leverboden").get(1);
                break;
        }//end switch direction

        currentTexture = deactiveatedTexture;

    }

    public void setActivated(boolean b) {
        isActivated = b;
    }

	/**
	* Wird bei Benutzung des Hebels aufgerufen und ändert den Zustand
	*/
	public void use() {

        isActivated = !isActivated;

        currentTexture = isActivated() ? activatedTexture : deactiveatedTexture;

        System.out.println(isActivated);
	}


	/**
	* @return Zustand des Hebels
	*/
	public boolean isActivated() {
		return isActivated;
	}


    public void draw(Batch batch) {

        batch.draw(currentTexture, positionX, positionY, 24, 24);


    }


}//end class Lever