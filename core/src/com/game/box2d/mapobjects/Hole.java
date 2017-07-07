package com.game.box2d.mapobjects;

import static com.game.Main.assetManager;
import static com.game.Main.spritesheet;
import static com.game.box2d.Player.carryingStone;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.leveldesign.WorldMap;

public class Hole extends MapObjects {

    /**
     *  @TODO
     *
     *
     *  NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!
     *  NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!
     *  NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!
     *  NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!
     *  NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!NICHT VERGESSEN!
     *
     *
     *  Je nach status Texturen ändern!!!!!!!!!
     *  Wenn Hole aktiviert wurde und stein richtig ist-> currentTexture = status_true;
     *  Wenn Hole aktiviert wurde und stein falsch ist -> currentTexture = status_false;
     *  Wenn Hole geleert wird/ leer ist -> currentTexture = status_default;
     *
     *
     *
     *
     *
     *
     *
     */

	private Rock contents;
	private String datatype;
	private boolean collidesWithRock;

    //Verschiedene Texturen für verschiedene Zustände
    private TextureRegion status_neutral, status_true, status_false, currentTexture;
	
	public Hole(WorldMap map, String mapSensorObject, String datatype) {
		super(map);
		PolygonShape shape = getRectangle((RectangleMapObject)map.getMap().getLayers().get("InteractiveObjects").getObjects().get(mapSensorObject));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.setType(BodyDef.BodyType.DynamicBody);
        fixture.setUserData(mapSensorObject);
        fixture.setSensor(true);
        this.datatype = datatype;
        collidesWithRock = false;

        status_neutral = spritesheet.findRegion("hole_default");
        status_true = spritesheet.findRegion("hole_true");
        status_false = spritesheet.findRegion("hole_false");
        currentTexture = status_neutral;
	}
	
	public boolean holdsRock() {
		return (contents != null);
	}
	
	public void putRock(Rock rock) throws Exception {
		if(contents == null) {
			if(rock.isRectangle()) {
				contents = rock;
				rock.putDown();
				if(this.datatype.equals(rock.datatype())) {
					currentTexture = status_true;
                    assetManager.get("sounds/hole_true.wav", Sound.class).play();
				} else {
					currentTexture = status_false;
                    assetManager.get("sounds/hole_false.wav", Sound.class).play();
				}
			} else {
				throw new Exception("This rock doesn't fit in that kind of hole.");
			}
		} else {
			throw new Exception("Hole already contains a puzzle rock.");
		}
	}
	
	/**
	 * Nimmt einen Puzzlestein aus einem Loch
	 * und gibt das gel�schte Objekt wieder zur�ck.
	 * @return der Stein, der aus dem Loch genommen wurde.
	 */
	public Rock removeRock() {
		if(contents != null) {
			Rock r = contents;
			contents = null;
			currentTexture = status_neutral;
			return r;
		} else {
			return null;
		}
	}
	
	@Override
	public void collideOn() {
		collidesWithRock = true;
		//System.out.println("collideOn: " + fixture.getUserData());
	}

	@Override
	public void collideOff() {
		collidesWithRock = false;
	//	System.out.println("collideOff: " + fixture.getUserData());
	}
	
	public boolean collidesWithRock() {
		return collidesWithRock;
	}
	
	public String datatype() {
		return datatype;
	}
	
	public Rock getRock() {
		return contents;
	}


    @Override
    public void draw(Batch batch) {

        batch.draw(currentTexture,positionX,positionY,32,22);
    }


}//end class Hole
