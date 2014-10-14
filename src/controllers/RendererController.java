package controllers;

import java.awt.Point;

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
	private static GameState state;
	private static GameViewData view;
	
	/**
	 * Constructor - creates a RendererController with a Pointer to the Singleton GameViewData for updating Renderer database
	 */
	public RendererController(){
		GameViewData gv = GameViewData.instance();
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
	 * Moves another Player in the Game
	 * @param player the Player to move
	 * @param point the Point to move the Player to
	 */
	public static void moveOtherPlayer(Player player, Point point){
		view.addNewPlayerMove(player.getPosition(), point);
		state.movePlayer(player, point);
	}
	
	/**
	 * Moves the current Player in the Game
	 * @param player the Player to move
	 * @param point the Point to move the Player to
	 */
	public static void movePlayer(Player player, Point point){
		//netCon.movePlayer(player, point);
		state.movePlayer(player, point);
	}
	/**
	 * remove Object from client side after other client pickup a object
	 * @param player - player who pickup the object
	 * @param object - object to remove;
	 * 
	 * */
	public static void removeObject(Player player, MoveableObject object){
		//TODO: kalo call this method to remove from gamestate
		//TODO: vivian call this method to remove from gameview
	//	state.removeObject(player, object);
	//	view.removeObject(player,object);
	}

	public void toOpenDoor(String doorAction, Point point) {
		// TODO vivian call this method to open door with given point
		//view.toOpenDoor(point.x,point.y);
		
	}
	
	

	
}
