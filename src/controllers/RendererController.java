package controllers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ui.components.GameViewData;
import world.components.MoveableObject;
import world.game.GameState;
import world.game.Player;

/**
 * Controller for the Renderer to handle communication between the NetworkController, GameState and Renderer
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class RendererController {

	private static NetworkController netCon;
	private static UIController uiCon;
	private static GameState state;
	private static GameViewData view;
	private static boolean singlePlayer;
	
	/**
	 * Constructor - creates a RendererController with a Pointer to the Singleton GameViewData for updating Renderer database
	 */
	public RendererController(boolean single){
		view = GameViewData.instance();
		singlePlayer = single;
	}
	
	/**
	 * @param n the netCon to set
	 */
	public static void setNetCon(NetworkController n) {
		netCon = n;
	}

	/**
	 * @param s the state to set
	 */
	public static void setState(GameState s) {
		state = s;
	}
	
	/**
	 * @param u the UIController to set
	 */
	public static void setUICon(UIController u){
		uiCon = u;
	}
	
	/**
	 * @return the singlePlayer
	 */
	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	/**
	 * Moves another Player in the Game
	 * @param player the Player to move
	 * @param point the Point to move the Player to
	 */
	public static void moveOtherPlayer(Player player, Point point){
		view.addNewPlayerMove(player.getPosition(), point);
		System.out.println("moving player: " + player.getName() + " to " + point.toString());
		state.movePlayer(player, point);
		System.out.println(player.getName() + " position: " + state.getPlayer(player.getName()).getPosition().toString());
	}
	
	/**
	 * Moves the current Player in the Game
	 * @param player the Player to move
	 * @param point the Point to move the Player to
	 */
	public static void movePlayer(Player player, Point point){
		if(!singlePlayer) netCon.movePlayer(player, point);
		if(state == null) System.out.println("State is null in RendererController");
		state.movePlayer(player, point);
	}
	
	/**
	 * once current player pickup a object will call this method and pass to netCon to 
	 * create a new pickup pack 
	 * 
	 * */
	
	public static void  pickupObject(Player player, MoveableObject object, Point point){
		netCon.pickupObject(player, object, point);
	}
	/**
	 * remove Object from client side after other client pickup a object
	 * @param player - player who pickup the object
	 * @param object - object to remove;
	 * @param point  -  for GameView to remove the object
	 * 
	 * */
	public static void removeObject(Player player, MoveableObject object, Point point){
	
		//TODO: kalo call this method to remove from gamestate
		//TODO: vivian call this method to remove from gameview
	//	state.removeObject(player, object);
	//	view.removeObject(point);
	}
	/**
	 *received action from server, need update the gameview and gamestate
	 *Instruction from server (other player trigger the door open action)
	 * 
	 * */
	public void toOpenDoor(String doorAction, Point point) {
		// TODO:  vivian call this method to open door with given point
		//TODO: Kalo call this method to open door with given point
		//view.toOpenDoor(point.x,point.y);
		//state.toOpenDoor(.......);
		
	}
	public void dropObject(Player player, MoveableObject object, Point point){
		netCon.dropObject(player,object,point);
	}
	
	public static boolean canOpenDoor(Player player, Point point){
		boolean canOpen = state.canOpenDoor(player, point);
		if(canOpen){
			openDoor(player.getName(), point);
			return true;
		}
		return false;
	}
	
	public List<Player> getPlayers(){
		return state.getPlayers();
	}
	 
	/**
	 * gameview will call this method 
	 * and pass the action to client then send to server
	 * 
	 * */
	
	private static void openDoor(String name, Point point){
		netCon.openDoor(name, point);
	}
	
	/**
	 * Teleport the currentPlayer in the Game
	 * @param player the Player to teleport
	 * @return
	 */
	public static boolean teleport(Player player){
		return uiCon.teleport(player);
	}
	
	public static void teleportOtherPlayer(Player player, int floorNumber){
		
	}
	
	

	
}
