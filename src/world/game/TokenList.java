package world.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import world.components.GameToken;

/**
 * The list of GameTokens a Player is required to collect to win the game
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class TokenList implements java.io.Serializable{

	// Defines the Color palette to use for every TokenList
	private static final Color COLORS[] = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.RED};
	
	private final List<GameToken> tokens;

	// Defines the number of tokens to be collected by each Player
	private final int tokenCount = 5;

	
	/**
	 * Constructor - creates a new TokenList, populating it with new GameTokens of a given type (Colors are assigned from the predefined COLORS array) 
	 * @param type the type of GameTokens to be used in this TokenList
	 */
	public TokenList(GameBuilder.TokenType type){
		tokens = new ArrayList<GameToken>();
		for(int i = 0; i < tokenCount; i++){
			tokens.add(new GameToken(type, COLORS[i]));
		}
	}
	
	/**
	 * Returns the specified GameToken from this list
	 * @param token the GameToken to return
	 * @return the specified GameToken
	 */
	public GameToken get(GameToken token){
		return tokens.get(tokens.indexOf(token));
	}
	
	/**
	 * Returns the GameToken at a given index position from this list
	 * @param index the index position of the GameToken to return
	 * @return the GameToken at index
	 */
	public GameToken get(int index){
		return tokens.get(index);
	}
	
	/**
	 * Returns the size of this TokenList
	 * @return the size of this TokenList
	 */
	public int size(){
		return tokens.size();
	}
}
