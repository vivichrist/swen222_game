package world.components;

/**
 * The interface for stationary objects in this game world
 * @author Kalo Pilato - ID: 300313803
 *
 */
public interface StationaryObject extends GameObject {

	/**
	 * Returns the Direction that this StationaryObject is facing
	 * Used by the renderer for determining furniture orientation for drawing
	 * @return the Directino that this StationaryObject is facing
	 */
	public Direction facing(); 
	
}
