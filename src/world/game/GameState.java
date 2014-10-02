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
	
	
	
	
}
