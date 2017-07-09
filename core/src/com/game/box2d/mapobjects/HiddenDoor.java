package com.game.box2d.mapobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

import static com.game.Main.assetManager;

/**
 * Versteckte Tür, die dem Player den Weg versperrt.
 * Beim öffnen der Tür bleibt sie geöffnet.
 *
 * Created by Artur on 09.07.2017.
 */
public class HiddenDoor extends MapObjects{

    /** Referen auf die Map in der sich die versteckte Tür befindet */
    private WorldMap map;

    /**
     * Erstellt eine neue versteckte Tür
     * HiddenDoors müssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
     * @param map
     */
    public HiddenDoor(WorldMap map, String mapSensorObject) {
        super( map);
        PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(mapSensorObject);

        this.map = map;

    }


    /**
     * Öffnet die versteckte Tür
     */
    public void openPath(){

        try{
            body.setActive(false);
        }catch (Exception e){
            e.printStackTrace();
        }

        assetManager.get("sounds/hiddendoor.wav", Sound.class).play();
        map.getMap().getLayers().get("Hidden").setVisible(false);

    }



}//end class HiddenDoor