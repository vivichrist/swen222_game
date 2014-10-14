package controllers;

import java.awt.Point;

import window.components.GUI;
import world.components.MoveableObject;
import world.game.GameState;
import world.game.Player;

/**
 * A Controller to handle interactions between the GameState and the GUI
 * @author Kalo Pilato - ID:300313803
 *
 */
public class UIController {

	private static GameState state;
	private static GUI gui;
	
	/**
	 * Constructor - creates a Controller for GameState/GUI interaction in this game
	 * @param state the GameState of this game
	 * @param gui the GUI for this game
	 */
	public UIController(GameState state, GUI gui){
		this.state = state;
		this.gui = gui;
	}
	
	public UIController(GUI gui){
		//this.state = state;
		this.gui = gui;
	}
	
	/**
	 * Redraws the GameToken panel in the GUI - use for updating the GUI when a Player collects a GameToken
	 */
	public static void refreshTokenPanel(){
		gui.redrawCollectItemCanvas();
	}
	
	/**
	 * Redraws the Inventory panel in the GUI - use for updating the GUI when a Player collects a MoveableObject
	 */
	public static void refreshInventoryPanel(){
		gui.redrawUsefulItemCanvas();
	}

	public void moveOtherPlayer(Player player, Point point) {
		System.out.println("waiting for vivian === move other player");
		state.movePlayer(player, point);
	}
	
	public void movePlayer(Player player, Point point){
		state.movePlayer(player, point);
	}
	public GameState deserialize(byte[] realData) {
		return state.deserialize(realData);
	}
	
	public boolean teleport(Player p){
		int floor = gui.getFloor(state.floorCount());
		return state.teleport(p, floor);
	}

	public void setState(GameState st) {
		state.setState(st);
	}

	public Player getPlayer(String username) {
		return state.getPlayer(username);
	}

	public void setConnection(boolean connection) {
		state.setConnection(connection);
	}
	
	
	/**
	 * this method is going to update the player inventory list once current player 
	 * dorp an object
	 * 
	 * */

	public void dropObject(Player player, MoveableObject object) {
		// TODO kalo please check the following code make sure it is delete the correct object
		//state.getPlayer(player.getName()).getInventory().remove(object);
		
	}



}
