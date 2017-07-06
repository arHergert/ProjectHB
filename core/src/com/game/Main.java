package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.game.box2d.Player;
import com.game.input.PlayerMovementInputProcessor;
import com.game.leveldesign.LevelManager;

/**
 * Hauptklasse zur Erstellung, Aktualisierung und Verwaltung der Spiellogik und -objekte
 */
public class Main extends ApplicationAdapter {

    //IV
    /** Initialisierung der benötigten Spielobjekte **/
    public static TextureAtlas spritesheet;
    public static AssetManager assetManager;
    private SpriteBatch batch;
    private MapCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private PlayerMovementInputProcessor playermoveInput;
    private LevelManager levels;
    private Player boxPlayer;
    private Box2DDebugRenderer debugRenderer;
    private InputMultiplexer multiplexer;

    //IM

    /**
     * Erstellt und definiert alle notwendigen Objekte, die zum Start
     * des Spiels verfügbar sein müssen.
     *
     * Die create()-Methode wird nur einmal zum Start ausgeführt.
     */
    public void create () {

        spritesheet = new TextureAtlas("spritesheet/tryandcatchpack.atlas");

        assetManager = new AssetManager();
        assetManager.load("sounds/wall_close.wav", Sound.class);
        assetManager.load("sounds/wall_open.wav", Sound.class);
        assetManager.load("sounds/button.wav", Sound.class);
        assetManager.load("sounds/puzzleSolved.mp3", Sound.class);
        assetManager.finishLoading();

        //Initialisierung des Spritebatches. Damit können Grafiken gezeichnet werden.
        batch = new SpriteBatch();

        levels = new LevelManager();

        //Aktuelles Level mit MapRenderer festlegen
        tiledMapRenderer = new OrthogonalTiledMapRenderer(levels.getCurrentWorldMap().getMap());

        //Kamera auf aktuelles Level setzen
        camera = new MapCamera(levels.getCurrentWorldMap());
        camera.update();

        //Neuer Renderer, der die Kollisionsboxen im Spiel erkenntlich macht
        debugRenderer = new Box2DDebugRenderer();
        boxPlayer = new Player(levels.getCurrentWorldMap(), "spawnStart");

        //Definieren des Spielerinputs.
        multiplexer = new InputMultiplexer(); //Enthält alle InputProcessor des laufenden Spiels
        playermoveInput = new PlayerMovementInputProcessor(); //Enthält PlayerMovementInputProcessor für die Spielerbewegung

        //InputProcessor für Playerbewegung und Levellogik des Startlevels an den Multiplexer hängen
        multiplexer.addProcessor(playermoveInput);
        multiplexer.addProcessor(levels.getCurrentlevelInputLogic());
        Gdx.input.setInputProcessor(multiplexer);


    }


    /**
     * Aktualisierung des Spiels pro Frame.
     *
     * Hier wird die komplette Spiellogik verwaltet.
     */
	public void render () {

        //Rendert mithilfe von OpenGL den Hintergrund Schwarz (0,0,0,1) -> (R,G,B,A), wenn sich an einer Stelle keine Textur befindet
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Prüft ob der der Spieler in ein anderes Level gehen will und wechselt entsprechend
        if(levels.isPlayerSwitchingLevel()){

            //Alten BOX2D Körper und InputProcessor des aktuellen Levels entsorgen
            levels.getCurrentWorldMap().getWorld().destroyBody(boxPlayer.playerBody);
            multiplexer.removeProcessor(levels.getCurrentlevelInputLogic());

            //TODO Methode zum Disposen des aktuellen Levels, ohne dabei den ArrayList Eintrag zu löschen

            //In das neue Level wechseln
            levels.switchLevel();

            //Kamera, Renderer, Player etc. auf das neue aktuelle Level verweisen
            multiplexer.addProcessor(levels.getCurrentlevelInputLogic());
            tiledMapRenderer = new OrthogonalTiledMapRenderer(levels.getCurrentWorldMap().getMap());
            camera = new MapCamera(levels.getCurrentWorldMap());
            camera.update();
            boxPlayer = new Player(levels.getCurrentWorldMap(), levels.getSpawnpoint());


        }

        //Spielerbewegung erfassen im PlayerMovementInputProcessor
        playermoveInput.updatePlayerMovement(boxPlayer,camera, levels.getCurrentWorldMap());

        //MapRenderer auf die Position der Kamera verweisen
        tiledMapRenderer.setView(camera);
        camera.update();

        //Grundebene des aktuellen Levels rendern
        tiledMapRenderer.render(new int[]{0,1,2});


        //Worldaktualisierung. Welt aktualisiert sich 60 mal pro Sekunde.
        levels.getCurrentWorldMap().getWorld().step(1/60f, 6,2);

        //Aktualisieren des DebugRenderers
        debugRenderer.render(levels.getCurrentWorldMap().getWorld(), camera.combined);

        //Das batch zeichnet/aktualisiert die Positionen der Grafiken
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        levels.getCurrentlevel().drawObjects(batch);
        boxPlayer.draw(batch); //Position der Playergrafik aktualisieren
        levels.getCurrentlevel().drawObjectsOverPlayer(batch);
        batch.end();

        //Restliche Ebenen, nach der Grundebene, rendern
        for(int i = 3; i < levels.getCurrentWorldMap().getMap().getLayers().getCount(); i++){
            tiledMapRenderer.render( new int[]{i});
        }


	}

    /**
     * Lässt angegebene Ressourcen die im Spiel erzeugt wurden wieder
     * frei und leert den Speicher.
     *
     * UNBEDINGT ALLES, was die .dispose() Methode ausführen kann, hier rein tun
     */
	public void dispose () {
        debugRenderer.dispose();
        levels.disposeAllLevels();
        spritesheet.dispose();
        assetManager.dispose();

	}


}//end class Main
