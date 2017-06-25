package com.game.leveldesign.levels;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.box2d.mapobjects.Door;
import com.game.box2d.mapobjects.Sensor;
import com.game.leveldesign.WorldMap;

/**
 * Muster-Level-Klasse.
 */
public abstract class Level {

    /** Map und World des aktuellen Levels */
    protected WorldMap worldmap;

    /** Fixtures für die Kollisionsabfragen in den Subklassen-Leveln */
    protected Fixture fixA;
    protected Fixture fixB;

    /** Sensoren für die Türobjekte. Jedes Level hat einen obereren und unteren Türsensor */
    private Sensor doorUp;
    private Sensor doorBottom;

    /** Türobjekt, die einen dürchlässt oder stoppt. Jedes Level hat mindestens eine Tür */
    protected Door door;
    /**
     *
     */
    public Level (String mapFile ){

        worldmap = new WorldMap(mapFile);

        //Türsensoren für das aktuelle Level initialisieren
        doorUp = new Sensor(worldmap, "doorUp" );
        doorBottom = new Sensor(worldmap, "doorBottom");

        //Tür für das Level initialisieren. Wenn keine Tür im Level verfügbar ist, wird eine Warnung ausgegeben
        try{
            door = new Door(worldmap,"Door");
        }catch(Exception e){
            System.err.println( mapFile +" hat keine Tuer! Bitte Tuer hinzufuegen");
        }

        setLevelCollisionListener(levelContact());

    }


    //IM

    /**
     * Definiert den Contactlistener für das aktuelle Level.
     * Als Übergabeparameter soll die überschriebene levelContact()
     * Methode in den Subklassen übergeben werden
     * @param contactListener
     */
    protected void setLevelCollisionListener(ContactListener contactListener){
        this.worldmap.getWorld().setContactListener(contactListener);
    }

    /**
     * Methode zum erstellen des Contactlisteners.
     * Muss in den Subklassen überschrieben werden.
     * Folgendermaßen sieht die Methode in den Subklassen aus:
     *
     * <pre><code>
     * protected ContactListener levelContact() {
     *
     *    return new ContactListener() {
     *           //Automatisch erstellte ContactListener Methoden in
     *           denen die Levellogik konfiguriert werden sollen
     *      }
     *
     *  }
     * </code></pre>
     *
     * @return levelContact
     */
    protected abstract ContactListener levelContact();


    /**
     * Methode zum Erstellen des InputProcessors.
     * Muss in den Subklassen überschrieben werden.
     * Folgendermaßen sieht die Methode in den Subklassn aus:
     * <pre><code>
     *     public InputProcessor levelInput() {
             return new InputAdapter(){
                 public boolean keyDown(int keycode) {
                    //Levellogic
                 }

                //Eventuell weitere Methoden wie keyUp etc.

             };
         }
     * </code></pre>
     * @return InputProcessor des Levels
     */
    public abstract InputProcessor levelInput();

    public WorldMap getWorldMap(){
        return this.worldmap;
    }


    /**
     * Methode zur Kollisionsabfragen der Türen im aktuellen Level.
     * Mehthode <b>MUSS</b> in der Subklasse in der überschriebenen ContactListenerklasse
     * aufgerufen werden um die Türsensoren zum Laufen zu bringen.
     * Folgendermaßen wird es implementiert:
     *
     * <pre><code>
     //beginContact Methode in dem ContactListener
     public void beginContact(Contact contact) {

         Fixture fixA = contact.getFixtureA();
         Fixture fixB = contact.getFixtureB();

         if( fixA.getUserData() != null && fixB.getUserData() != null){

         <b>checkDoorCollisions(fixA,fixB);</b>  <b><--- Aus Level aufgerufene checkDoorCollisions Methode</b>

         }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert
     }
     * </code></pre>
     * @param fixA
     * @param fixB
     */
    protected void checkDoorCollisions(Fixture fixA, Fixture fixB){

        if(fixtureIs("Player")) {
            if (fixtureIs("doorUp")){
                doorUp.activate();
            }

            if (fixtureIs("doorBottom")){
                doorBottom.activate();
            }

        }

    }


    /**
     * Überprüft ob bei zwei Kollisionen alle Kollisionsobjekte eine
     * Userdata (Namen) haben. Wichtig um benamte Objekte
     * zu überprüfen.
     * @return
     */
    protected boolean fixturesNotNull(){
        return (fixA.getUserData() != null && fixB.getUserData() != null);
    }

    /**
     * Überprüft ob eine der zwei kollidierenden FIxtures das jeweilige
     * Objekt ist.
     * @param collisionName Name des Objektes mit dem geprüft werden soll ob es mit etwas
     *                      anderen Kollidiert
     * @return
     */
    protected boolean fixtureIs(String collisionName){
        return (fixA.getUserData().equals(collisionName) || fixB.getUserData().equals(collisionName));

    }
    public Sensor getDoorUp(){
        return this.doorUp;
    }

    public Sensor getDoorBottom(){
        return this.doorBottom;
    }

    /**
     * Kann in den einzelnen Leveln überschrieben oder
     * leer gelassen werden.
     * Sollte überschrieben werden, wenn Grafiken für
     * MapObjects (z.B. Lever, Plates etc.) gerendert werden sollen
     *
     * @param batch Die SpriteBatch batch aus der main
     */
    public abstract void drawObjects(Batch batch);

    /**
     * Zeichnet wie {@link Level#drawObjects(Batch)}
     * die Levelgrafiken. Sollte benutzt werden, wenn
     * Grafiken über den Spieler, statt hinter ihm
     * gezeichnet werden sollen (z.B. Türen)
     * @param batch Die SpriteBatch batch aus der main
     */
    public abstract void drawObjectsOverPlayer(Batch batch);

}//end class Level
