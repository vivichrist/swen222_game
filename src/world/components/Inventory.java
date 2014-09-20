package world.components;

import java.util.ArrayList;
import java.util.List;

/**
 * An inventory for a Player in the game world.  Contains MoveableObjects.
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class Inventory {
	
	private List<MoveableObject> inventory;
	
	/**
	 * Constructor - creates an empty Inventory
	 */
	public Inventory(){
		inventory = new ArrayList<MoveableObject>();
	}
	
	/**
	 * Adds a MoveableObject to this Inventory
	 * @param item the MoveableObject to add
	 * @return true if added
	 */
	public boolean add(MoveableObject item){
		return inventory.add(item);
	}
	
	/**
	 * Removes a MoveableObject from this Inventory
	 * @param item the MoveableObject to remove
	 * @return true if removed
	 */
	public boolean remove(MoveableObject item){
		return inventory.remove(item);
	}
	
}
