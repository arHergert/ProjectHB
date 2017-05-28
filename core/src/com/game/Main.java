package com.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.Player;
import com.game.box2d.mapobjects.Sensor;
import com.game.box2d.mapobjects.StaticMapCollisions;
import com.game.box2d.WorldContactListener;
import com.game.input.KeyboardInput;

/**
 * Hauptklasse zur Erstellung, Rendern und Verwalten der Spielobjekte und -logik
 */
public class Main extends ApplicationAdapter {

    //IV
    private SpriteBatch batch;
    private MapCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private KeyboardInput keyboardInput;
    private LevelMap level1;
    private LevelMap level2;
    private LevelMap currentlevel;
    private World level1_world;
    private World level2_world;
    private World currentworld;
    private Player boxPlayer;
    private Box2DDebugRenderer debugRenderer;
    private StaticMapCollisions mapCollisions_level1 = new StaticMapCollisions();
    private StaticMapCollisions mapCollisions_level2 = new StaticMapCollisions();
    private Sensor doorUp;
    private Sensor doorBottom;

    //IM

    /**
     * Erstellt und definiert alle notwendigen Objekte, die zum Start
     * des Spiels verfügbar sein müssen.
     *
     * Die create()-Methode wird nur einmal zum Start ausgeführt.
     */
    public void create () {

        batch = new SpriteBatch();

        level1 = new LevelMap("tilemapCave.tmx");
        level2 = new LevelMap("tilemapCave2.tmx");

        level1_world = new World(new Vector2(0, 0),false);
        level2_world = new World(new Vector2(0, 0),false);

        mapCollisions_level1.createObjects(level1_world,level1);
        doorUp = new Sensor(level1_world, level1, "doorUp" );

        mapCollisions_level2.createObjects(level2_world, level2 );
        doorBottom = new Sensor(level2_world, level2, "doorBottom");

        level1_world.setContactListener( new WorldContactListener(doorUp, doorBottom));
        level2_world.setContactListener( new WorldContactListener(doorUp, doorBottom));

        currentlevel = level1;
        currentworld = level1_world;

        tiledMapRenderer = new OrthogonalTiledMapRenderer(currentlevel.getMap());

        camera = new MapCamera(currentlevel);
        camera.update();


        debugRenderer = new Box2DDebugRenderer();
        boxPlayer = new Player(currentworld, currentlevel, "caveman.png", "spawnStart");


        keyboardInput = new KeyboardInput();
        Gdx.input.setInputProcessor(keyboardInput);


    }


    /**
     * Aktualisierung des Spiels pro Frame.
     *
     * Hier wird die komplette Spiellogik verwaltet.
     */
	public void render () {

        //Rendert mithilfe von OpenGL den
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(doorUp.isActivated()){

            doorUp.deactivate();
            currentworld.destroyBody(boxPlayer.body);
            currentworld = level2_world;
            currentlevel = level2;
            updateLevel("spawnBottomDoor");

        }


        if (doorBottom.isActivated()){

            doorBottom.deactivate();
            currentworld.destroyBody(boxPlayer.body);
            currentworld = level1_world;
            currentlevel = level1;
            updateLevel("spawnUpperDoor");
        }


        keyboardInput.updatePlayerMovement(boxPlayer,camera, currentlevel);
        tiledMapRenderer.setView(camera);
        camera.update();
        tiledMapRenderer.render(new int[]{0});

        //Box2d
        currentworld.step(1/60f, 6,2);

        debugRenderer.render(currentworld, camera.combined);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        boxPlayer.draw(batch);
        batch.end();

        if( currentlevel == level2){
            tiledMapRenderer.render(new int[]{4});
        }



	}


	public void dispose () {
        currentlevel.getMap().dispose();
        level2.getMap().dispose();
        level1.getMap().dispose();
        level1_world.dispose();
        debugRenderer.dispose();
        boxPlayer.getImg().dispose();

	}



    public void updateLevel(String spawn){

        tiledMapRenderer = new OrthogonalTiledMapRenderer(currentlevel.getMap());
        camera = new MapCamera(currentlevel);
        camera.update();

        boxPlayer = new Player(currentworld, currentlevel, "caveman.png", spawn);


    }

}//end class Main
