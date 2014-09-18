package world.components;
/**
 * A Door between two rooms
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class Door {

	private final Room room1;
	private final Room room2;
	private Key key;
	private boolean locked = false;
	private String description = null;
	
	/**
	 * Constructor - creates a Door (unlocked by default)
	 * @param room1 the Room on one side of the door
	 * @param room2 the Room on the other side of the door
	 */
	public Door(Room room1, Room room2){
		this.room1 = room1;
		this.room2 = room2;
	}

	/**
	 * Uses a Key in this Door's lock.  Provided it is the correct key, the lock will toggle the Door's locked state
	 * i.e. if the Door was locked it will now be unlocked
	 * @param key the Key to use in this lock
	 * @return the 
	 */
	public boolean useKey(Key key){
		//TODO: should the null check throw an exception???
		if(key == null) return false;
		
		if(key.equals(this.key)){
			locked = !locked;
			return locked;
		}
		return false;
	}
	
	/**
	 * Sets the Key for this Door
	 * @param key the Key to lock/unlock this Door
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * Checks whether this Door is locked
	 * @return true if the Door is locked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Sets the locked status of this Door
	 * @param locked the boolean to set this Door's locked status to (true = locked)
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * Gets the description of this door
	 * @return the description of this door
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of this Door
	 * @param description the description to set for this Door
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	

}
