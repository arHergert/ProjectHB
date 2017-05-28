package com.game.box2d.mapobjects;

/**
 * Ein Hebel kann aktiviert werden und deaktiviert
 * werden.
 */
public class Lever extends MapObjects {

	/**
	* Speichert den Zustand des Hebels. Standardweise false
	*/
	private boolean isActivated = false;


	/**
	* Wird bei Benutzung des Hebels aufgerufen und ändert den Zustand
	*/
	public void use() {

        isActivated = !isActivated;
	}


	/**
	* @return Zustand des Hebels
	*/
	protected boolean IsActivated() {
		return isActivated;
	}


}//end class Lever