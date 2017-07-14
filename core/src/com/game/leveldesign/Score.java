package com.game.leveldesign;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Zählt die Zeit, die der Spieler für's durchspielen der
 * Level braucht und berechnet
 *
 * Created by Artur on 09.07.2017.
 */
public class Score implements Disposable{

    private long startTime;
    private TryAndCatchFont scoreFontDesc = new TryAndCatchFont(23, "717184");
    private TryAndCatchFont scoreFontColor = new TryAndCatchFont(23, "2A9E7E");
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


    private String endScore(){
        if(completeTime < 240) {
            return ""+760 + (collected * 20);
        }else if( completeTime > 760){
            return ""+ 50 + (collected * 20);
        }else{
            return ""+( (760-completeTime) + (collected * 20)  );
        }

    }



    public void drawFirstLine(Batch batch, WorldMap map){

        if(!scoreCalculated){

            completeTime = (int) calculateTime();
            cleanTimeString = getDurationString(completeTime);
            scoreX = map.getMapWidth()/2-130;
            scoreY = map.getMapHeight()/2+50;
            scoreCalculated = true;
        }


        scoreFontDesc.draw(batch, "Zeit: ", scoreX, scoreY);
        scoreFontColor.draw(batch, cleanTimeString+" Minuten", scoreX+70, scoreY);

    }

    public void drawSecondLine(Batch batch, WorldMap map){

        scoreFontDesc.draw(batch, "Collectibles: ", scoreX, scoreY-50);
        scoreFontColor.draw(batch, collected + "/12 ", scoreX+190, scoreY-50);

    }

    public void drawThirdLine(Batch batch, WorldMap map){

        scoreFontDesc.draw(batch, "Endscore: ", scoreX, scoreY-100);
        scoreFontColor.draw(batch, endScore() +"/1000", scoreX+130, scoreY-100);

    }


    public void addCollectibles(int number){
        collected += number;
    }

    public int getCollectedCount(){
        return collected;
    }

    @Override
    public void dispose() {

    }
}//end class Score
