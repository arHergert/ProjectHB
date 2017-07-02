package com.game.leveldesign.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.box2d.mapobjects.Door;
import com.game.box2d.mapobjects.Lever;
import com.game.box2d.mapobjects.Plate;

/**
 * Created by Max on 02.07.2017.
 */
public class Level_5 extends Level {

    //IV
    private Plate[][] plates1;
    private Plate[][] plates2;
    private Door door1;
    private Door door2;
    private BitmapFont font;
    private Lever lever1 = new Lever(worldmap, "Lever1");

    public Level_5() {
        super("level5_map.tmx");
        door1 = new Door(worldmap, "Door1");
        door2 = new Door(worldmap, "Door2");
        plates1 = new Plate[2][2];
        plates2 = new Plate[2][2];
        plates1[0][0] = new Plate(worldmap, true, "Plate000");
        plates1[0][1] = new Plate(worldmap, true, "Plate001");
        plates1[1][0] = new Plate(worldmap, true, "Plate010");
        plates1[1][1] = new Plate(worldmap, true, "Plate011");
        plates2[0][0] = new Plate(worldmap, true, "Plate100");
        plates2[0][1] = new Plate(worldmap, true, "Plate101");
        plates2[1][0] = new Plate(worldmap, true, "Plate110");
        plates2[1][1] = new Plate(worldmap, true, "Plate111");
    }

    @Override
    protected ContactListener levelContact() {
        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if (fixturesNotNull()) {

                    //Kollisionsabfragen für die Türen
                    checkDoorCollisions(fixA, fixB);

                    // Kollision mit Hebel abfragen
                    if (fixtureIs("Lever1")) {
                        lever1.collideOn();

                    }

                    //Restliches Zeug
                    if (fixtureIs("Player_feet")) {

                        if (fixtureIs("Plate000")) {
                            plates1[0][0].load();

                        } else if (fixtureIs("Plate001")) {
                            plates1[0][1].load();

                        } else if (fixtureIs("Plate010")) {
                            plates1[1][0].load();

                        } else if (fixtureIs("Plate011")) {
                            plates1[1][1].load();

                        } else if (fixtureIs("Plate100")) {
                            plates2[0][0].load();

                        } else if (fixtureIs("Plate101")) {
                            plates2[0][1].load();

                        } else if (fixtureIs("Plate110")) {
                            plates2[1][0].load();

                        } else if (fixtureIs("Plate111")) {
                            plates2[1][1].load();

                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if (fixturesNotNull() && fixtureIs("Player")) {

                    // Kollision mit Hebel abfragen
                    if (fixtureIs("Lever1")) {
                        lever1.collideOff();

                    }

                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }

        };
    }
    @Override
    public InputProcessor levelInput() {
        return new InputAdapter(){

            public boolean keyDown(int keycode) {

                return false;
            }

        };
    }

    @Override
    public void drawObjects(Batch batch) {
        door1.draw(batch);
        door2.draw(batch);
        for (int i = 0; i < plates1.length; i++) {
            for (int j = 0; j < plates1[i].length; j++) {

                plates1[i][j].draw(batch);
                plates2[i][j].draw(batch);
            }
        }
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

    }
}

