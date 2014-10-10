package world.game;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import controllers.Controller;
import window.components.GUI;
import world.components.Door;
import world.components.GameObject;
import world.components.GameToken;
import world.components.Map;
import world.components.MoveableObject;

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
	//private Controller controller;
	private boolean isMoved;

	
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
	 * Sets the PlayerController for this game
	 * @param controller the PlayerController to use for this game
	 */
//	public void setController(Controller controller){
//		this.controller = controller;
//	}
//	
	/**
	 * Moves a Player in the game
	 * @param player the Player to move 
	 * @param point the Point to move the Player to
	 */
	public void movePlayer(Player player, Point point){
		player.move(point);
		isMoved = true;
	}
	public boolean isMoved(){
		return isMoved;
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
	 * Picks up a MoveableObject or GameToken from the game world and adds it to the Player's Inventory or TokenList
	 * @param p the Player to add the item to
	 * @param point the Point to retrieve the object from
	 * @return true if successfully picked up
	 */
	public boolean pickupObjectAtPoint(Player player, Point point){
		
		GameObject object = player.getFloor().objectAtPoint(point);
		
		// Handle the case that the object is a GameToken
		if(object instanceof GameToken){
			GameToken token = (GameToken) object;
			if(!players.contains(player)){
				return false;
			}
			else{
				player.getFloor().removeGameToken(point, token);
				player.getTokenList().tokenFound(token);
				Controller.refreshTokenPanel();
				if(player.getTokenList().collectedAll()){
					//TODO: update action here to go in to win state checking or something
					System.out.println("All Tokens Collected!");
				}
				return true;
			}
		}
		
		// Handle the normal case that the object is a MoveableObject
		if(object instanceof MoveableObject){
			MoveableObject moveable = (MoveableObject) object;
			player.getInventory().add(moveable);
			player.getFloor().removeMoveableObject(point);
			Controller.refreshInventoryPanel();
			return true;
		}
		return false;
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
	
	/**
	 * Checks whether a Player can open a Door at a given Point
	 * @param player the Player to open the Door
	 * @param point the position of the Door in the map
	 * @return true if the Player has the key to this Door
	 */
	public boolean canOpenDoor(Player player, Point point){
		Door door = player.getFloor().getDoor(point);
		if(player.getInventory().contains(door.getKey())) return true;
		return false;
	}
	
	public byte[] serialize() {
		System.out.println("x: "+ players.get(0).getPosition().x+ " Y: "+players.get(0).getPosition().y);

		byte[] bytes = new byte[85000];
		try {
			//object to bytearray
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(this);
			bytes = baos.toByteArray();
			out.flush();
			baos.close();
			out.close();
			return bytes;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public GameState deserialize(byte[]bytes) {

		try{
			//ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream((new ByteArrayInputStream(bytes)));
//			List<Player> players = new ArrayList<Player>();
//			Map[] floors = new Map[game.getLevelsBuilding()];
//			GameState gameState = new GameState(players, floors);
			GameState gameState = (GameState) in.readObject();
			System.out.println(gameState.getPlayers().get(1).getName());
//			this.players = gameState.players;
//			this.floors = gameState.floors;
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
	/**\
	 * Return the current GameState
	 * @return the current GameState
	 */
	public GameState getState(){
		return this;
	}
	
	//TODO: return a clone here instead of the actual list
	/**
	 * Returns a List of the current Players in this game
	 * @return a List of the current Players in this game
	 */
	public List<Player> getPlayers(){
		return players;
	}
	
}
