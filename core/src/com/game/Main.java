package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.game.box2d.Player;
import com.game.input.KeyboardInput;
import com.game.leveldesign.LevelManager;

/**
 * Hauptklasse zur Erstellung, Aktualisierung und Verwaltung der Spiellogik und -objekte
 */
public class Main extends ApplicationAdapter {

    //IV
    /** Initialisierung der benötigten Spielobjekte **/
    private SpriteBatch batch;
    private MapCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private KeyboardInput keyboardInput;
    private LevelManager levels;
    private Player boxPlayer;
    private Box2DDebugRenderer debugRenderer;

    //IM

    /**
     * Erstellt und definiert alle notwendigen Objekte, die zum Start
     * des Spiels verfügbar sein müssen.
     *
     * Die create()-Methode wird nur einmal zum Start ausgeführt.
     */
    public void create () {

        //Initialisierung des Spritebatches. Damit können Grafiken gezeichnet werden.
        batch = new SpriteBatch();

        levels = new LevelManager();

        //Aktuelles Level mit MapRenderer festlegen
        tiledMapRenderer = new OrthogonalTiledMapRenderer(levels.getCurrentlevel().getMap());

        //Kamera auf aktuelles Level setzen
        camera = new MapCamera(levels.getCurrentlevel());
        camera.update();

        //Neuer Renderer, der die Kollisionsboxen im Spiel erkenntlich macht
        debugRenderer = new Box2DDebugRenderer();
        boxPlayer = new Player(levels.getCurrentlevel(), "caveman.png", "spawnStart");

        //Definieren des Spielerinputs.
        keyboardInput = new KeyboardInput();
        Gdx.input.setInputProcessor(keyboardInput);


    }


    /**
     * Aktualisierung des Spiels pro Frame.
     *
     * Hier wird die komplette Spiellogik verwaltet.
     */
	public void render () {

        //Rendert mithilfe von OpenGL den Hintergrund Schwarz (0,0,0,1), wenn sich an einer Stelle keine Textur befindet
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Prüft ob der der Spieler in ein anderes Level gehen will und wechselt entsprechend
        if(levels.isPlayerSwitchingLevel()){

            levels.getCurrentlevel().getWorld().destroyBody(boxPlayer.body);

            //In das neue Level wechseln
            levels.switchLevel();
            tiledMapRenderer = new OrthogonalTiledMapRenderer(levels.getCurrentlevel().getMap());
            camera = new MapCamera(levels.getCurrentlevel());
            camera.update();
            boxPlayer = new Player(levels.getCurrentlevel(), "caveman.png", levels.getSpawnpoint());
        }

        //Spielerbewegung
        keyboardInput.updatePlayerMovement(boxPlayer,camera, levels.getCurrentlevel());

        //MapRenderer auf die Position der Kamera verweisen
        tiledMapRenderer.setView(camera);
        camera.update();

        //Grundebene des aktuellen Levels rendern
        tiledMapRenderer.render(new int[]{0});

        //Worldaktualisierung. Welt aktualisiert sich 60 mal pro Sekunde.
        levels.getCurrentlevel().getWorld().step(1/60f, 6,2);

        //Aktualisieren des DebugRenderers
        debugRenderer.render(levels.getCurrentlevel().getWorld(), camera.combined);

        //Das batch zeichnet/aktualisiert die Positionen der Graphiken
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        boxPlayer.draw(batch); //Position der Playergrafik aktualisieren
        batch.end();

        //Restliche Ebenen, nach der Grundebene, rendern
        for(int i = 1; i < levels.getCurrentlevel().getMap().getLayers().getCount(); i++){
            tiledMapRenderer.render( new int[]{i});
        }


	}

    /**
     * Lässt angegebene Ressourcen die im Spiel erzeugt wurden wieder
     * frei und leert den Speicher.
     *
     * UNBEDINGT ALLES, was die {@link Main#dispose()} Methode ausführen kann, hier rein tun
     */
	public void dispose () {
        //TODO Methode zum Disposen aller geladenen Level
        debugRenderer.dispose();
        boxPlayer.getImg().dispose();

	}


}//end class Main
