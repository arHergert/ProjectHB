package com.game.leveldesign.levels;

import com.badlogic.gdx.physics.box2d.Contact;
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



    public Sensor getDoorUp(){
        return this.doorUp;
    }

    public Sensor getDoorBottom(){
        return this.doorBottom;
    }

}//end class Level
