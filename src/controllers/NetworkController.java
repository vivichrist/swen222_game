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
	private Controller controller;
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
	public void movePlayer(Player player, Point point){
		controller.movePlayer(player, point);
		Packet03Move move = new Packet03Move(player.getName(),point);
		move.writeData(client);
	}


	public void moveOtherPlayer(Player player, Point point){
		controller.moveOtherPlayer(player, point);
	}

	public void setGameView(GLJPanel gameView) {
		// TODO Auto-generated method stub
		this.gameView = (GameView)gameView;


	}

	public void setClient(Client client) {
		// TODO Auto-generated method stub
		this.client = client;

	}

}
