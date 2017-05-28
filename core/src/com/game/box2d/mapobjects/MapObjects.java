package com.game.box2d.mapobjects;

import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.LevelMap;

/**
 * Created by Artur on 21.05.2017.
 */
public abstract class MapObjects {

    protected World world;
    protected LevelMap level;
    protected Body body;
    protected FixtureDef fixtureDef;
    protected BodyDef bodyDef;

    public MapObjects(){}

    public MapObjects(World world, LevelMap map){
        this.world = world;
        this.level = map;

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

    }



    protected PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) ,
                (rectangle.y + rectangle.height * 0.5f ) );
        polygon.setAsBox(rectangle.width * 0.5f ,
                rectangle.height * 0.5f ,
                size,
                0.0f);
        return polygon;
    }

    protected CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius );
        circleShape.setPosition(new Vector2(circle.x , circle.y ));
        return circleShape;
    }

    protected PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        System.arraycopy(vertices, 0, worldVertices, 0, vertices.length);

        polygon.set(worldVertices);
        return polygon;
    }

    protected ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] ;
            worldVertices[i].y = vertices[i * 2 + 1] ;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }



}//end class InteractiveMapObjects

