package ServerClients.Test;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Point;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.junit.Test;

import ServerClients.UDPpackets.Packet00Login;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.MultyPlayer;
import ServerClients.Server;
import ServerClients.Client;

public class ServerClientTest {

	private Client client;
	private Server server;
	private Component frame;
	private GameState state;
	
	
	public ServerClientTest(){
		if (JOptionPane.showConfirmDialog(frame, "Do you want to run the server") == 0) {
			server = new Server();
			server.start();
		}
		state = new GameState(null,null);

		client = new Client(state,"localhost");
		client.start();
		client.sendData(("02ping").getBytes());
		System.out.println("client started");
	}

	@Test
	public void testSendMessage(){
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testServerPlayerListName(){

		String playerName = "Jacky";
		MultyPlayer player1 = new MultyPlayer(playerName, new Point(18,23),
				null,null, -1);

		System.out.println("b "+ player1.getName());

		checkConnection(player1);
		//System.out.println("Size array: "+server.getConnectedPlayers().size()+ "player Name: "+server.getConnectedPlayers().get(0).getName());
		assertTrue(playerName.equals(server.getConnectedPlayers().get(0).getName()));
		assertEquals(1,server.getConnectedPlayers().size());

		server.stop();
		client.stop();
	}
	
	
	
	
	private void checkConnection(MultyPlayer player1){

		Packet00Login loginPacket = new Packet00Login(player1.getName(), player1.getPosition().x,player1.getPosition().y);
		System.out.println("loginPacket: "+loginPacket.getUsername());

		if (server.serverStart==99) {
			server.addConnection(player1, loginPacket);
			int size1 = server.getConnectedPlayers().size();

			System.out.println("size1: "+size1);

		}else System.out.println("server== null");
		loginPacket.writeData(client);

	}
}
