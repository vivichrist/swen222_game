package controllers;

import java.awt.Point;

import window.components.GUI;
import world.game.GameState;
import world.game.Player;

/**
 * A Controller to handle interactions between the GameState and the GUI
 * @author Kalo Pilato - ID:300313803
 *
 */
public class Controller {

	private static GameState state;
	private static GUI gui;
	
	/**
	 * Constructor - creates a Controller for GameState/GUI interaction in this game
	 * @param state the GameState of this game
	 * @param gui the GUI for this game
	 */
	public Controller(GameState state, GUI gui){
		this.state = state;
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
	}
	public void movePlayer(Player player, Point point){
		state.movePlayer(player, point);
	}
	public GameState deserialize(byte[] realData) {
		return state.deserialize(realData);
	}
	
	public boolean teleport(Player p, int floor){
		return state.teleport(p, floor);
	}

	public void setState(GameState st) {
		state.setState(st);
	}

	public Player getPlayer(String username) {
		return state.getPlayer(username);
	}

	public void setConnection(boolean connection) {
		// TODO Auto-generated method stub
		state.setConnection(connection);
	}


}
