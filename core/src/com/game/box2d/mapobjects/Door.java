package com.game.box2d.mapobjects;

import com.badlogic.gdx.physics.box2d.World;
import com.game.LevelMap;

/**
 * Eine T�r kann geschlossen sein oder offen.
 */
public class Door extends MapObjects {
	
	/** Speichert den Wert, ob die T�r ge�ffnet oder geschlossen ist */
	private boolean isOpen;
	
	/**
	 * Erstellt ein neues T�r-Objekt
	 * @param world
	 * @param map
	 */
	public Door(World world, LevelMap map) {
		super(world, map);
		isOpen = false;
	}
	
	/** Gibt den Wert zur�ck, der aussagt, ob die T�r ge�ffnet oder geschlossen ist. */
	public boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * �ffnet die T�r.
	 */
	public void open() {
		isOpen = true;
	}
	
	/**
	 * Schlie�t die T�r.
	 */
	public void close() {
		isOpen = false;
	}

}