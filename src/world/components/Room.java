package world.components;

import java.util.ArrayList;
import java.util.List;

/**
 * A Room in the game world.  Rooms make up the core of the game world and can contain GameObjects and Doors.
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class Room {

	private final String name;
	private Door northDoor;
	private Door eastDoor;
	private Door southDoor;
	private Door westDoor;
	private List<GameObject> objects;
	
	/**
	 * Constructor - creates a Room with a given name.
	 * @param name the name of this Room
	 */
	public Room(String name){
		this.name = name;
		objects = new ArrayList<GameObject>();
	}
	
	/**
	 * Adds a Door to this Room.  A Door cannot be added if it is null, or if it is to be added to a wall that already has a Door.
	 * @param door the Door to add
	 * @return false if the Door was not added
	 */
	public boolean addDoor(Door door, String dir){
		//TODO: Enumerate Directions in the Game Logic and update this method's dir param and refactor.  Until then this method will behave badly with incorrect dir Strings
		if(door == null) return false;
		if(dir.equals("N")){
			if(northDoor != null) return false;
			else{
				northDoor = door;
				return true;
			}
		}
		else if(dir.equals("E")){
			if(eastDoor != null) return false;
			else{
				eastDoor = door;
				return true;
			}
		}
		else if(dir.equals("S")){
			if(southDoor != null) return false;
			else{
				southDoor = door;
				return true;
			}
		}
		else if(dir.equals("W")){
			if(westDoor != null) return false;
			else{
				westDoor = door;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the Door at a given direction in this room
	 * @param dir the direction of the wall to retrieve the door from
	 * @return the Door on the dir wall. Returns null if no Door exists in that direction.
	 */
	public Door getDoor(String dir){
		//TODO: Enumerate Directions in game logic and update this method's dir param and refactor. 
		if(dir.equals("N")) return northDoor;
		if(dir.equals("E")) return eastDoor;
		if(dir.equals("S")) return southDoor;
		if(dir.equals("W")) return westDoor;
		return null;
	}
	
	
	
	
}
