package com.game.box2d.mapobjects;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.LevelMap;

/**
 * Created by Artur on 21.05.2017.
 */
public class Sensor extends MapObjects {
    protected Fixture fixture;
    private boolean activated = false;

    public Sensor(World world, LevelMap map, String mapSensorObject) {
        super(world, map);
        PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("Door").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(mapSensorObject);
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
