package ServerClients;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controllers.NetworkController;
import ServerClients.UDPpackets.Packet00Login;
import ServerClients.UDPpackets.Packet02Data;
import ServerClients.UDPpackets.Packet03Move;
import window.components.GUI;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.MultyPlayer;
import ServerClients.Server;
import ServerClients.Client;

public class test {

	public Client client;
	public Server server;
	private JFrame frame;
	public GameState state;
	public NetworkController controller;



	public void startGame(){
		if (JOptionPane.showConfirmDialog(frame, "Do you want to run the server") == 0) {
			server = new Server(4768);
			server.start();
		}
		else{
			state = new GameState(null, null);
			MultyPlayer player1 = new MultyPlayer("Jacky",
					null,null, -1);

			MultyPlayer player2 = new MultyPlayer("Sisi",
					null,null, -1);

			GUI gui = null;
			client = new Client(gui,"Jacky","localhost",controller,4768);
			client.start();

			Packet00Login loginPacket = new Packet00Login(player1.getName());
			Packet00Login loginPacket2 = new Packet00Login(player2.getName());

//			if (server.serverStart==99) {
//				server.addConnection(player1, loginPacket);
//				int size1 = server.getConnectedPlayers().size();
//
//				System.out.println("size1: "+size1);
//
//			}else System.out.println("server== null");
			loginPacket.writeData(client);
			loginPacket2.writeData(client);
			Packet03Move move = new Packet03Move(player1.getName(),new Point(6,6));
			move.writeData(client);
			Packet03Move move2 = new Packet03Move(player2.getName(),new Point(8,8));
			move2.writeData(client);



		}


	}
	public static void main(String []args){
		test t = new test();
		t.startGame();
	}
}



