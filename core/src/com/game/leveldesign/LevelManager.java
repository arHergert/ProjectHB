package com.game.leveldesign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.game.MapCamera;
import com.game.leveldesign.levels.*;

import java.util.ArrayList;

import static com.game.Main.scoremanager;

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

    private InputProcessor currentlevelInputLogic;



    /**
     *
     */
    public LevelManager(){

        //Hinzufügen der Level
        levelList.add(0, new Level_0());
        levelList.add(1, new Level_1());
        levelList.add(2, new Level_2());
        levelList.add(3, new Level_3());
        levelList.add(4, new Level_4());
        levelList.add(5, new Level_5());
        levelList.add(6, new Level_6());
        levelList.add(7, new Level_7());
        levelList.add(8, new Level_nMinus1());
        levelList.add(9, new Level_Score());
        levelList.add(10, new Level_Credits());

        //Aktuelles Level bestimmen
        this.currentlevel = levelList.get(0);

        //Spawnpoint des Spielers für das Startlevel bestimmten
        currentSpawn = SPAWN_START;

        //Inputlogik für das aktuelle Level festlegen
        currentlevelInputLogic = currentlevel.levelInput();

        //Punktemanager initialisieren
        scoremanager = new Score();

    }


    //IM

    /**
     * Gibt die {@link WorldMap} des aktuellen Levels zurück
     *
     * @return WorldMap des aktuellen Levels
     */
    public WorldMap getCurrentWorldMap(){
        return currentlevel.getWorldMap();
    }

    public Level getCurrentlevel(){
        return currentlevel;
    }

    /**
     * Gibt an ob das aktuelle Level eines der Level ist,
     * die zu lang sind und bei der die Kamera zum Start in der Mitte
     * liegt.
     * Level die zu lang sind sollten in dieser Methode eingetragen werden.
     * @return
     */
    public boolean currentLevelIsLong(){
        return (currentlevel == levelList.get(7) || currentlevel == levelList.get(9)) ;
    }


    /**
     * Falls ein aktuelles Level zu lang ist, wird
     * die Kameraposition verschoben auf den gewünschten Ort.
     * Jede Verschiebung muss für jedes gewünschte Level
     * eingetragen werden.
     * @param camera
     */
    public void translateToPlayer(MapCamera camera) {

        if(currentlevel == levelList.get(7) ){
            camera.translateInBounds(-440,0);
        }else if (currentlevel == levelList.get(9)){
            camera.translateInBounds(-725,0);
        }


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
                System.err.println("Kein naechstes Level verfuegbar. Bitte Sensor entfernen oder naechstes Level hinzufuegen!");

            }else{
                //Anzahl eingesammelter Collectibles des Levels speichern
                scoremanager.addCollectibles(currentlevel.garneredLevelCollectibles());

                currentlevel = levelList.get(levelList.indexOf(currentlevel)+1 );

                System.out.println("Eingesammelte Collectibles insgesamt: " + scoremanager.getCollectedCount());
            }


        }else{ //Vorheriges Level gehen

            currentlevel.getDoorUp().deactivate();
            currentSpawn = SPAWN_BOTTOM;

            //Überprüft ob es ein vorheriges Level gibt und wechselt zu diesem, ansonsten gibt es eine Fehlermeldung aus
            if( levelList.indexOf(currentlevel)-1 < 0){
                System.err.println("Kein vorheriges Level verfuegbar. Bitte Sensor entfernen oder vorheriges Level hinzufuegen!");

            }else{
                currentlevel = levelList.get(levelList.indexOf(currentlevel)-1 );
            }


        }


        //Level wurde gewechselt -> FLAG zurücksetzen
        this.playerSwitchesLevel = false;

        //Inputlogik für das aktuelle Level aktualisieren
        currentlevelInputLogic = currentlevel.levelInput();



    }


    public String getSpawnpoint(){
        return this.currentSpawn;
    }

    public InputProcessor getCurrentlevelInputLogic(){
        return this.currentlevelInputLogic;
    }




    /**
     * Entfernt alle Ressourcen der Levels mit deren Box2d world und TiledMap maps
     * aus dem RAM
     */
    public void disposeAllLevels(){

        for (Level level : levelList) {
            level.getWorldMap().getMap().dispose();
            level.getWorldMap().getWorld().dispose();
        }

    }


}//end class LevelManager
