package com.game.leveldesign.levels;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.box2d.mapobjects.Sensor;
import com.game.leveldesign.WorldMap;

/**
 * Muster-Level-Klasse.
 */
public abstract class Level {

    /** Map und World des aktuellen Levels */
    protected WorldMap worldmap;

    /** Sensoren für die Türobjekte. Jedes Level hat eine obere und Untere Tür (außer 1-2 Außnahmen)*/
    private Sensor doorUp;
    private Sensor doorBottom;

    /**
     *
     */
    public Level (String mapFile ){

        worldmap = new WorldMap(mapFile);

        //Türsensoren für das aktuelle Level initialisieren
        doorUp = new Sensor(worldmap, "doorUp" );
        doorBottom = new Sensor(worldmap, "doorBottom");

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

        if(fixA.getUserData().equals("Player") || fixB.getUserData().equals("Player")) {
            if (fixA.getUserData().equals("doorUp") || fixB.getUserData().equals("doorUp")){
                Fixture doorUpFixture = fixA.getUserData() == "doorUp" ? fixA : fixB;
                Fixture player = doorUpFixture == fixA ? fixB : fixA;

                doorUp.activate();


            }

            if (fixA.getUserData().equals("doorBottom") || fixB.getUserData().equals("doorBottom")){
                Fixture doorBottomFixture = fixA.getUserData() == "doorBottom" ? fixA : fixB;
                Fixture player = doorBottomFixture == fixA ? fixB : fixA;

                doorBottom.activate();

            }

        }

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
}//end class Level
