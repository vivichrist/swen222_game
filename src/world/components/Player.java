package world.components;

/**
 * Represents a Player in the game world.
 * @author Kalo Pilato
 *
 */
public class Player {
	
	private final String name;
	private final Inventory inventory;
	private int xPos;
	private int yPos;
	
	/**
	 * Constructor - Creates a Player with a given name
	 * @param name the name of this Player
	 */
	public Player(String name){
		this.name = name;
		this.inventory = new Inventory();
	}
	
	/**
	 * Sets the position of this Player
	 * @param x the x coordinate of the Player
	 * @param y the y coordinate of the Player
	 */
	public void setPosition(int x, int y){
		xPos = x;
		yPos = y;
	}

}
