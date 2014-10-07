package ServerClients;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ServerClients.UDPpackets.Packet00Login;
import world.game.GameBuilder;
import world.game.MultyPlayer;
import ServerClients.Server;
import ServerClients.Client;

public class test {

	public Client client;
	public Server server;
	private JFrame frame;

	//	@Test
	//	public void testAddPlayers(){
	//		System.out.println("aaa");
	//		String playerName = "Jacky";
	//		String playerName2 = "sisi";
	//		MultyPlayer player1 = new MultyPlayer(playerName, new Point(18,23),
	//				null,null, -1,null);
	//		MultyPlayer player2 = new MultyPlayer(playerName2, new Point(38,23),
	//				null,null, -1,null);
	//		checkConnection(player1,player2);
	//		if(server==null)System.out.println("server==null");
	//		assertEquals(2,server.getConnectedPlayers().size());
	//
	//	}

	public void testServerPlayerListName(){

	
		MultyPlayer player1 = new MultyPlayer(JOptionPane.showInputDialog(this, "Please enter a username"), new Point(18,23),
				null,null, -1);

		System.out.println("b "+ player1.getName());

		checkConnection(player1);

	}

	private void checkConnection(MultyPlayer player1){
		if (JOptionPane.showConfirmDialog(frame, "Do you want to run the server") == 0) {
			server = new Server();
			server.start();
		}

		client = new Client("localhost");

		client.start();
		System.out.println("client started");

		Packet00Login loginPacket = new Packet00Login(player1.getName(), player1.getPosition().x,player1.getPosition().y);
	//	System.out.println("loginPacket: "+loginPacket.getUsername());



	//	System.out.println("serverStart: "+server.serverStart);
		if (server.serverStart==99) {
			server.addConnection(player1, loginPacket);
			int size1 = server.getConnectedPlayers().size();

			System.out.println("size1: "+size1);

		}else System.out.println("server== null");
		loginPacket.writeData(client);

//		if (server.getConnectedPlayers().size()==2) {
//			ArrayList<String>names = new ArrayList<String>();
//			names.add(player1.getName());
//			GameBuilder builder =new GameBuilder(names);
//			client.setState( builder.getGameState());
//		}



	}
	public static void main(String []args){
		test t = new test();
		t.testServerPlayerListName();
	}
}
