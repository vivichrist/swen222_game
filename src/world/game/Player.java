package world.game;



/**
 * Represents a Player in the game world.
 * @author Kalo Pilato
 *
 */
public class Player {
	
	private final String name;
	private final TokenList toCollect;
	private final Inventory inventory;
	//TODO: should Players maintain their position? or should the game state? or both?
	private int xPos;
	private int yPos;
	
	/**
	 * Constructor - Creates a Player with a given name, an empty inventory, and a TokenList of items to collect
	 * @param name the name of this Player
	 */
	public Player(String name, GameBuilder.TokenType type){
		this.name = name;
		toCollect = new TokenList(type);
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
