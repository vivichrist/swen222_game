package world.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import world.components.Map;


public class GameBuilder {

	//TODO: Decide how TokenTypes should be graphically represented and update TokenTypes here to match
	public enum TokenType{
		SQUARE, CIRCLE, TRIANGLE
	}
	
	private List<Player> players;
	private Map[] floors;
	
	/**
	 * Constructor - creates a new game with a given list of Players.  Currently builds the same number of floors as there are Players
	 * @param players the list of Players in this game.
	 */
	public GameBuilder(){
		players = new ArrayList<Player>();
		getPlayers();
		buildFloors(players.size());
	}
	
	/**
	 * Prompts users to input names and builds Players with the corresponding names
	 */
	private void getPlayers(){
		//TODO: Specify/check a player count when starting a game, currently hardcoded to 3.  Should be minimum 2? or 1?  Maximum should equal the number of TokenTypes
		int playerCount = 3;
		for(int i = 0; i < playerCount; i++){
			String playerName = JOptionPane.showInputDialog("Enter Player Name:", null);
			System.out.println("TokenType: " + i + " - " + TokenType.values()[i]);
			players.add(new Player(playerName, TokenType.values()[i]));
		}
	}
	
	/**
	 * Builds the collection of "floors" for this game.
	 * @param floorCount the number of floors to use in this game.
	 */
	private void buildFloors(int floorCount){
		floors = new Map[floorCount];
		for(int i = 0; i < floorCount; i++){
			//TODO: Create multiple map files and update this method to build each floor from a different map file - currently builds all identical floors
			floors[i] = new Map(new File("map1.txt"));
		}
	}
}
