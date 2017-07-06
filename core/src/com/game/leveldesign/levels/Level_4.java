package com.game.leveldesign.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.game.box2d.Player;
import com.game.box2d.mapobjects.*;

import static com.game.Main.assetManager;
import static com.game.box2d.Player.carryingStone;

/**
 * Created by Max on 25.06.2017.
 */
public class Level_4 extends Level{


    private Plate[][] plates;
    private Door door1;
    private Door door2;
    private BitmapFont font;
    private BitmapFont fontL;
    private BitmapFont fontR;
    private String puzzleTextL =
            "if(!array[0][0] & array[1][1]) {\n" +
            "   door.open();\n" +
            "}\n";
    private String puzzleTextR =
            "if(array[0][0]) {\n" +
            "   door.open();\n" +
            "}\n";

    private String puzzleTextDoor =
            "if (array[0][0] && array[0][1] && array[1][0] && array[1][1]) door.open";
    private Button lever1 = new Button(worldmap,"Lever1");

    public Level_4() {
        super("level4_map.tmx");
        door1 = new Door(worldmap,"Door1");
        door2 = new Door(worldmap,"Door2");
        plates = new Plate[2][2];
        plates[0][0] = new Plate(worldmap, true, "Plate00");
        plates[0][1] = new Plate(worldmap, true, "Plate01");
        plates[1][0] = new Plate(worldmap, true, "Plate10");
        plates[1][1] = new Plate(worldmap, true, "Plate11");

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.color = Color.valueOf("43435d");
        fontParameter.size = 9;
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("CodeNewRoman.otf"));
        fontGenerator.scaleForPixelHeight(9);
        fontParameter.minFilter = Texture.TextureFilter.Nearest;
        fontParameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font = fontGenerator.generateFont(fontParameter);
        fontParameter.size = 14;
        fontR= fontGenerator.generateFont(fontParameter);
        fontParameter.size = 13;
        fontL= fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();
    }

    private boolean allPlatesActivated() {
        boolean r = true;
        for (int i = 0; i< plates.length; i++){
            for (int j = 0; j < plates[i].length; j++ ){
                if(!plates[i][j].isActivated()) r = false;
            }
        }
        return r;
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
                    if(fixtureIs("Lever1")) {
                        lever1.collideOn();

                    }

                    //Restliches Zeug
                    if(fixtureIs("Player_feet")) {

                        if (fixtureIs("Plate00")) {
                            plates[0][0].load();
                            if(allPlatesActivated()) {
                                Gdx.app.postRunnable(() -> door.open());

                                Timer.schedule(new Timer.Task(){

                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);

                            }
                            Gdx.app.postRunnable(() -> door2.open());
                        }else if(fixtureIs("Plate01")) {
                            plates[0][1].load();
                            if(allPlatesActivated()) {
                                Gdx.app.postRunnable(() -> door.open());

                                Timer.schedule(new Timer.Task(){

                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);
                            }
                        }else if(fixtureIs("Plate10")) {
                            plates[1][0].load();
                            if(allPlatesActivated()) {
                                Gdx.app.postRunnable(() -> door.open());

                                Timer.schedule(new Timer.Task(){

                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);
                            }
                        } else if(fixtureIs("Plate11")) {
                            plates[1][1].load();
                            if(allPlatesActivated()) {
                                Gdx.app.postRunnable(() -> door.open());

                                Timer.schedule(new Timer.Task(){

                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);
                            }
                            if(!plates[0][0].isActivated()) {
                                Gdx.app.postRunnable(() -> door1.open());
                            }
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if( fixturesNotNull() && fixtureIs("Player")){

                    // Kollision mit Hebel abfragen
                    if(fixtureIs("Lever1")) {
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

                if (keycode == Input.Keys.E || keycode == Input.Keys.SPACE){

                    if(lever1.collidesWithPlayer()){
                        lever1.use();
                        for (int i = 0; i< plates.length; i++){
                            for (int j = 0; j < plates[i].length; j++ ){
                                plates[i][j].unload();
                            }
                        }
                        door.close();
                    }

                }

                return false;
            }

        };
    }

    @Override
    public void drawObjects(Batch batch) {

        lever1.draw(batch);
        door1.draw(batch);
        door2.draw(batch);
        for (int i = 0; i< plates.length; i++){
            for (int j = 0; j < plates[i].length; j++ ){

                plates[i][j].draw(batch);
            }
        }

        fontL.draw(batch, puzzleTextL, worldmap.getMapRight()- 630, worldmap.getMapHeight() - 45);
        fontR.draw(batch, puzzleTextR, worldmap.getMapRight()- 200, worldmap.getMapHeight() - 40);

        font.draw(batch, puzzleTextDoor, 145, worldmap.getMapHeight() - 430);
        font.draw(batch, "[0][0]", 145, worldmap.getMapHeight() - 390);
        font.draw(batch, "[0][1]", 145, worldmap.getMapHeight() - 165);
        font.draw(batch, "[1][0]", 465, worldmap.getMapHeight() - 390);
        font.draw(batch, "[1][1]", 465, worldmap.getMapHeight() - 165);
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {

        door.draw(batch);
    }
}
