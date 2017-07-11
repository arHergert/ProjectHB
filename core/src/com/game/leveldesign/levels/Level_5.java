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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Timer;
import com.game.box2d.mapobjects.*;

import static com.game.Main.assetManager;

/**
 * Created by Max on 02.07.2017.
 */
public class Level_5 extends Level {

    //IV
    private Plate[][] plates1;
    private Plate[][] plates2;
    private Door door1;
    //private Door door2;
    private BitmapFont font8;
    private BitmapFont font9;
    private BitmapFont font10;
    private Button lever1 = new Button(worldmap, "Lever1");
    private Button button1 = new Button(worldmap, "Button1");

    private Collectibles coll1, coll2;
    private HiddenDoor hiddenDoor;

    private String puzzleText1 =
            "if(a[0][0]) {\n" +
            "   door.open();\n" +
            "}\n";
    private String puzzleText2 =
            "for (int i = 0; i< b.length; i++){\n"+
            "    for (int j = 0; j < b[i].length; j++ ){\n"+
            "        b[i][j] = !a[i][j];\n"+
            "    }\n"+
            "}";
    private String puzzleText3 =
            "if(a[1][0] & b[0][0]) {\n" +
            "   door.open();\n" +
            "}\n";

    public Level_5() {
        super("level5_map.tmx");
        door1 = new Door(worldmap, "Door1");
        //door2 = new Door(worldmap, "Door2");
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
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.color = Color.valueOf("43435d");
        fontParameter.size = 8;
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("CodeNewRoman.otf"));
        fontGenerator.scaleForPixelHeight(9);
        fontParameter.minFilter = Texture.TextureFilter.Nearest;
        fontParameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font8 = fontGenerator.generateFont(fontParameter);
        fontParameter.size = 9;
        font9 = fontGenerator.generateFont(fontParameter);
        fontParameter.size = 10;
        font10 = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();

        coll1 = new Collectibles(worldmap, "Coll1");
        coll2 = new Collectibles(worldmap, "Coll2");
        hiddenDoor = new HiddenDoor(worldmap,"HiddenDoor", "Hidden");
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

                    //System.out.println("BEGIN    FIX A: " + fixA.getUserData() + "  FIX B: " +fixB.getUserData());

                    //Kollisionsabfragen für die Türen
                    checkDoorCollisions(fixA, fixB);

                    // Kollision mit Hebel abfragen
                    if (fixtureIs("Lever1")) {
                        lever1.collideOn();

                    }
                    if(fixtureIs("Button1")) {
                        button1.collideOn();

                    }



                    if(fixtureIs("Player") || fixtureIs("Player_feet")){
                        if(fixtureStartsWith("Coll")){

                            if(fixtureIs("Coll1")){

                                Gdx.app.postRunnable(() -> coll1.collect());

                            }else if (fixtureIs("Coll2")){
                                Gdx.app.postRunnable(() -> coll2.collect());

                            }

                            increaseGarneredCollectiblesCount();
                        }


                        if(fixtureIs("HiddenDoor")){
                            hiddenDoor.collideOn();
                        }

                    }


                    //Restliches Zeug
                    if (fixtureIs("Player_feet")) {

                        if (fixtureIs("Plate000")) {
                            plates1[0][0].load();
                            Gdx.app.postRunnable(() -> door1.open());

                        } else if (fixtureIs("Plate001")) {
                            plates1[0][1].load();

                        } else if (fixtureIs("Plate010")) {
                            plates1[1][0].load();
                            if(plates2[0][0].isActivated()) {
                                Gdx.app.postRunnable(() -> door.open());

                               if(!door.isOpen()){
                                   Timer.schedule(new Timer.Task(){

                                       public void run() {
                                           assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                       }
                                   }, 0.8f);
                               }


                            }

                        } else if (fixtureIs("Plate011")) {
                            plates1[1][1].load();

                        } else if (fixtureIs("Plate100")) {
                            plates2[0][0].load();
                            if(plates1[1][0].isActivated()) {
                                Gdx.app.postRunnable(() -> door.open());

                                if(!door.isOpen()){
                                    Timer.schedule(new Timer.Task(){

                                        public void run() {
                                            assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                        }
                                    }, 0.8f);
                                }


                            }

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
                    if (fixtureIs("Button1")) {
                        button1.collideOff();

                    }

                    if(fixtureIs("Player") || fixtureIs("Player_feet")){

                        if(fixtureIs("HiddenDoor")){
                            hiddenDoor.collideOff();
                        }

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

                if (keycode == Input.Keys.E || keycode == Input.Keys.SPACE ){

                    if(hiddenDoor.collidesWithPlayer()){
                        hiddenDoor.openPath();
                    }

                    if(lever1.collidesWithPlayer()){
                        lever1.use();
                        for (int i = 0; i< plates1.length; i++){
                            for (int j = 0; j < plates1[i].length; j++ ){
                                if(!plates1[i][j].isActivated()) {
                                    plates2[i][j].load();
                                } else {
                                    plates2[i][j].unload();
                                    assetManager.get("sounds/plate_unload.wav", Sound.class).play();
                                }
                            }
                        }
                        if(plates1[1][0].isActivated() && plates2[0][0].isActivated()) {
                            door.open();

                        }
                    }
                    if(button1.collidesWithPlayer()){
                        button1.use();
                        for (int i = 0; i< plates1.length; i++){
                            for (int j = 0; j < plates1[i].length; j++ ){
                                plates1[i][j].unload();
                                assetManager.get("sounds/plate_unload.wav", Sound.class).play();
                            }
                        }
                    }

                }

                if(keycode == Input.Keys.Z){

                    door.open();
                }

                return false;
            }

        };
    }

    @Override
    public void drawObjects(Batch batch) {
        for (int i = 0; i < plates1.length; i++) {
            for (int j = 0; j < plates1[i].length; j++) {

                plates1[i][j].draw(batch);
                plates2[i][j].draw(batch);
            }
        }
        font8.draw(batch, puzzleText1, 514, worldmap.getMapHeight() - 215);
        font10.draw(batch, puzzleText2, 610, worldmap.getMapHeight() - 102);
        font8.draw(batch, puzzleText3, 17, worldmap.getMapHeight() - 85);
        font9.draw(batch, "[0][0]", 193, worldmap.getMapHeight() - 228);
        font9.draw(batch, "[0][1]", 193, worldmap.getMapHeight() - 228+80);
        font9.draw(batch, "[1][0]", 193+96, worldmap.getMapHeight() - 228);
        font9.draw(batch, "[1][1]", 193+96, worldmap.getMapHeight() - 228+80);
        button1.draw(batch);
        lever1.draw(batch);

        coll1.draw(batch);
        coll2.draw(batch);
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {
        door1.draw(batch);
        door.draw(batch);

    }
}

