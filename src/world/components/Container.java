package world.components;

import java.util.ArrayList;

import world.components.MoveableObject;

/**
 * Represents the abstract concept of a GameObject that can contain another GameObject
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class Container implements GameObject{

	private ArrayList<MoveableObject> contents;
	private CellType type;
	private int maxSize;
	
	public Container(CellType type){
		contents = new ArrayList<MoveableObject>();
		this.type = type;
		if(type == CellType.BRIEFCASE || type == CellType.CHEST) maxSize = 1;
		if(type == CellType.DRAWERS) maxSize = 3;
	}
	
	public boolean setContents(MoveableObject object){
		if(contents.size() < maxSize){
			contents.add(object);
			return true;
		}
		return false;
	}
	
	public MoveableObject getContents(){
		if(!contents.isEmpty()) return contents.remove(0);
		else return null;
	}
}
