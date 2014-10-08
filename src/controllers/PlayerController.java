package controllers;

import window.components.GUI;
import world.game.GameState;

public class PlayerController {

	private GameState state;
	private GUI gui;
	
	public PlayerController(GameState state, GUI gui){
		this.state = state;
		this.gui = gui;
	}
}
