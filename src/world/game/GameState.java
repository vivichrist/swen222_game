package world.game;

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
	
	
	
	
	
}
