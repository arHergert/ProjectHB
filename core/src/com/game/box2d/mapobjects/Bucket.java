package com.game.box2d.mapobjects;

import java.util.TreeSet;

import com.badlogic.gdx.physics.box2d.World;
import com.game.LevelMap;

public class Bucket<T extends MapObjects> extends MapObjects {

	/** MapObject, das der Eimer enth�lt */
	private T contents;
	
	public Bucket(World world, LevelMap map) {
		super(world, map);
	}
	
	/**
	 * F�gt ein MapObject als Inhalt des Eimers hinzu
	 * Ein Eimer kann maximal ein Object enthalten
	 * @param object das MapObject, das in den Eimer gelegt werden soll
	 * @throws Exception wenn der Eimer bereits ein MapObject enth�lt
	 */
	public void fill(T mapObject) throws Exception {
		if(contents == null) {
			contents = mapObject;
		} else {
			throw new Exception("The bucket already holds the maximum amount of objects!");
		}
	}
	
	/**
	 * Leert den Eimer und gibt das enthaltene MapObject zur�ck.
	 * @return das MapObject, das vorher im Eimer war
	 */
	public T empty() {
		T t = contents;
		if(contents != null) {
			contents = null;
		}
		return t;
	}
	
	/**
	 * Gibt das MapObject zur�ck, das im Eimer enthalten ist.
	 * @return
	 */
	public T contents() {
		return contents;
	}
	
	/**
	 * Pr�ft, ob der Eimer leer ist.
	 * @return Ist der Eimer leer?
	 */
	public boolean isEmpty() {
		return (contents == null);
	}
	
}