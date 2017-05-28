package com.game.box2d.mapobjects;

/**
 * Eine Druckplatte aktiviert sich wenn man sie beschwert.
 * Sobald man das Gewicht entfert deaktiviert sie sich, außer
 * sie ist eingerastet, dann bleibt sie aktiviert
 */
public class Plate extends MapObjects {

	/**
	* Gibt an, ob die Druckplatte aktiviert ist. Standardweise false
	*/
	private boolean isActivated = false;

	/**
	* Gibt an, ob die Bodenplatte einrastet. Standardweise false
	*/
	private boolean lock = false;


	/**
	* Wird aufgerufen, wenn Platte beschwert wird, und aktiviert diese
	*/
	public void load() {
		isActivated = true;
	}

	/**
	* Wird aufgerufen, wenn sich kein Gewicht mehr auf der Platte befindet, und deaktivert diese, wenn sie nicht einrastet
	*/
	public void unload() {
		if(!lock) {
			isActivated = false;
		}
	}


	/**
	* @return Zustand des Hebels
	*/
	protected boolean isActivated() {
		return isActivated;
	}


}