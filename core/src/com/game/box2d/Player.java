package com.game.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.game.LevelMap;

/**
 * Created by Artur on 15.05.2017.
 */
public class Player extends Sprite {

   public  static final float PPM = 16 * 5.6f;

    private World world;
    public Body body;
    private Texture boxImg;
    private float startX = 0;
    private float startY = 0;

    public Player (World world, LevelMap level, String playerImgFile , String spawnpoint){
        this.world = world;
        boxImg = new Texture(Gdx.files.internal(playerImgFile));


        if (spawnpoint.equals("spawnStart")){
            startX = level.getSpawnStartX();
            startY = level.getSpawnStartY();

        } else if( spawnpoint.equals("spawnBottomDoor")){
            startX = level.getSpawnBottomDoorX();
            startY = level.getSpawnBottomDoorY();

        } else if (spawnpoint.equals("spawnUpperDoor")){
            startX = level.getSpawnUpperDoorX();
            startY = level.getSpawnUpperDoorY();
        }

        definePlayer(startX, startY);

    }


    public void definePlayer(float startX, float startY){

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(startX , startY );
        def.fixedRotation = true;
        body = world.createBody(def);

        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(boxImg.getWidth()/2, boxImg.getHeight()/2);

        body.createFixture(pShape, 1.0f).setUserData("Player");
        pShape.dispose();


    }

    public Texture getImg(){
        return boxImg;
    }

    public int getSpriteHeight(){
       return boxImg.getHeight();
    }

    public int getSpriteWidth(){
        return boxImg.getWidth();
    }


    public void draw(Batch batch){
        batch.draw(boxImg, body.getPosition().x - boxImg.getWidth()/2, body.getPosition().y - boxImg.getHeight()/2);
    }

}//end class Player

