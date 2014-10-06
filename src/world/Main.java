package world;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import ServerClients.Client;
import ServerClients.Server;
import ServerClients.UDPpackets.Packet00Login;
import window.components.GUI;
import world.components.Map;
import world.components.TokenType;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.MultyPlayer;
import world.game.Player;
import world.game.TokenList;

public class Main {
	private Server server = null;
	private Client client;
	private String playerName;
	private GameState state;
	private MultyPlayer player;
	private Map[] floors;
	private TokenType TokenType;
	public void startGame(){
		GUI gui = new GUI();
		//gui.setName("Jacky");
		playerName = gui.getName();
		System.out.println("11 Main: " +playerName +"palyer: "+player.getName());

		if(gui.getName()!=null){
			if(!server.serverStart){
				//if (JOptionPane.showConfirmDialog(null, "Do you want to run the server", null, 0) == 0) {
					createState();
					server = new Server();
					server.start();
					
				//}
			}
			player = new MultyPlayer(playerName, new Point(18,23),
					null,null, -1,null);
			placePlayer();
			client = new Client(state, "localhost");
			client.start();
			System.out.println("Main: " +playerName +"palyer: "+player.getName());
			Packet00Login loginPacket = new Packet00Login(playerName, player.getPosition(),null);
			if (server != null) {
				server.addConnection(player, loginPacket);
			}
			loginPacket.writeData(client);
			if (server.getConnectedPlayers().size()==2) {
				GameBuilder builder = new GameBuilder(server.getPlayerNames());
				builder.getGameState();
			}
		}
	}
	private void createState(){
		ArrayList<Player>players = new ArrayList<Player>();
		Map[] floors = new Map[5];
		buildFloors(5);
		placeTokens();
		state = new GameState(players,floors);
	}
	private void buildFloors(int floorCount){
		floors = new Map[floorCount];
		for(int i = 0; i < floorCount; i++){
			//TODO: Create multiple map files and update this method to build each floor from a different map file - currently builds all identical floors
			floors[i] = new Map(new File("map1.txt"));
		}
	}

	//TODO: do we randomise start positions of players? or hard code?  this method currently places each player in the same position on different floors
	/**
	 * Places each Player in the game world, setting their start position to a hard coded floor and x/y coordinate.
	 * Players are placed at the same Point on different floors.  
	 */
	private void placePlayer(){
		player.setPosition(18,  20);
		player.setFloor(floors[0]);
		floors[0].placePlayer(new Point(18, 20), player);
	}


	/**
	 * Distributes Player's Tokens throughout the game world, choosing floors and Points at random
	 */


	private void placeTokens(){
		TokenList tokens = new TokenList(TokenType);
		for(int i = 0; i<5; i++){
			for(int j = 0; j < tokens.size(); j++){
				Random random = new Random();
				// Select a random floor in this world
				Map randomFloor = floors[random.nextInt(floors.length)];
				// Place the Token in a random cell on the floor
				randomFloor.addGameToken(randomFloor.randomEmptyCell(), tokens.get(j));
				System.out.println(tokens.get(j).toString());
			}
		}
	}

	public static void main(String[] args){
		Main main = new Main();
		main.startGame();
	}
}
