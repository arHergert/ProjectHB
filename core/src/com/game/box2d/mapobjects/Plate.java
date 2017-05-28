package com.game.box2d.mapobjects;

public class Plate extends MapObjects {

	/*
	* Gibt an, ob die Druckplatte aktiviert ist
	*/
	private boolean isActivated;

	/*
	* Gibt an, ob die Bodenplatte einrastet
	*/
	private boolean locks;


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
		if(!locks) {
			isActivated = false;
		}
	}


	/*
	* @return Zustand des Hebels
	*/
	protected boolean getIsActivated() {
		return isActivated;
	}


}