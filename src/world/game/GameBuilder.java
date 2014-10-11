package world.game;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import world.components.CellType;
import world.components.Direction;
import world.components.Door;
import world.components.Furniture;
import world.components.Key;
import world.components.Map;
import world.components.TokenType;
import world.components.Torch;


public class GameBuilder {

	private List<Player> players;
	private Map[] floors;
	private GameState state;
	
	/**
	 * Constructor - creates a new game with a given list of Players.  Currently builds the same number of floors as there are Players
	 * @param players the list of Players in this game.
	 */
	public GameBuilder(List<String> playerNames){
		if(playerNames.size() > 4) System.out.println("This game supports a maximum of 4 players only");
		else{
			players = new ArrayList<Player>();
			for(int i = 0; i < playerNames.size(); i++){
				players.add(new Player(playerNames.get(i), TokenType.values()[i]));
			}
			
			//getPlayers();
			buildFloors(players.size());
			//TODO: generate collections of StationaryObjects and MoveableObjects somehow???
			//TODO: place StationaryObjects
			placePlayers();
			//TODO: place MoveableObjects
			placePlayerTokens();
			placeTorches();
			//placeFurniture();
			state = new GameState(players, floors);
		}
	}
	
	//TODO: remove this constructor once integration testing is complete
	/**
	 * Temporary Constructor - creates a new game with a given list of Players.  Currently builds the same number of floors as there are Players
	 * @param players the list of Players in this game.
	 */
	public GameBuilder(String playerName){
		players = new ArrayList<Player>();
		players.add(new Player(playerName, TokenType.values()[0]));
		
		//getPlayers();
		buildFloors(players.size());
		//TODO: generate collections of StationaryObjects and MoveableObjects somehow???
		//TODO: place StationaryObjects
		placePlayers();
		placeFurniture();
		placePlayerTokens();
		placeTorches();
		state = new GameState(players, floors);
	}
	
	/**
	 * Returns the newly constructed GameState
	 * @return the newly constructed GameState
	 */
	public GameState getGameState(){
		return state;
	}
	
	private void serialize(){
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("../state.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(state);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in ../state.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
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
	public void buildFloors(int floorCount){
		floors = new Map[floorCount];
		for(int i = 0; i < floorCount; i++){
			//TODO: Create multiple map files and update this method to build each floor from a different map file - currently builds all identical floors
			floors[i] = new Map(new File("map1.txt"));
		}
	}
	
	//TODO: do we randomise start positions of players? or hard code?  this method currently places each player in the same position on different floors
	/**
	 * Places each Player in the game world, setting their start position to a hard coded floor and x/y coordinate.
	 * Players are placed at the same Point on different floors.  
	 */
	private void placePlayers(){
		for(int i = 0; i < players.size(); i++){
			Player currentPlayer = players.get(i);
			Point startPoint = new Point(3, 20);
			currentPlayer.setPosition(startPoint);
			currentPlayer.setFloor(floors[i]);
			floors[i].placePlayer(startPoint, currentPlayer);
		}
		//TODO: remove this testing code
		Player secondPlayer = new Player("aaa", TokenType.values()[1]);
		players.add(secondPlayer);
		Point start = new Point(3, 15);
		secondPlayer.setPosition(start);
		secondPlayer.setFloor(floors[0]);
		floors[0].placePlayer(start, secondPlayer);
	}
	
	/**
	 * Distributes each Player's Tokens throughout the game world, choosing floors and Points at random
	 */
	private void placePlayerTokens(){
		for(int i = 0; i < players.size(); i++){
			Player currentPlayer = players.get(i);
			TokenList currentTokens = currentPlayer.getTokenList();
			for(int j = 0; j < currentTokens.size(); j++){
				Random random = new Random();
				// Select a random floor in this world
				Map randomFloor = floors[random.nextInt(floors.length)];
				// Place the Token in a random cell on the floor
				randomFloor.addGameToken(randomFloor.randomEmptyCell(), currentTokens.get(j));
			}
		}
	}
	
	/**
	 * Distributes a torches (one per player) throughout the game world, choosing floors and Points at random
	 */
	private void placeTorches(){
		for(int i = 0; i < players.size(); i ++){
			Random random = new Random();
			Map randomFloor = floors[random.nextInt(floors.length)];
			randomFloor.addMoveable(randomFloor.randomEmptyCell(), new Torch());
			System.out.println("Torch added");
		}
	}
	
	//TODO: write a real method for placing furniture in the game - this is for testing purposes only
	private void placeFurniture(){
		
//		try{
//			Scanner scan = new Scanner(new File("furniture1.txt"));
//
//			// First token should be a String corresponding to a furniture type
//			if(!scan.hasNext()) 
//			{
//				scan.close();
//				throw new Exception("Map format error: first header token should be an int");
//			}
//			else xLimit = scan.nextInt();
//			if(!scan.hasNext("x"))
//			{
//				scan.close();
//				throw new Exception("Map format error: second header toden should be 'x'");
//			}
//			else scan.next();
//			if(!scan.hasNextInt())
//			{
//				scan.close();
//				throw new Exception("Map format error: third header token should be an int");
//			}
//			else yLimit = scan.nextInt();
//
//			
//			scan.close();
//		} catch ( Exception e )
//		{
//			e.printStackTrace();
//		}
		
		
		
		
		for(int i = 0; i < floors.length; i++){
			Point position = new Point(1, 16);
			floors[i].addFurniture(position, new Furniture(CellType.COUCH, position, Direction.WEST));
			floors[i].addFurniture(new Point(2, 1), new Furniture(CellType.COUCH, new Point(2, 1), Direction.SOUTH));
		}
	}

	public int getLevelsBuilding() {
		// TODO Auto-generated method stub
		return floors.length;
	}
	
}
