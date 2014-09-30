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
		placePlayers();
		placePlayerTokens();
	}
	
	//TODO: replace this method with a Player entry point in the UI - GameBuilder constructor will need to be updated to take a list of Players
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
	
	//TODO: do we randomise start positions of players? or hard code?  this method currently places each player in the same position on different floors
	/**
	 * Places a Player in the game world, setting their start position to a hard coded floor and x/y coordinate.  
	 */
	private void placePlayers(){
		for(int i = 0; i < players.size(); i++){
			Player currentPlayer = players.get(i);
			currentPlayer.setPosition(18,  20);
			currentPlayer.setFloor(floors[i]);
		}
	}
	
	private void placePlayerTokens(){
		
	
	}
	
}
