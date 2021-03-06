package com.game.input;

import com.badlogic.gdx.*;
import com.game.leveldesign.WorldMap;
import com.game.MapCamera;
import com.game.box2d.Player;

import static com.game.box2d.Player.*;


public class PlayerMovementInputProcessor extends InputAdapter{

    //IV
    private boolean camMoveLeft, camMoveRight, camMoveUp, camMoveDown;
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

            if (playerBody.getPosition().y < levelmap.getMapTop() - boxPlayer.getSpriteHeight()/2){
                vertSpeed += moveSpeed;
            }

            if (playerBody.getPosition().y >= camera.position.y){
                camera.translateInBounds(0,moveSpeed);
            }

        }

        if (camMoveDown){

                vertSpeed -= moveSpeed;

            if (playerBody.getPosition().y <= camera.position.y){
                camera.translateInBounds(0,-moveSpeed);
            }

        }

        playerBody.setLinearVelocity(horizSpeed * (PPM * PLAYER_SPEED)   , vertSpeed * (PPM * PLAYER_SPEED) );



    }

    public boolean keyDown(int keycode) {


        if(keycode == Input.Keys.D || keycode == Input.Keys.RIGHT){
            camMoveRight = true;
            lastMovedDirection = keycode;
        }

        if(keycode == Input.Keys.A|| keycode == Input.Keys.LEFT){
            camMoveLeft = true;
            lastMovedDirection = keycode;
        }

        if(keycode == Input.Keys.W || keycode == Input.Keys.UP){
            camMoveUp = true;
            lastMovedDirection = keycode;
        }

        if(keycode == Input.Keys.S|| keycode == Input.Keys.DOWN){
            camMoveDown = true;
            lastMovedDirection = keycode;
        }

        if(keycode == Input.Keys.SHIFT_LEFT){

            //Spieler darf nicht rennen, wenn er etwas trägt
            if(!Player.isCarryingObject){
                moveSpeed = 2.5f;
                PLAYER_SPEED = 12f;
                PPM = 64;
            }else{
                System.err.println("Rennen waehrend des Tragens nicht moeglich!");
            }

        }
        return false;
    }


    public boolean keyUp(int keycode) {

        if(keycode == Input.Keys.D|| keycode == Input.Keys.RIGHT){
            camMoveRight = false;
        }

        if(keycode == Input.Keys.A || keycode == Input.Keys.LEFT){
            camMoveLeft = false;
        }

        if(keycode == Input.Keys.W || keycode == Input.Keys.UP){
            camMoveUp = false;
        }

        if(keycode == Input.Keys.S || keycode == Input.Keys.DOWN){
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
