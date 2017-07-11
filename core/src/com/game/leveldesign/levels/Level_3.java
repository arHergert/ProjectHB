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
import com.game.box2d.mapobjects.Collectibles;
import com.game.box2d.mapobjects.Plate;

import static com.game.Main.assetManager;

/**
 * Created by Max on 19.06.2017.
 */
public class Level_3 extends Level {

    //IV
    private Plate[][] plates;

    private BitmapFont font;

    private Collectibles coll1 = new Collectibles(worldmap,"Coll1");

    private String puzzleText = "for (int j=0; j <= 1; j++){\n" +
            "   for (int i=0; i < 2; i++{\n" +
            "       array[j][i] = true;\n" +
            "   }\n" +
            "}\n";

    /**
     * @param
     */
    public Level_3() {
        super("level3_map.tmx");
        plates = new Plate[4][3];
        plates[0][0] = new Plate(worldmap, true, "Plate00");
        plates[0][1] = new Plate(worldmap, true, "Plate01");
        plates[0][2] = new Plate(worldmap, true, "Plate02");
        plates[1][0] = new Plate(worldmap, true, "Plate10");
        plates[1][1] = new Plate(worldmap, true, "Plate11");
        plates[1][2] = new Plate(worldmap, true, "Plate12");
        plates[2][0] = new Plate(worldmap, true, "Plate20");
        plates[2][1] = new Plate(worldmap, true, "Plate21");
        plates[2][2] = new Plate(worldmap, true, "Plate22");
        plates[3][0] = new Plate(worldmap, true, "Plate30");
        plates[3][1] = new Plate(worldmap, true, "Plate31");
        plates[3][2] = new Plate(worldmap, true, "Plate32");

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.color = Color.valueOf("43435d");
        fontParameter.size = 9;
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("CodeNewRoman.otf"));
        fontGenerator.scaleForPixelHeight(9);
        fontParameter.minFilter = Texture.TextureFilter.Nearest;
        fontParameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();
    }

    private void resetPlates() {
        for(int i=0; i<plates.length; i++) {
            for(int j=0; j<plates[i].length; j++) {
                plates[i][j].reset();
            }
        }
        Gdx.app.postRunnable(() -> door.close());
    }

    @Override
    protected ContactListener levelContact() {
        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if( fixturesNotNull()){

                    //Kollisionsabfragen für die Türen
                    checkDoorCollisions(fixA,fixB);

                    //Restliches Zeug

                    if(fixtureIs("Player") || fixtureIs("Player_feet")){
                        if(fixtureStartsWith("Coll")){

                            if(fixtureIs("Coll1")){

                                Gdx.app.postRunnable(() -> coll1.collect());

                            }

                            increaseGarneredCollectiblesCount();
                        }

                    }



                    /**
                     * Da nur Player_feet die Druckplatten auslösen dürfen, muss
                     * zuerst abgefragt werden ob bei einer Kollision
                     * mit den Druckplatten kein anderes Objekt mit denen kollidiert
                     */

                    if(fixA.getUserData().equals("Player_feet") || fixB.getUserData().equals("Player_feet")){

                        if (fixA.getUserData().equals("Plate00") || fixB.getUserData().equals("Plate00")){
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate00" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            plates[0][0].load();
                        } else if (fixA.getUserData().equals("Plate01") || fixB.getUserData().equals("Plate01")){
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate01" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            if(plates[0][0].isActivated()) {
                                plates[0][1].load();
                            } else {
                                resetPlates();
                            }
                        } else if (fixA.getUserData().equals("Plate02") || fixB.getUserData().equals("Plate02")){
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate02" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            resetPlates();
                        } else if (fixA.getUserData().equals("Plate10") || fixB.getUserData().equals("Plate10")){
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate10" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            if(plates[0][1].isActivated()) {
                                plates[1][0].load();
                            } else {
                                resetPlates();
                            }
                        } else if (fixA.getUserData().equals("Plate11") || fixB.getUserData().equals("Plate11")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate11" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            if(plates[1][0].isActivated()) {
                                plates[1][1].load();
                                Gdx.app.postRunnable(() -> door.open());

                                Timer.schedule(new Timer.Task(){


                                    public void run() {
                                        assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                    }
                                }, 0.8f);


                            } else {
                                resetPlates();
                            }
                        } else if (fixA.getUserData().equals("Plate12") || fixB.getUserData().equals("Plate12")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate12" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            resetPlates();
                        } else if (fixA.getUserData().equals("Plate20") || fixB.getUserData().equals("Plate20")){
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate20" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            resetPlates();
                        } else if (fixA.getUserData().equals("Plate21") || fixB.getUserData().equals("Plate21")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate21" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            resetPlates();
                        } else if (fixA.getUserData().equals("Plate22") || fixB.getUserData().equals("Plate22")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate22" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            resetPlates();
                        } else if (fixA.getUserData().equals("Plate30") || fixB.getUserData().equals("Plate30")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate30" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            resetPlates();
                        } else if (fixA.getUserData().equals("Plate31") || fixB.getUserData().equals("Plate31")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate31" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            resetPlates();
                        } else if (fixA.getUserData().equals("Plate32") || fixB.getUserData().equals("Plate32")) {
                            Fixture doorBottomFixture = fixA.getUserData() == "Plate22" ? fixA : fixB;
                            Fixture player = doorBottomFixture == fixA ? fixB : fixA;
                            resetPlates();
                        }

                    }



                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert
            }

            @Override
            public void endContact(Contact contact) {

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
        for (int i = 0; i< plates.length; i++){
            for (int j = 0; j < plates[i].length; j++ ){

                plates[i][j].draw(batch);
            }
        }

        font.draw(batch, puzzleText, worldmap.getMapRight()- 230, worldmap.getMapHeight() - 40);
        font.draw(batch, "i", 80, worldmap.getMapHeight()-140);
        font.draw(batch, "j", 370, worldmap.getMapHeight()-300);

        coll1.draw(batch);
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {
        door.draw(batch);
    }

}
