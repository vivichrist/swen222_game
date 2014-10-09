package world.game;

import java.util.ArrayList;
import java.util.List;

import world.components.MoveableObject;

/**
 * An inventory for a Player in the game world.  Contains MoveableObjects.
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class Inventory implements java.io.Serializable{
	
	private List<MoveableObject> inventory;
	private int maxSize = 5;
	
	/**
	 * Constructor - creates an empty Inventory
	 */
	public Inventory(){
		inventory = new ArrayList<MoveableObject>();
	}
	
	/**
	 * Adds a MoveableObject to this Inventory (provided that the Inventory has available space)
	 * @param item the MoveableObject to add
	 * @return true if added
	 */
	public boolean add(MoveableObject item){
		//TODO: Should this throw an exception???
		if(inventory.size() == maxSize) return false;
		else return inventory.add(item);
	}
	
	/**
	 * Removes a MoveableObject from this Inventory
	 * @param item the MoveableObject to remove
	 * @return true if removed
	 */
	public boolean remove(MoveableObject item){
		return inventory.remove(item);
	}
	
	/**
	 * Returns the MoveableObject at a given index
	 * @param index the index to retrieve the MoveableObject from
	 * @return the MoveableObject at index
	 */
	public MoveableObject get(int index){
		return inventory.get(index);
	}
	
	/**
	 * Returns the size of this Inventory
	 * @return the size of this Inventory
	 */
	public int size(){
		return inventory.size();
	}
	
	/**
	 * Checks if this Inventory contains a given MoveableObject
	 * @param object the MoveableObject to check for
	 * @return true if contained
	 */
	public boolean contains(MoveableObject object){
		if(inventory.contains(object)) System.out.println("Inventory contains object");
		return inventory.contains(object);
	}
}
