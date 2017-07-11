package com.game.leveldesign.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.box2d.mapobjects.Sensor;
import com.game.leveldesign.TryAndCatchFont;

import static com.game.Main.assetManager;
import static com.game.box2d.Player.PLAYER_SPEED;

/**
 * Created by Artur on 04.06.2017.
 */
public class Level_Credits extends Level{


    private Sensor startMusic = new Sensor(worldmap,"StartMusic");
    private boolean creditsInitiated = false;
    private TryAndCatchFont font;
    private TryAndCatchFont titleFont;
    private int positionX, positionY;


    /**
     *
     */
    public Level_Credits() {
        super("level_credits_map.tmx");

        gameCompleted = true;
        font = new TryAndCatchFont(9);
        titleFont = new TryAndCatchFont(13);

        positionX = worldmap.getMapLeft()+80;
        positionY = worldmap.getMapHeight()/2+60;


    }

    @Override
    protected ContactListener levelContact() {


        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                if (fixturesNotNull()) {

                    if(fixtureIs("Player")) {
                        if (fixtureIs("doorBottom")) {

                            Gdx.app.exit();

                        }
                    }

                    // wenn irgendwas mit dem Spieler kollidiert
                    if (fixtureIs("Player") || fixtureIs("Player_feet")) {

                        if(!creditsInitiated){
                            creditsInitiated = true;
                            assetManager.get("sounds/credits.mp3", Music.class).setVolume(0.5f);
                            assetManager.get("sounds/credits.mp3", Music.class).play();

                        }



                    }

                }


            }

            public void endContact(Contact contact) {}
            public void preSolve(Contact contact, Manifold oldManifold) {}
            public void postSolve(Contact contact, ContactImpulse impulse) {}
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
        PLAYER_SPEED = 1.8f;
        titleFont.draw(batch,"Spiel erstellt von:", positionX,positionY);
        font.draw(batch,"Maximilian Pallasch\nFlorian Osterberg\nArtur Hergert", positionX+20,positionY-16);

        titleFont.draw(batch,"Texturen:", positionX+150,positionY);
        font.draw(batch,"-\"Dungeon Tileset\" by \nCalciumtrice\n" +
                "-Textures and Animations by \nArtur Hergert", positionX+170,positionY-14);

        titleFont.draw(batch,"Sounds (opengameart.org):", positionX+310,positionY);
        font.draw(batch,"-Puzzle Completed Sound by \nDavid McKee (ViRiX)\n" +
                "-GUI Sounds by LokiF" +
                "-Button Sound by Pauli W.", positionX+330,positionY-14);
        font.draw(batch,"-Walking Sound Effects by HaelDB\n" +
                "-False Plates Sound by dklon\n" +
                "-Plate Sounds by p0ss and NenadSimic\n" +
                "-Collectible Sound by NenadSimic", positionX+590,positionY-14);

        titleFont.draw(batch,"Musik (opengameart.org):", positionX+810,positionY);
        font.draw(batch,"Hero Immortal\" by Trevor Lentz", positionX+830,positionY-35);


        titleFont.draw(batch,"Fonts:", positionX+1040,positionY);
        font.draw(batch,"-Code New Roman by Samiru R.\n" +
                "-Disposable Droid by Nate Piekos \n(studio@blambot.com)", positionX+1055,positionY-20);

        titleFont.draw(batch,"Danke an unsere Beta-Tester:", positionX+1240,positionY);
        font.draw(batch,"- Oliver.\n" +
                "- Christian Reusrath", positionX+1255,positionY-20);

        titleFont.draw(batch,"Danke fuer's Spielen\n    :)", positionX+1450,positionY-25);

        titleFont.draw(batch,"Spiel beenden", positionX+1480,positionY-72);

    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

    }


}//end class Level_0
