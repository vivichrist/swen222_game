package controllers;

import java.awt.Point;

import javax.media.opengl.awt.GLJPanel;

import ServerClients.Client;
import ServerClients.UDPpackets.Packet03Move;
import ui.components.GameView;
import window.components.GUI;
import world.game.GameState;
import world.game.Player;
/**
 * A NetworkController to handle interactions between the Server/Client and Controller(GameState and the GUI)
 * @author zhaojiang chang - 300282984
 *
 */
public class NetworkController {
	private static Client client;
	private static GUI gui;
	private static GameState state;
	private GameView gameView;
	private static Controller controller;
	/**
	 * Constructor - creates a Network Controller for GameState/GUI/Client interaction in this game
	 * @param state the GameState of this game
	 * @param gui the GUI for this game
	 * @param client the Client for the game
	 */
	public NetworkController(Controller controller){
		this.controller = controller;
	}

	/**
	 * Receive action from gameView(user input) then create a new move package to send to server then broadcast to all other client
	 *  @param player - current player  
	 *  @param point -  the new position move to
	 */
	public static void movePlayer(Player player, Point point){
		Packet03Move move = new Packet03Move(player.getName(),point);
		move.writeData(client);

		controller.movePlayer(player, point);
		
	}
	/**
	 * Receive action from server then move other player (current may able to see other player movement 
	 *  @param player - other player  
	 *  @param point -  the new position move to
	 */
	public static void moveOtherPlayer(Player player, Point point){
		System.out.println("NetworkController->moveOtherPlayer "+ point.x+" "+point.y);
		controller.moveOtherPlayer(player, point);
	}
	/**
	 * pass gameView from GUI
	 *  @param set gameView 

	 */
	public void setGameView(GLJPanel gameView) {
		this.gameView = (GameView)gameView;
	}
	/**
	 * set client from GUI
	 *  @param set client 

	 */
	public void setClient(Client client) {
		this.client = client;
	}
	/**
	 * server send the serialized data to server then through the networkController pass to controller then update the state
	 *  @param realData - 

	 */
	public GameState deserialize(byte[] realData) {
		return controller.deserialize(realData);
	}

	public void setState(GameState st) {
		controller.setState(st);

	}

	public Player getPlayer(String username) {
		return controller.getPlayer(username);
	}
	/**
	 * check server connection
	 *  @param boolean value - return true means connect to the server 

	 */
	public void setConnection(boolean connection) {
		controller.setConnection(connection);
		
	}

	/**
	 * Teleports a given Player to a user selected floor
	 * @param p the Player to Teleport
	 * @return the user selected floor
	 */
	public static boolean teleport(Player p){
		System.out.println("teleport called");
		return controller.teleport(p);
	}

}
