package com.game.box2d.mapobjects;

import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.WorldMap;

/**
 * Standarddefinierung eines Box2D Kollisionsobjektes, welches mit dem Spieler
 * kollidieren kann
 */
public abstract class MapObjects {

    protected World world;
    protected WorldMap level;
    protected Body body;
    protected FixtureDef fixtureDef;
    protected BodyDef bodyDef;
    protected Fixture fixture;

    public MapObjects(){}

    /**
     * <b>Warnung: Ein MapObject allein kann nicht instanziiert werden,
     * da es keine Kollisionsform besitzt</b>
     *
     * @param map Map mit den Ebenen zur Definierung des Objektes und der Welt
     */
    public MapObjects(WorldMap map){
        this.world = map.getWorld();
        this.level = map;

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

    }


    /**
     * Erstellt ein Rechteck als Kollisionsform und speichert die Position
     * des Objektes aus der Levelebende
     *
     * @param rectangleObject
     * @return Rechteck
     */
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

    /**
     * Erstellt ein Kreis als Kollisionsform und speichert die Position
     * des Objektes aus der Levelebende
     *
     * @param circleObject
     * @return Kreis
     */
    protected CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius );
        circleShape.setPosition(new Vector2(circle.x , circle.y ));
        return circleShape;
    }

    /**
     * Erstellt ein freiförmiges Polygon als Kollisionsform und speichert die Position
     * des Objektes aus der Levelebende
     *
     * @param polygonObject
     * @return Polygon
     */
    protected PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        System.arraycopy(vertices, 0, worldVertices, 0, vertices.length);

        polygon.set(worldVertices);
        return polygon;
    }

    /**
     * Erstellt eine Linie als Kollisionsform und speichert die Position
     * des Objektes aus der Levelebende
     *
     * @param polylineObject
     * @return Linie
     */
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

