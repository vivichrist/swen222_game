package world.game;

import java.awt.Point;
import java.util.List;

import world.components.Map;

/**
 * Represents the state of the game.
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class GameState implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private final List<Player> players;
	private final Map[] floors;
	
	/**
	 * Constructor - creates the starting game state
	 * @param players the list of Players in this game
	 * @param floors the floors that make up the game world
	 */
	public GameState(List<Player> players, Map[] floors){
		this.players = players;
		this.floors = floors;
	}
	
	/**
	 * Moves a Player in the game
	 * @param player the Player to move 
	 * @param point the Point to move the Player to
	 */
	public void movePlayer(Player player, Point point){
		player.move(point);
	}
	
	/*
	public boolean addPlayer(String name){
		players.add(new Player(name, TokenType[players.size()]));
	}
	*/
	
	//TODO: replace with better accessor methods to get Maps for given players - may not need to be in this class at all
	/**
	 * Returns the Map representing the first floor in this game world
	 * @return
	 */
	public Map getMap(){
		return floors[0];
	}
	
	//TODO: assign Players to clients as appropriate - this method is purely for integration testing of a single player game state
	/**
	 * Returns the first Player in this Players collection
	 * @param index the index of the Player
	 * @return the Player at the given index
	 */
	public Player getPlayer(){
		return players.get(0);
	}
	
	/**
	 * Teleports a Player from one floor to another.
	 * @param p the Player to teleport
	 * @param floorNumber the number of the floor to teleport to
	 * @return true if successfully teleported
	 */
	public boolean teleport(Player p, int floorNumber){
		if(floorNumber >= floors.length) return false;
		else{
			p.getFloor().removePlayer(p);
			floors[floorNumber].placePlayer(p.getPosition(), p);
			p.setFloor(floors[floorNumber]);
			return true;
		}
	}
	
	/**
	 * Returns a Player with a given name
	 * @param name the name of the Player to return
	 * @return the Player with the given name - returns null if the name is not found
	 */
	public Player getPlayer(String name){
		for(Player p: players){
			if(p.getName().equals(name)) return p;
		}
		return null;
	}
	
	
	
	
}
