package com.game.box2d.mapobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.box2d.Player;
import com.game.leveldesign.WorldMap;

import static com.game.Main.assetManager;
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

	/** Gibt zurueck, ob ein Stein gerade hochgehoben ist. Standardweise false */
	private boolean isPickedUp = false;
	private boolean isRectangle;
	float x,y;
	private String datatype;
    //Shape shape;

    /** Region der aktuellen Steintextur aus der Spritesheet*/
    private TextureRegion currentTexture;
	
	/**
	 * Erstellt ein neues Stein-Objekt.
     * Das Steinobjekt ist ein Kreisobjekt.
     * Rocks muessen sich im Level in der Ebene "InteractiveObjects" befinden.
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

        //Hauptbody erstellen fuer das Kollidieren und zum Bewegen
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(mapSensorObject);
        shape.dispose();
        body.setType(BodyDef.BodyType.KinematicBody);


        //Sensorbody erstellen zur Interaktion mit statischen Objekten



        this.datatype = datatype;
        this.isRectangle = isRectangle;

        /* Texturen fuer jeden einzelnen Datentypen */
        TextureRegion intTex, stringTex, floatTex, boolText,
        arrayListTex,listTex,listVarTex,stringTypeTex,equalsTex,newTex,bracketsTex,diamondTex;
        switch (datatype) {
            case "int":
                intTex = spritesheet.findRegion("rock_rectangle_int");
                currentTexture = intTex;
                break;
            case "String":
                stringTex = spritesheet.findRegion("rock_rectangle_String");
                currentTexture = stringTex;
                break;
            case "float":
                floatTex = spritesheet.findRegion("rock_rectangle_float");
                currentTexture = floatTex;
                break;
            case "boolean":
                boolText = spritesheet.findRegion("rock_rectangle_boolean");
                currentTexture = boolText;
                break;
            case "List":
            	listTex = spritesheet.findRegion("rock_list");
            	currentTexture = listTex;
            	break;
            case "<String>":
            	stringTypeTex = spritesheet.findRegion("rock_String");
            	currentTexture = stringTypeTex;
            	break;
            case "list":
            	listVarTex = spritesheet.findRegion("rock_listvar");
            	currentTexture = listVarTex;
            	break;
            case "=":
            	equalsTex = spritesheet.findRegion("rock_equals");
            	currentTexture = equalsTex;
            	break;
            case "new":
            	newTex = spritesheet.findRegion("rock_new");
            	currentTexture = newTex;
            	break;
            case "ArrayList":
            	arrayListTex = spritesheet.findRegion("rock_arraylist");
            	currentTexture = arrayListTex;
            	break;
            case "<>":
            	diamondTex = spritesheet.findRegion("rock_diamond");
            	currentTexture = diamondTex;
            	break;
            case "();":
            	bracketsTex = spritesheet.findRegion("rock_brackets");
            	currentTexture = bracketsTex;
            	break;
            case "normal":
                bracketsTex = spritesheet.findRegion("rock_normal");
                currentTexture = bracketsTex;
                break;
            default:
                intTex = spritesheet.findRegion("rock_rectangle_int");
                currentTexture = intTex;
                break;
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
        assetManager.get("sounds/rock_pickup.wav", Sound.class).play();

	}
	
	/**
	 * Setzt einen Stein wieder ab.
	 */
	public void putDown() {

        if (positionX + (currentTexture.getRegionWidth()/2)  < level.getMapRight() ){
            System.out.println("Stein runter -> " + fixture.getUserData());
            Player.isCarryingObject = false;
            isPickedUp = false;
            assetManager.get("sounds/rock_putdown.wav", Sound.class).play();

        }else{
            System.err.println("Stein kann an dieser Stelle nicht abgelegt werden!");
        }




	}

	/**
	 * Gibt zurueck, ob ein Stein gerade hochgehoben wird.
	 * @return Ist der Stein hochgehoben?
	 */
	public boolean isPickedUp() {
		return isPickedUp;
	}

	/**
	 * Gibt zurueck, ob es sich um einen runden oder um einen eckigen Stein (fuer Puzzles) handelt.
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
    
    public String datatype() {
    	return datatype;
    }
    
}//end class Rock