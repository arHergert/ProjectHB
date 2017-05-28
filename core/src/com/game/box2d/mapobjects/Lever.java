package com.game.box2d.mapobjects;

public class Lever extends MapObjects {

	/**
	* Gibt den Zustand des Hebels an
	*/
	private boolean isActivated;	//Vielleicht dummer Name


	/*
	* Wird bei Benutzung des Hebels aufgerufen und ändert den Zustand
	*/
	public void use() {
		isActivated = !isActivated;
	}


	/*
	* @return Zustand des Hebels
	*/
	protected boolean getIsActivated() {
		return isActivated;
	}


}//end class Lever