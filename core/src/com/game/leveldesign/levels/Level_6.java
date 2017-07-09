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
import com.game.box2d.mapobjects.Button;
import com.game.box2d.mapobjects.Door;
import com.game.box2d.mapobjects.Lever;

import static com.game.Main.assetManager;

/**
 * Created by Max on 08.07.2017.
 */
public class Level_6 extends Level {

    //IV
    private Lever lever1 = new Lever(worldmap,"Lever1", "Boden");
    private Lever lever2 = new Lever(worldmap,"Lever2", "Boden");
    private Door door0 = new Door(worldmap, "Door0");
    private Door door1 = new Door(worldmap, "Door1");
    private Door door2 = new Door(worldmap, "Door2");
    private Door door3 = new Door(worldmap, "Door3");
    private Button button1 = new Button(worldmap, "Button1");
    private Button button2 = new Button(worldmap, "Button2");
    private Button button3 = new Button(worldmap, "Button3");
    private String solution = "748";
    private String s1 = "i1++;\n" +
            "if(i1>9) i1=0;";
    private String s2 = "i2 = i2*2;\n" +
            "if(i2>9) i2=1;";
    private String s3 = "int temp = i3;\n" +
            "i3 = i3 + i3alt;\n" +
            "i3alt = temp;\n" +
            "if(i3>9) i3=i3alt=1;";
    private int i1 = 1;
    private int i2 = 1;
    private int i3 = 1;
    private int i3alt = 1;
    private BitmapFont font14;
    private boolean  playerOverLever = true;

    public Level_6() {
        super("level6_map.tmx");
        door0.openWithoutSound();
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.color = Color.valueOf("43435d");
        fontParameter.size = 12;
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("CodeNewRoman.otf"));
        fontGenerator.scaleForPixelHeight(9);
        fontParameter.minFilter = Texture.TextureFilter.Nearest;
        fontParameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font14 = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();
    }

    @Override
    protected ContactListener levelContact() {
        return new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if( fixturesNotNull() && (fixtureIs("Player") || fixtureIs("Player_feet")) ){

                    checkDoorCollisions(fixA,fixB);

                    // Kollision mit Hebel abfragen
                    if(fixtureIs("Lever1")) {
                        lever1.collideOn();
                    } else if(fixtureIs("Lever2")) {
                        lever2.collideOn();
                    } else if(fixtureIs("Button1")) {
                        button1.collideOn();
                    } else if(fixtureIs("Button2")) {
                        button2.collideOn();
                    } else if(fixtureIs("Button3")) {
                        button3.collideOn();
                    }



                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert


                //Wenn der Spieler mit seinen Füßen über den Steinen geht, wird die Variable akiviert
                if(fixturesNotNull() && fixtureIs("Player_feet")){

                    if(fixA.getUserData().toString().startsWith("Lever") || fixB.getUserData().toString().startsWith("Lever")){

                        playerOverLever = false;

                    }
                }


            }

            public void endContact(Contact contact) {
                fixA = contact.getFixtureA();
                fixB = contact.getFixtureB();

                //Überprüfen ob Player nicht mit nicht-interagierbaren Objekten wie Wände o.ä. kollidiert
                if( fixturesNotNull() && (fixtureIs("Player") || fixtureIs("Player_feet") )){

                    // Kollision mit Hebel abfragen
                    if(fixtureIs("Lever1")) {
                        lever1.collideOff();
                    } else if(fixtureIs("Lever2")) {
                        lever2.collideOff();
                    } else if(fixtureIs("Button1")) {
                        button1.collideOff();
                    } else if(fixtureIs("Button2")) {
                        button2.collideOff();
                    } else if(fixtureIs("Button3")) {
                        button3.collideOff();
                    }




                }//end if-Abfage ob Player nicht mit StaticMapCollisions-Objekten kollidiert

                if(fixturesNotNull() && fixtureIs("Player_feet")){

                    if(fixA.getUserData().toString().startsWith("Lever") || fixB.getUserData().toString().startsWith("Lever")){

                        playerOverLever = true;

                    }
                }


            }
            public void preSolve(Contact contact, Manifold oldManifold) {}
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        };
    }

    @Override
    public InputProcessor levelInput() {
        return new InputAdapter(){

            public boolean keyDown(int keycode) {

                if (keycode == Input.Keys.E || keycode == Input.Keys.SPACE ){

                    if(lever1.collidesWithPlayer()){
                        lever1.use();
                        updateDoors();
                    } else if(lever2.collidesWithPlayer()){
                        lever2.use();
                        updateDoors();
                    } else if(button1.collidesWithPlayer()) {
                        i1++;
                        if(i1>9) i1=0;
                        if(i1==7 && i2==4 && i3==8) {
                            door.open();
                            Timer.schedule(new Timer.Task(){

                                public void run() {
                                    assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                }
                            }, 0.8f);
                        }
                    } else if(button2.collidesWithPlayer()) {
                        i2 = i2*2;
                        if(i2>9) i2=1;
                        if(i1==7 && i2==4 && i3==8) {
                            door.open();
                            Timer.schedule(new Timer.Task(){

                                public void run() {
                                    assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                }
                            }, 0.8f);
                        }
                    } else if(button3.collidesWithPlayer()) {
                        int temp = i3;
                        i3 = i3 + i3alt;
                        i3alt = temp;
                        if(i3>9) i3=i3alt=1;
                        if(i1==7 && i2==4 && i3==8) {
                            door.open();
                            Timer.schedule(new Timer.Task(){

                                public void run() {
                                    assetManager.get("sounds/puzzleSolved.mp3", Sound.class).play();
                                }
                            }, 0.8f);
                        }
                    }

                }

                return false;
            }

        };
    }

    private void updateDoors() {
        door0.close();
        door1.close();
        door2.close();
        door3.close();
        if(!lever1.isActivated() && !lever2.isActivated()) {
            door0.open();
        } else if(lever1.isActivated() && !lever2.isActivated()) {
            door1.open();
        } else if(!lever1.isActivated() && lever2.isActivated()) {
            door2.open();
        } else if(lever1.isActivated() && lever2.isActivated()) {
            door3.open();
        }
    }

    @Override
    public void drawObjects(Batch batch) {
        if( playerOverLever){
            lever1.draw(batch);
            lever2.draw(batch);
        }
        door0.draw(batch);
        door1.draw(batch);
        door2.draw(batch);
        door3.draw(batch);
        button1.draw(batch);
        button2.draw(batch);
        button3.draw(batch);
        font14.draw(batch, solution, 660, worldmap.getMapHeight() - 315);
        font14.draw(batch, "i1 = "+(new Integer(i1)).toString(), 330, worldmap.getMapHeight() - 185);
        font14.draw(batch, "i2 = "+(new Integer(i2)).toString(), 555, worldmap.getMapHeight() - 185);
        font14.draw(batch, "i3 = "+(new Integer(i3)).toString(), 780, worldmap.getMapHeight() - 185);
        font14.draw(batch, s1, 288, worldmap.getMapHeight() - 52);
        font14.draw(batch, s2, 512, worldmap.getMapHeight() - 52);
        font14.draw(batch, s3, 722, worldmap.getMapHeight() - 38);
    }

    @Override
    public void drawObjectsOverPlayer(Batch batch) {
        if( !playerOverLever){
            lever1.draw(batch);
            lever2.draw(batch);
        }

        door.draw(batch);
    }
}
