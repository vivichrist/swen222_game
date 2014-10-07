package world.game;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import world.components.GameToken;
import world.components.Map;

/**
 * Represents the state of the game.
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class GameState implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private  List<Player> players;
	private  Map[] floors;
	private GameBuilder game;

	
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
	 * Sets the status of a given Player's GameToken to found
	 * @param p the Player whose GameToken has been found
	 * @param token the GameToken that has been found
	 * @return true if successfully set to found
	 */
	public boolean foundToken(Player p, GameToken token){
		if(!players.contains(p)){
			return false;
		}
		else{
			p.getTokenList().get(token).setFound(true);
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
	
	public byte[] serialize() {
		System.out.println("x: "+ players.get(0).getPosition().x+ " Y: "+players.get(0).getPosition().y);

		byte[] bytes = new byte[1024];
		try {
			//object to bytearray
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(this);
			bytes = baos.toByteArray();
			baos.close();
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bytes;
	}
	public GameState deserialize(byte[]bytes) {

		try{
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(bais);
			List<Player> players = new ArrayList<Player>();
			Map[] floors = new Map[game.getLevelsBuilding()];
			GameState gameState = new GameState(players, floors);
			gameState = (GameState) in.readObject();
			this.players = gameState.players;
			this.floors = gameState.floors;
			if(players.size() == 2){
			System.out.println(players.get(0).getPosition().x);
			System.out.println(players.get(1).getPosition().x);
			return gameState;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public GameState getState(){
		return this;
	}


	
	
	
}
