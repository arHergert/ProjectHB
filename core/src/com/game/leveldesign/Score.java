package com.game.leveldesign;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Zählt die Zeit, die der Spieler für's durchspielen der
 * Level braucht und berechnet
 *
 * Created by Artur on 09.07.2017.
 */
public class Score {

    private long startTime;


    /**
     * Speichert die Zeit um die das Spiel begonnen hat,
     * damit am Ende des Spiels die Spielzeit berechnet
     * werden kann
     */
    public Score(){

        startTime = TimeUtils.millis();

    }



    public String calculateTime(){
        //Gesamtzeit in Millisikunden bestimmen
        long endTime = TimeUtils.timeSinceMillis(startTime);

        return ""+ (endTime /1000);
    }

}//end class Score
