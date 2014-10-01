package world.game;

/**
 * The top level game logic class which holds (and manages changes of) the game state
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class Game implements java.io.Serializable, Runnable{

	private static GameState state;
	
	/**
	 * Constructor - creates a new game with a given GameState
	 * @param state
	 */
	public Game(GameState state){
		this.state = state;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
