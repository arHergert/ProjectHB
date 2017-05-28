package com.game.box2d.mapobjects;

import com.badlogic.gdx.physics.box2d.World;
import com.game.LevelMap;

/**
 * Eine Tür kann geschlossen sein oder offen.
 */
public class Door extends MapObjects {
	
	/** Speichert den Wert, ob die Tür geöffnet oder geschlossen ist */
	private boolean isOpen;
	
	/**
	 * Erstellt ein neues Tür-Objekt
	 * @param world
	 * @param map
	 */
	public Door(World world, LevelMap map) {
		super(world, map);
		isOpen = false;
	}
	
	/** Gibt den Wert zurück, der aussagt, ob die Tür geöffnet oder geschlossen ist. */
	public boolean isOpen() {
		return isOpen;
	}
	
	/**
	 * Öffnet die Tür.
	 */
	public void open() {
		isOpen = true;
	}
	
	/**
	 * Schließt die Tür.
	 */
	public void close() {
		isOpen = false;
	}

}