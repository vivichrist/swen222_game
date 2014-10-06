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

	public Client client=null;
	public Server server =null;
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

		String playerName = "Jacky";
		String playerName2 = "sisi";
		MultyPlayer player1 = new MultyPlayer(playerName, new Point(18,23),
				null,null, -1,null);
		MultyPlayer player2 = new MultyPlayer(playerName2, new Point(38,23),
				null,null, -1,null);
		System.out.println("b "+ player1.getName()+"  "+player2.getName());

		checkConnection(player1,player2);

	}

	private void checkConnection(MultyPlayer player1, MultyPlayer player2){
		if (JOptionPane.showConfirmDialog(frame, "Do you want to run the server") == 0) {

			server = new Server();
			System.out.println("new server created");
			server.start();
			System.out.println(server.getServerStart());
		}
		System.out.println("server started");

		client = new Client("localhost");
		System.out.println("new client created");

		client.start();
		System.out.println("client started");
		if(client==null)System.out.println("client==null");

		Packet00Login loginPacket = new Packet00Login(player1.getName(), player1.getPosition(),null);
		System.out.println("loginPacket: "+loginPacket.getUsername());
		Packet00Login loginPacket2 = new Packet00Login(player2.getName(), player2.getPosition(),null);
		System.out.println("loginPacket2: "+loginPacket2.getUsername());


		loginPacket.writeData(client);
		loginPacket2.writeData(client);
		if (server.serverStart) {
			server.addConnection(player1, loginPacket);
			int size1 = server.getConnectedPlayers().size();
			server.addConnection(player2, loginPacket2);
			int size2 = server.getConnectedPlayers().size();
			System.out.println("size1: "+size1+" size2: "+size2);

		}else System.out.println("server== null");

		if (server.getConnectedPlayers().size()==2) {
			ArrayList<String>names = new ArrayList<String>();
			names.add(player1.getName());
			names.add(player2.getName());
			GameBuilder builder =new GameBuilder(names);
			client.setState( builder.getGameState());
		}

		System.out.println("size:  "+server.getConnectedPlayers().size());


	}
	public static void main(String []args){
		test t = new test();
		t.testServerPlayerListName();
	}
}
