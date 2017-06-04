package com.game.leveldesign;

import com.game.leveldesign.levels.Level;
import com.game.leveldesign.levels.Level_1;
import com.game.leveldesign.levels.Level_2;

import java.util.ArrayList;

/**
 * Klasse, die sich um alle Levels im Spiel kümmert.
 * Besitzt folgende Aufgaben:
 * - Richtiges Levelwechseln beim durchqueren einer Tür
 *
 */
public class LevelManager {

    /** Liste aller Level, die im laufenden Spiel betreten werden können */
    private ArrayList<Level> levelList = new ArrayList<Level>();

    /** Referenz auf das aktuelle Level*/
    private Level currentlevel;

    /** Prüfen ob der Spieler das aktuelle Level verlassen und in ein neues Gehen will. Wird zum weiteren Levelwechsel benötigt*/
    private boolean playerSwitchesLevel = false;

    /** Strings zum Identifizieren der Spawnpoints.
     * currentSpawn ist wichtig für den Player, damit dieser weiß wo er im nächsten Raum spawnen soll */
    private String currentSpawn;
    private final String SPAWN_START = "spawnStart";
    private final String SPAWN_BOTTOM = "spawnBottomDoor";
    private final String SPAWN_UP = "spawnUpperDoor";

    /**
     *
     */
    public LevelManager(){

        //Hinzufügen der Level
        levelList.add(0, new Level_1());
        levelList.add(1, new Level_2());


        //Aktuelles Level bestimmen
        this.currentlevel = levelList.get(0);

        //Spawnpoint des Spielers für das Startlevel bestimmten
        currentSpawn = SPAWN_START;

    }


    //IM

    /**
     * Gibt die {@link WorldMap} des aktuellen Levels zurück
     *
     * @return WorldMap des aktuellen Levels
     */
    public WorldMap getCurrentlevel(){
        return currentlevel.getWorldMap();
    }


    /**
     * Gibt zurück ob der Spieler das aktuelle Level wechseln möchte.
     * Ist TRUE wenn der Spieler durch einen Tür-Sensor geht.
     *
     * @return Ob der Spieler ein Level wechseln will oder nicht
     */
    public boolean isPlayerSwitchingLevel() {

        playerSwitchesLevel = (currentlevel.getDoorUp().isActivated() || currentlevel.getDoorBottom().isActivated());

        return playerSwitchesLevel;
    }

    /**
     * Wechselt das aktuelle Level
     *
     * Muss benutzt werden, nachdem die {@link LevelManager#isPlayerSwitchingLevel()} Methode
     * abgefragt wurde.
     * Signalisiert, dass der Spieler das Level gewechselt hat und nicht mehr
     * wechseln will.
     *
     */
    public void switchLevel() {

        //Überprüfen durch welche Tür der Spieler gehen will
        if (currentlevel.getDoorBottom().isActivated()){ //Nächstes Level gehen

            currentlevel.getDoorBottom().deactivate();
            currentSpawn = SPAWN_UP;

            //Überprüft ob es ein nächstes Level gibt und wechselt zu diesem, ansonsten gibt es eine Fehlermeldung aus
            if( levelList.indexOf(currentlevel)+1 >= levelList.size()){
                System.err.println("Kein naechstes Level verfuegbar. Bitte Sensor entfernen oder Level hinzufuegen!");
            }else{
                currentlevel = levelList.get(levelList.indexOf(currentlevel)+1 );
            }


        }else{ //Vorheriges Level gehen

            currentlevel.getDoorUp().deactivate();
            currentSpawn = SPAWN_BOTTOM;

            //Überprüft ob es ein vorheriges Level gibt und wechselt zu diesem, ansonsten gibt es eine Fehlermeldung aus
            if( levelList.indexOf(currentlevel)-1 < 0){
                System.err.println("Kein vorheriges Level verfuegbar. Bitte Sensor entfernen oder Level hinzufuegen!");
            }else{
                currentlevel = levelList.get(levelList.indexOf(currentlevel)-1 );
            }


        }

        //Level wurde gewechselt -> FLAG zurücksetzen
        this.playerSwitchesLevel = false;




    }



    public String getSpawnpoint(){
        return this.currentSpawn;
    }
}//end class LevelManager
