package com.game.box2d.mapobjects;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.game.leveldesign.WorldMap;

import static com.game.Main.assetManager;
import static com.game.Main.spritesheet;

/**
 * Ein Button sendet bei Aktivierung immer ein
 * Signal.
 */
public class Button extends MapObjects {


    private boolean activated = false;


    private TextureRegion buttonDefault;
    private TextureRegion buttonOn;
    private TextureRegion currentFrame;

    /**
     * Erstellt einen neuen Button
     * Buttons müssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
     * @param map
     */
    public Button(WorldMap map, String mapSensorObject){
        super( map);
        PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(mapSensorObject);
        fixture.setSensor(true);

        buttonDefault = spritesheet.findRegion("button_default");
        buttonOn = spritesheet.findRegion("button_on");
        currentFrame = buttonDefault;

    }


	/**
	* Wird bei Benutzung des Buttons aufgerufen
	*/
	public void use() {
		activated = true;
        currentFrame = buttonOn;
        assetManager.get("sounds/button.wav", Sound.class).play();

        Timer.schedule(new Task(){


            public void run() {
                activated = false;
                currentFrame = buttonDefault;
            }
        }, 0.5f);

    }

    public boolean isActivated(){
        return activated;
    }


    @Override
    public void draw(Batch batch) {

        batch.draw(currentFrame,positionX,positionY, 20,20);
    }


}//end class Button
