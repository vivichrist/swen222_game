package controllers;

import java.awt.Point;

import ui.components.GameViewData;
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
		GameViewData gv = GameViewData.instance();
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
		state.movePlayer(player, point);
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
	 * Teleport the currentPlayer in the Game
	 * @param player the Player to teleport
	 * @return
	 */
	public static boolean teleport(Player player){
		return uiCon.teleport(player);
	}
	
	

	
}
