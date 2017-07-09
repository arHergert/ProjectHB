package com.game.box2d.mapobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

import static com.game.Main.assetManager;
import static com.game.Main.spritesheet;

/**
 * Created by Artur on 09.07.2017.
 */
public class Collectibles extends MapObjects {


    private TextureRegion texture;

    private boolean wasCollected;
    /**
     * Erstellt ein neues Tür-Objekt
     * Doors müssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
     * @param map
     * @param nameInTiledMap Der Name des Objektes, welches es im Tiled Map Editor trägt
     */
    public Collectibles(WorldMap map, String nameInTiledMap){

        super( map);
        PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("Collectibles").getObjects().get(nameInTiledMap));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        fixture.setUserData(nameInTiledMap);
        fixture.setSensor(true);

        wasCollected=false;

        int randomInt = MathUtils.random(0,5);


        switch (randomInt){
            case 0:{
                texture = spritesheet.findRegions("collectible").get(0);
            }break;

            case 1:{
                texture = spritesheet.findRegions("collectible").get(1);
            }break;

            case 2:{
                texture = spritesheet.findRegions("collectible").get(2);
            }break;

            case 3:{
                texture = spritesheet.findRegions("collectible").get(3);
            }break;

            case 4:{
                texture = spritesheet.findRegions("collectible").get(4);
            }break;

            case 5:{
                texture = spritesheet.findRegions("collectible").get(5);
            }break;

        }//end switch randomInt

    }


    public void collect(){

        if(!wasCollected){
            assetManager.get("sounds/collectible.wav", Sound.class).play();
        }
        wasCollected = true;
        body.setActive(false);
    }

    public void draw(Batch batch) {

        if(!wasCollected){
            batch.draw(texture, positionX, positionY, 8, 8);
        }

    }


}//end class Collectibles

