package world.components;

import world.components.MoveableObject;

public abstract class Container implements StationaryObject{

	protected String name;
	protected MoveableObject contents;
	
	public Container(String name){
		this.name = name;
	}
	
	public abstract boolean setContents(MoveableObject contents);
	
	public abstract MoveableObject getContents();
}
