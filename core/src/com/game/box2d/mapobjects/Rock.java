package com.game.box2d.mapobjects;

import com.badlogic.gdx.physics.box2d.World;
import com.game.WorldMap;

/**
 * Ein Stein kann getragen werden und runtergestellt werden
 */
public class Rock extends MapObjects {

	/** Gibt zurück, ob ein Stein gerade hochgehoben ist. Standardweise false */
	private boolean isPickedUp = false;
	
	/**
	 * Erstellt ein neues Stein-Objekt.
	 * @param world
	 * @param map
	 */
	public Rock(World world, WorldMap map) {
		super(world, map);
	}
	
	/**
	 * Hebt einen Stein hoch.
	 */
	public void pickUp() {
		isPickedUp = true;
	}
	
	/**
	 * Setzt einen Stein wieder ab.
	 */
	public void putDown() {
		isPickedUp = false;
	}
	
	/**
	 * Gibt zurück, ob ein Stein gerade hochgehoben wird.
	 * @return Ist der Stein hochgehoben?
	 */
	public boolean isPickedUp() {
		return isPickedUp;
	}

}