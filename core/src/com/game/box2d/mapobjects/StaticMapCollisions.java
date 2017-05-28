package com.game.box2d.mapobjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.physics.box2d.*;
import com.game.LevelMap;

/**
 * Created by Artur on 20.05.2017.
 */
public class StaticMapCollisions extends MapObjects {


    public void createObjects(World world, LevelMap level){
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for(MapObject object : level.getMap().getLayers().get("StaticCollisions").getObjects()){

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject)object);
            }
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
            }
            else {
                continue;
            }

            bodyDef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bodyDef);

            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

    }





}//end class StaticMapCollisions
