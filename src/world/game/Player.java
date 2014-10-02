package world.game;

import java.awt.Point;

import world.components.Map;
import world.components.TokenType;



/**
 * Represents a Player in the game world.
 * @author Kalo Pilato
 *
 */
public class Player implements java.io.Serializable{
	
	private final String name;
	private final TokenList toCollect;
	private final Inventory inventory;
	//TODO: should Players maintain their position? or should the game state? or both?
	private Map floor;
	private Point position;
	
	/**
	 * Constructor - Creates a Player with a given name, an empty inventory, and a TokenList of items to collect
	 * @param name the name of this Player
	 */
	public Player(String name, TokenType type){
		this.name = name;
		toCollect = new TokenList(type);
		this.inventory = new Inventory();
	}
	
	/**
	 * Returns the name of this Player
	 * @return the name of this Player
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sets the position of this Player using x/y coordinates
	 * @param x the x coordinate of the Player
	 * @param y the y coordinate of the Player
	 */
	public void setPosition(int x, int y){
		position = new Point(x, y);
	}
	
	/**
	 * Sets the position of this Player using a Point
	 * @param p the Point to set as this Player's position
	 */
	public void setPosition(Point p){
		position = p;
	}
	
	/**
	 * Returns the current position of this Player
	 */
	public Point getPosition(){
		return position;
	}
	
	/**
	 * Sets the Map that this Player is currently on
	 * @param floor the Map to set for this Player
	 */
	public void setFloor(Map floor){
		this.floor = floor;
	}
	
	//TODO: return a clone of the TokenList to make it immutable
	/**
	 * Returns this Player's TokenList
	 * @return this Player's TokenList
	 */
	public TokenList getTokenList(){
		return toCollect;
	}
	
	/**
	 * Returns the Map representing the Player's current floor
	 * @return the Map representing the Player's current floor
	 */
	public Map getFloor(){
		return floor;
	}
	
	public boolean move(Point p){
		if(floor.movePlayer(this, position, p)){
			System.out.println(name + " moved from " + position.toString() + " to " + p.toString());
			position = p;
			return true;
		}
		return false;
	}

}
