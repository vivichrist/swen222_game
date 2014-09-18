package world.components;

/**
 * A Torch for lighting up dark rooms.  Can be turned on and off.
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class Torch implements MoveableObject {

	private boolean lit = false;
	
	/**
	 * Constructor - creates a Torch which is turned off by default.
	 */
	public Torch(){
	}
	
	/**
	 * Turns this Torch on
	 */
	public void turnOn(){
		lit = true;
	}
	
	/**
	 * Turns this Torch off
	 */
	public void turnOff(){
		lit = false;
	}
}
