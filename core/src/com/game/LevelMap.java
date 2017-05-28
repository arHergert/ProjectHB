package com.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Artur on 20.05.2017.
 */
public class LevelMap {

    private TiledMap map;
    private int mapWidth, mapHeight, mapLeft, mapRight, mapBottom, mapTop;
    private float spawnStartX = this.getMapWidth()/2, spawnStartY = this.getMapHeight()/2;
    private float spawnBottomDoorX = this.getMapWidth()/2, spawnBottomDoorY = this.getMapHeight()/2;
    private float spawnUpperDoorX = this.getMapWidth()/2, spawnUpperDoorY = this.getMapHeight()/2;
    private MapObjects spawnpoints;


    public LevelMap(String mapfile){

        map = new TmxMapLoader().load(mapfile);
        TiledMapTileLayer mainLayer = (TiledMapTileLayer) map.getLayers().get(0);
        int tileSize = (int) mainLayer.getTileWidth();
        mapWidth = mainLayer.getWidth() * tileSize;
        mapHeight = mainLayer.getHeight() * tileSize;

        mapLeft = 0;
        mapRight = mapWidth;
        mapBottom = 0;
        mapTop = mapHeight;

        spawnpoints = map.getLayers().get("PlayerSpawnpoints").getObjects();

        if (spawnpoints.get("spawnStart") != null){
            setSpawnstartCoordinates();
        }

        if (spawnpoints.get("spawnBottomDoor") != null){
            setSpawnBottomDoor();
        }

        if (spawnpoints.get("spawnUpperDoor") != null){
            setSpawnUpperDoor();
        }

    }



    private void setSpawnstartCoordinates(){
        MapObject object = spawnpoints.get("spawnStart");
        Rectangle rect = ((RectangleMapObject)object).getRectangle();
        this.spawnStartX = rect.getX();
        this.spawnStartY = rect.getY();

    }


    private void setSpawnBottomDoor(){
        MapObject object = spawnpoints.get("spawnBottomDoor");
        Rectangle rect = ((RectangleMapObject)object).getRectangle();
        this.spawnBottomDoorX= rect.getX();
        this.spawnBottomDoorY= rect.getY();

    }

    private void setSpawnUpperDoor(){
        MapObject object = spawnpoints.get("spawnUpperDoor");
        Rectangle rect = ((RectangleMapObject)object).getRectangle();
        this.spawnUpperDoorX= rect.getX();
        this.spawnUpperDoorY= rect.getY();

    }



    public TiledMap getMap(){
        return this.map;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapLeft() {
        return mapLeft;
    }

    public int getMapRight() {
        return mapRight;
    }

    public int getMapBottom() {
        return mapBottom;
    }

    public int getMapTop() {
        return mapTop;
    }

    public float getSpawnStartX() {
        return spawnStartX;
    }

    public float getSpawnStartY() {
        return spawnStartY;
    }

    public float getSpawnBottomDoorX() {
        return spawnBottomDoorX;
    }

    public float getSpawnBottomDoorY() {
        return spawnBottomDoorY;
    }

    public float getSpawnUpperDoorX() {
        return spawnUpperDoorX;
    }

    public float getSpawnUpperDoorY() {
        return spawnUpperDoorY;
    }




}//end class LevelMap

