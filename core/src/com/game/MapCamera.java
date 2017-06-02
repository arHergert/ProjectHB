package com.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Erweiterung der {@link OrthographicCamera} um zusätzliche Funktionen.
 * Diese sind:<br>
 *     - Platzierung der Kamera auf die Position der Levelmap
 *     - Bewegung der Kamera nur innerhalb der Levelmap-Grenzen
 *     - Dynamische Kameraviewgröße anhand der Mapgröße
 */
public class MapCamera extends OrthographicCamera {
    //IV
    private Vector3 lastPosition;
    private BoundingBox left, right, top, bottom = null;

    //Constructor
    public MapCamera() {}

    /**
     * Setzt die Kamera an die Position der Map
     *
     * @param levelmap Aktuelles Level
     */
    public MapCamera(WorldMap levelmap){

        this.setToOrtho(false, levelmap.getMapHeight() * 1.7f, ((levelmap.getMapHeight()/16)*9) * 1.7f );
        this.position.set ( levelmap.getMapWidth()/2,  levelmap.getMapHeight()/2, 0);
        this.setWorldBounds(levelmap.getMapLeft(), levelmap.getMapBottom(), levelmap.getMapWidth(), levelmap.getMapHeight());
    }

    /**
     * Definiert die Mapgrenzen, in denen sich die Kamera bewegen darf
     *
     * @param left  Linke seite der Map
     * @param bottom Untere seite der Map
     * @param width Breite der Map
     * @param height Höhe der Map
     */
    public void setWorldBounds(int left, int bottom, int width, int height) {
        int top = bottom + height;
        int right = left + width;

        this.left = new BoundingBox(new Vector3(left - 2, 0, 0), new Vector3(left -1, top, 0));
        this.right = new BoundingBox(new Vector3(right + 1, 0, 0), new Vector3(right + 2, top, 0));
        this.top = new BoundingBox(new Vector3(0, top + 1, 0), new Vector3(right, top + 2, 0));
        this.bottom = new BoundingBox(new Vector3(0, bottom - 1, 0), new Vector3(right, bottom - 2, 0));
    }


    @Override
    /**
     * Bewegen der Kamera.
     * <b>WARNUNG: Beachtet nicht die Grenzen des Levels</b>
     */
    public void translate(float x, float y) {
        lastPosition = new Vector3(position);
        super.translate(x, y);
    }

    /**
     * Bewegt die Kamera in eine Richtung und
     * beachtet dabei die Mapgrenzen
     * @param x X-Richtung
     * @param y Y-Richtung
     */
    public void translateInBounds(float x, float y) {
        translate(x, y);
        update();
        ensureBounds();
        update();
    }

    private void ensureBounds() {
        if(isInsideBounds())
        {
            position.set(lastPosition);
        }
    }

    private boolean isInsideBounds() {
        return (frustum.boundsInFrustum(left) || frustum.boundsInFrustum(right) || frustum.boundsInFrustum(top) || frustum.boundsInFrustum(bottom));
    }


}//end class MapCamera