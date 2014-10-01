package world.components;

import world.components.MoveableObject;

public abstract class Container implements StationaryObject{

	protected MoveableObject contents;
	
	public abstract boolean setContents(MoveableObject contents);
	
	public abstract MoveableObject getContents();
}
