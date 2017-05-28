package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.Main;

/**
 * Startklasse zum Ausführen der Applikation
 */
public class DesktopLauncher {

    /**
     * Definiert die Fensterkonfigurationen (Titel, Höhe, Breite etc.) und
     * startet die Applikation über die {@link Main} - Klasse
     * @param arg
     */
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Project HB v0.0.1";
        config.width = 1280;
        config.height = 720;
        config.resizable = true;

        new LwjglApplication(new Main(), config);

    }
}
