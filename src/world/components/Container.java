package world.components;

import world.components.MoveableObject;

/**
 * Represents the abstract concept of a GameObject that can contain another GameObject
 * @author Kalo Pilato - ID: 300313803
 *
 */
public abstract class Container implements GameObject{

	protected String name;
	protected MoveableObject contents;
	
	public Container(String name){
		this.name = name;
	}
	
	public abstract boolean setContents(MoveableObject contents);
	
	public abstract MoveableObject getContents();
}
