package controllers;

import window.components.GUI;
import world.game.GameState;

/**
 * A Controller to handle interactions between the GameState and the GUI
 * @author Kalo Pilato - ID:300313803
 *
 */
public class Controller {

	private GameState state;
	private GUI gui;
	
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
	public void refreshTokenPanel(){
		gui.redrawCollectItemCanvas();
	}
	
	/**
	 * Redraws the Inventory panel in the GUI - use for updating the GUI when a Player collects a MoveableObject
	 */
	public void refreshInventoryPanel(){
		gui.redrawUsefulItemCanvas();
	}
}
