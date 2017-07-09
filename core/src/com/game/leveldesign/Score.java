package com.game.leveldesign;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;
import com.game.leveldesign.levels.Level;

/**
 * Zählt die Zeit, die der Spieler für's durchspielen der
 * Level braucht und berechnet
 *
 * Created by Artur on 09.07.2017.
 */
public class Score {

    private long startTime;
    private TryAndCatchFont scoreFont = new TryAndCatchFont(23, "ffffff");
    private boolean scoreCalculated = false;
    private int completeTime;
    private String cleanTimeString;
    private int collected;
    private float scoreX, scoreY;

    /**
     * Speichert die Zeit um die das Spiel begonnen hat,
     * damit am Ende des Spiels die Spielzeit berechnet
     * werden kann
     */
    public Score(){

        startTime = TimeUtils.millis();

    }



    private long calculateTime(){
        //Gesamtzeit in Millisikunden bestimmen
        long endTime = TimeUtils.timeSinceMillis(startTime);

        return (endTime /1000);
    }

    /**
     * Wandelt Sekunden in einen String mit Minuten und
     * Sekunden um
     * @param seconds
     * @return
     */
    private String getDurationString(int seconds) {

        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    /**
     * Fügt einer Zahl weitere Nullen hinzu um
     * sie an die Uhrzeitmuster anzupassen
     * @param number
     * @return
     */
    private String twoDigitString(int number) {

        if(number == 0) {
            return "00";

        }else if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }




    public void printScoreScreen(Batch batch, WorldMap map){

        if(!scoreCalculated){

            completeTime = (int) calculateTime();
            cleanTimeString = getDurationString(completeTime);
            scoreX = map.getMapWidth()/2-120;
            scoreY = map.getMapHeight()/2+30;
            scoreCalculated = true;
        }


        scoreFont.draw(batch, "Time: "+ cleanTimeString+" Minuten", scoreX, scoreY);
        scoreFont.draw(batch, "Collectibles: "+ collected + " / 99", scoreX, scoreY-50);
    }





    public void addCollectibles(int number){
        collected += number;
    }

    public int getCollectedCount(){
        return collected;
    }

}//end class Score
