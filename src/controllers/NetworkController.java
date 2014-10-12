package controllers;

import java.awt.Point;

import javax.media.opengl.awt.GLJPanel;

import ServerClients.Client;
import ServerClients.UDPpackets.Packet03Move;
import ui.components.GameView;
import window.components.GUI;
import world.game.GameState;
import world.game.Player;

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
	 * Redraws the GameToken panel in the GUI - use for updating the GUI when a Player collects a GameToken
	 */
	public static void movePlayer(Player player, Point point){
		controller.movePlayer(player, point);
		Packet03Move move = new Packet03Move(player.getName(),point);
		move.writeData(client);
	}


	public static void moveOtherPlayer(Player player, Point point){
		controller.moveOtherPlayer(player, point);
	}

	public void setGameView(GLJPanel gameView) {
		this.gameView = (GameView)gameView;


	}

	public void setClient(Client client) {
		this.client = client;
	}
	public GameState deserialize(byte[] realData) {
		return controller.deserialize(realData);
	}

	public void setState(GameState st) {
		controller.setState(st);

	}

	public Player getPlayer(String username) {
		return controller.getPlayer(username);
	}

	public void setConnection(boolean connection) {
		// TODO Auto-generated method stub
		controller.setConnection(connection);
		
	}

	/**
	 * Teleports a given Player to a user selected floor
	 * @param p the Player to Teleport
	 * @return the user selected floor
	 */
	public static boolean teleport(Player p){
		int newFloor = gui.getFloor(state.floorCount());
		return controller.teleport(p, newFloor);
	}

}
