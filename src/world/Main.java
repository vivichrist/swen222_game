package world;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import ServerClients.Client;
import ServerClients.Server;
import ServerClients.UDPpackets.Packet00Login;
import window.components.GUI;
import world.components.Map;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.MultyPlayer;
import world.game.Player;

public class Main {
	private Server server;
	private Client client;
	private String playerName;
	private GameState state;
	private MultyPlayer player;
	private Map[] floors;
	private boolean serverStart = false;
	public void startGame(){
		GUI gui = new GUI();
		playerName = gui.getName();
		player = new MultyPlayer(playerName, new Point(18,23),
				null,null, -1,null);
		if(server.serverStart){
			Packet00Login loginPacket = new Packet00Login(playerName, player.getPosition(),null);

		}
		else if(!server.serverStart){
			ArrayList<Player>players = new ArrayList<Player>();
			Map[] floors = new Map[5];
			buildFloors(5);
			state = new GameState(players,floors);
			server = new Server(state);
			System.out.println("GameBuilder>start().>>new server created...");
			server.start();
			System.out.println("GameBuilder>start().>>server started...");

		}
		
		
		
		Packet00Login loginPacket = new Packet00Login(playerName, player.getPosition(),null);
		
	}
	private void buildFloors(int floorCount){
		floors = new Map[floorCount];
		for(int i = 0; i < floorCount; i++){
			//TODO: Create multiple map files and update this method to build each floor from a different map file - currently builds all identical floors
			floors[i] = new Map(new File("map1.txt"));
		}
	}
	public static void main(String[] args){
		Main main = new Main();
		main.startGame();
	}
}
