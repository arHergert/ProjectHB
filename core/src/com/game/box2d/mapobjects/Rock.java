package com.game.box2d.mapobjects;

import com.badlogic.gdx.physics.box2d.World;
import com.game.LevelMap;

public class Rock extends MapObjects {

	/** Gibt zurück, ob ein Stein gerade hochgehoben ist. */
	private boolean isPickedUp;
	
	/**
	 * Erstellt ein neues Stein-Objekt.
	 * @param world
	 * @param map
	 */
	public Rock(World world, LevelMap map) {
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
	 * Gibt zurück, ob ein Stein gerade hochgehoben ist.
	 * @return Ist der Stein hochgehoben?
	 */
	public boolean isPickedUp() {
		return isPickedUp;
	}

}