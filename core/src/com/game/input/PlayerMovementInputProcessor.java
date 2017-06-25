package com.game.input;

import com.badlogic.gdx.*;
import com.game.leveldesign.WorldMap;
import com.game.MapCamera;
import com.game.box2d.Player;

import static com.game.box2d.Player.PLAYER_SPEED;
import static com.game.box2d.Player.PPM;
import static com.game.box2d.Player.playerBody;


public class PlayerMovementInputProcessor extends InputAdapter{

    //IV
    boolean camMoveLeft, camMoveRight, camMoveUp, camMoveDown;
    private float moveSpeed = 1f;
    public static int horizSpeed;
    public static int vertSpeed;

    //Constructor

    public PlayerMovementInputProcessor(){

    }


    public void updatePlayerMovement(Player boxPlayer, MapCamera camera, WorldMap levelmap){

        horizSpeed = 0;
        vertSpeed = 0;

        if(camMoveLeft){

            if(playerBody.getPosition().x > levelmap.getMapLeft() + boxPlayer.getSpriteWidth()/2 ){
                horizSpeed -= moveSpeed;
            }

            if (playerBody.getPosition().x<= camera.position.x){
                camera.translateInBounds(-moveSpeed,0);
            }


        }

        if(camMoveRight){

            if (playerBody.getPosition().x < levelmap.getMapRight()- boxPlayer.getSpriteWidth()/2){
                horizSpeed += moveSpeed;
            }

            if (playerBody.getPosition().x >= camera.position.x){
                camera.translateInBounds(moveSpeed,0);
            }

        }

        if(camMoveUp){

            if (playerBody.getPosition().y < levelmap.getMapTop() - boxPlayer.getSpriteHeight()){
                vertSpeed += moveSpeed;
            }

            if (playerBody.getPosition().y >= camera.position.y){
                camera.translateInBounds(0,moveSpeed);
            }

        }

        if (camMoveDown){


           // if (playerBody.getPosition().y > levelmap.getMapBottom() + boxPlayer.getSpriteHeight()/4){
                vertSpeed -= moveSpeed;
           // }


            if (playerBody.getPosition().y <= camera.position.y){
                camera.translateInBounds(0,-moveSpeed);
            }

        }

        playerBody.setLinearVelocity(horizSpeed * (PPM * PLAYER_SPEED)   , vertSpeed * (PPM * PLAYER_SPEED) );



    }

    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.D){
            camMoveRight = true;
        }

        if(keycode == Input.Keys.A){
            camMoveLeft = true;
        }

        if(keycode == Input.Keys.W){
            camMoveUp = true;
        }

        if(keycode == Input.Keys.S){
            camMoveDown = true;
        }

        if(keycode == Input.Keys.SHIFT_LEFT){
          moveSpeed = 2.5f;
          PLAYER_SPEED = 12f;
          PPM = 64;
        }
        return false;
    }


    public boolean keyUp(int keycode) {

        if(keycode == Input.Keys.D){
            camMoveRight = false;
        }

        if(keycode == Input.Keys.A){
            camMoveLeft = false;
        }

        if(keycode == Input.Keys.W){
            camMoveUp = false;
        }

        if(keycode == Input.Keys.S){
            camMoveDown = false;
        }

        if(keycode == Input.Keys.SHIFT_LEFT){
            moveSpeed = 1f;
            PLAYER_SPEED = 5.3f;
            PPM = 16;
        }


        return false;
    }



    public boolean keyTyped(char character) {
        return false;
    }


    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }


}//end class PlayerMovementInputProcessor
