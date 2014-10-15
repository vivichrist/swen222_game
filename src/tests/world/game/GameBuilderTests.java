package tests.world.game;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import world.components.TokenType;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.Player;

/**
 * Tests for the GameBuilder class
 * @author Kalo Pilato - ID:300313803
 *
 */
public class GameBuilderTests {

	/**
	 * A newly constructed single player game should have only one Player
	 */
	@Test public void validConstructor1(){
		GameState state = new GameBuilder("Test Player").getGameState();
		assertTrue(state.getPlayers().size() == 1);
	}
	
	/**
	 * A newly constructed single player game should have only one floor
	 */
	@Test public void validConstructor2(){
		GameState state = new GameBuilder("Test Player").getGameState();
		assertTrue(state.floorCount() == 1);
	}
	
	/**
	 * A newly constructed multiplayer game should have one Player per player name
	 */
	@Test public void validConstructor3(){
		ArrayList<String> pNames = new ArrayList<String>();
		pNames.add("Dave");
		pNames.add("Stephen");
		pNames.add("Marco");
		pNames.add("BrainStackOverflow");
		GameState state = new GameBuilder(pNames).getGameState();
		assertTrue(state.getPlayers().size() == pNames.size());
	}
	
	/**
	 * A newly constructed multiplayer game should have only one floor per player
	 */
	@Test public void validConstructor4(){
		ArrayList<String> pNames = new ArrayList<String>();
		pNames.add("Dave");
		pNames.add("Stephen");
		pNames.add("Marco");
		pNames.add("BrainStackOverflow");
		GameState state = new GameBuilder(pNames).getGameState();
		assertTrue(state.floorCount() == pNames.size());
	}
	
	/**
	 * Checks whether a player move in a single player came is correctly completed
	 */
	@Test public void movePlayer1(){
		GameState state = new GameBuilder("Test Player").getGameState();
		Player p = state.getPlayer(0);
		Point destination = new Point(3, 15);
		state.movePlayer(p, destination);
		assertTrue(p.getPosition().equals(destination));
	}
	
	/**
	 * Checks whether a player move in a multiplayer came is correctly completed
	 */
	@Test public void movePlayer2(){
		ArrayList<String> pNames = new ArrayList<String>();
		pNames.add("Dave");
		pNames.add("Stephen");
		pNames.add("Marco");
		pNames.add("BrainStackOverflow");
		GameState state = new GameBuilder(pNames).getGameState();
		Player p = state.getPlayer(2);
		Point destination = new Point(3, 15);
		state.movePlayer(p, destination);
		assertTrue(p.getPosition().equals(destination));
	}
}
