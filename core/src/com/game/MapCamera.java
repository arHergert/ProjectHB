package com.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;


public class MapCamera extends OrthographicCamera {
    //IV
    private Vector3 lastPosition;
    private BoundingBox left, right, top, bottom = null;

    //Constructor
    public MapCamera() {}

    public MapCamera(LevelMap levelmap){

        this.setToOrtho(false, levelmap.getMapHeight() * 1.7f, ((levelmap.getMapHeight()/16)*9) * 1.7f );
        this.position.set ( levelmap.getMapWidth()/2,  levelmap.getMapHeight()/2, 0);
        this.setWorldBounds(levelmap.getMapLeft(), levelmap.getMapBottom(), levelmap.getMapWidth(), levelmap.getMapHeight());
    }


    public void setWorldBounds(int left, int bottom, int width, int height) {
        int top = bottom + height;
        int right = left + width;

        this.left = new BoundingBox(new Vector3(left - 2, 0, 0), new Vector3(left -1, top, 0));
        this.right = new BoundingBox(new Vector3(right + 1, 0, 0), new Vector3(right + 2, top, 0));
        this.top = new BoundingBox(new Vector3(0, top + 1, 0), new Vector3(right, top + 2, 0));
        this.bottom = new BoundingBox(new Vector3(0, bottom - 1, 0), new Vector3(right, bottom - 2, 0));
    }


    @Override
    public void translate(float x, float y) {
        lastPosition = new Vector3(position);
        super.translate(x, y);
    }

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