package com.game.box2d.mapobjects;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

/**
 * Sensor zum Überprüfen ob ein anderes Objekt dieses berührt.
 * Kann mit anderen überlappen/ andere Objekte können durch ein Sensor durchgehen.
 *
 * Sobald ein Objekt im Sensor ist oder den Sensor berührt ist dieser aktiviert.
 * Sobald das nicht mehr der Fall ist, deaktiviert er sich.
 */
public class Sensor extends MapObjects {

    /** Flag zum Überprüfen ob der Sensor an ist oder nicht */
    private boolean activated = false;

    /**
     * Erstellt ein Sensorobjekt, mit dem man z.B. Türen erstellen kann.
     *
     * Türen müssen sich im Level in der Ebene "Door" befinden.
     *
     * @param map Referenz auf das Level, in der sich "Door" Ebene befindet
     * @param mapSensorObject Name des Sensors (Userdata). MUSS einzigartig sein.
     */
    public Sensor(WorldMap map, String mapSensorObject) {
        super(map);
        PolygonShape shape;

        if(map.getMap().getLayers().get("Door").getObjects().get(mapSensorObject) == null ){
            shape = null;
        }else{
            shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("Door").getObjects().get(mapSensorObject));
            fixtureDef.shape = shape;
            fixtureDef.isSensor = true;
            fixture = body.createFixture(fixtureDef);

            shape.dispose();
            fixture.setUserData(mapSensorObject);
        }


    }

    public void activate(){
       this.activated = true;
    }

    public boolean isActivated(){
        return this.activated;
    }

    public void deactivate(){
        this.activated = false;
    }

}//end class Sensor
