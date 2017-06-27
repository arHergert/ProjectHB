package com.game.box2d.mapobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.box2d.Player;
import com.game.leveldesign.WorldMap;

import static com.game.Main.spritesheet;
import static com.game.box2d.Player.PLAYER_SPEED;
import static com.game.box2d.Player.PPM;
import static com.game.box2d.Player.playerBody;
import static com.game.input.PlayerMovementInputProcessor.horizSpeed;
import static com.game.input.PlayerMovementInputProcessor.vertSpeed;

/**
 * Ein Stein kann getragen werden und runtergestellt werden
 */
public class Rock extends MapObjects {

	/** Gibt zurück, ob ein Stein gerade hochgehoben ist. Standardweise false */
	private boolean isPickedUp = false;
	private boolean isRectangle;
	float x,y;
    //Shape shape;

    /** Region der aktuellen Steintextur aus der Spritesheet*/
    private TextureRegion currentTexture;
	
	/**
	 * Erstellt ein neues Stein-Objekt.
     * Das Steinobjekt ist ein Kreisobjekt.
     * Rocks müssen sich im Level in der Ebene "InteractiveObjects" befinden.
     *
	 * @param map WorldMap des Levels
     * @param mapSensorObject Name des Objektes in der TiledMap
     * @param isRectangle TRUE, wenn der Stein Rechteckig sein soll, FALSE wenn es rund sein soll
     * @param datatype Mit welchen Datentypen der Stein beschriftet sein soll
	 */
	public Rock(WorldMap map, String mapSensorObject, boolean isRectangle, String datatype) {
		super(map);


        Shape shape;
        if (isRectangle){
            shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        }else{
            shape = getCircle((CircleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        }

        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(mapSensorObject);
        shape.dispose();
        body.setType(BodyDef.BodyType.KinematicBody);

        this.isRectangle = isRectangle;

        /* Texturen für jeden einzelnen Datentypen */
        TextureRegion intTex, stringTex, floatTex, boolText;
        if(datatype.equals("int")){
            intTex = spritesheet.findRegion("rock_rectangle_int");
            currentTexture = intTex;
        }else if(datatype.equals("String")){
            stringTex = spritesheet.findRegion("rock_rectangle_String");
            currentTexture = stringTex;
        }else if(datatype.equals("float")){
            floatTex = spritesheet.findRegion("rock_rectangle_float");
            currentTexture = floatTex;
        }else if (datatype.equals("boolean")){
            boolText = spritesheet.findRegion("rock_rectangle_boolean");
            currentTexture = boolText;
        }else{
            intTex = spritesheet.findRegion("rock_rectangle_int");
            currentTexture = intTex;
        }
	}
	
	/**
	 * Hebt einen Stein hoch.
	 */
	public void pickUp() {

        System.out.println("Stein hoch -> " + fixture.getUserData());
        this.x = -1;
        this.y = -1;
		isPickedUp = true;
        Player.isCarryingObject = true;

	}
	
	/**
	 * Setzt einen Stein wieder ab.
	 */
	public void putDown() {

        if (positionX + (currentTexture.getRegionWidth()/2)  < level.getMapRight() ){
            System.out.println("Stein runter -> " + fixture.getUserData());
            Player.isCarryingObject = false;
            isPickedUp = false;

        }else{
            System.err.println("Stein kann an dieser Stelle nicht abgelegt werden!");
        }




	}
	
	public void putDown(float x, float y) {
		if (positionX + (currentTexture.getRegionWidth()/2)  < level.getMapRight() ){
            System.out.println("Stein runter -> " + fixture.getUserData());


            body.setTransform(new Vector2(0,0), 0);
            Player.isCarryingObject = false;
            isPickedUp = false;

        }else{
            System.err.println("Stein kann an dieser Stelle nicht abgelegt werden!");
        }
	}
	
	/**
	 * Gibt zurück, ob ein Stein gerade hochgehoben wird.
	 * @return Ist der Stein hochgehoben?
	 */
	public boolean isPickedUp() {
		return isPickedUp;
	}

	/**
	 * Gibt zurück, ob es sich um einen runden oder um einen eckigen Stein (für Puzzles) handelt.
	 * @return true, wenn der Stein eckig ist
	 */
	public boolean isRectangle() {
		return this.isRectangle;
	}


    public void draw(Batch batch) {


        if(isPickedUp()) {

            if (!playerBody.getLinearVelocity().isZero()) {

                //positionX und positionY ist immer die positon der Textur
                positionX += horizSpeed * (PLAYER_SPEED / 3.75);
                positionY += vertSpeed * (PLAYER_SPEED / 3.75);
                body.setLinearVelocity(horizSpeed * (PPM * PLAYER_SPEED), vertSpeed * (PPM * PLAYER_SPEED));
            } else {
                body.setLinearVelocity(0, 0);
            }
        }

        batch.draw(currentTexture, positionX, positionY, 32, 22);


        
    }
}//end class Rock