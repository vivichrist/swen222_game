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
					server = new Server();
					server.start();
					
				//}
			}
			player = new MultyPlayer(playerName, new Point(18,23),
					null,null, -1,null);
			client = new Client( "localhost");
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
	public static void main(String[] args){
		Main main = new Main();
		main.startGame();
	}
}
