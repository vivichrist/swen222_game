package ServerClients;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ServerClients.UDPpackets.Packet00Login;
import ServerClients.UDPpackets.Packet02Data;
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



	public void startGame(){
		if (JOptionPane.showConfirmDialog(frame, "Do you want to run the server") == 0) {
			server = new Server();
			server.start();
		}
		else{
			state = new GameState(null, null);
			MultyPlayer player1 = new MultyPlayer(JOptionPane.showInputDialog(this, "Please enter a username"), new Point(18,20),
					null,null, -1);


			client = new Client(state,"localhost");
			client.start();
			
			Packet00Login loginPacket = new Packet00Login(player1.getName(), player1.getPosition().x,player1.getPosition().y);

			if (server.serverStart==99) {
				server.addConnection(player1, loginPacket);
				int size1 = server.getConnectedPlayers().size();
				
				System.out.println("size1: "+size1);

			}else System.out.println("server== null");
			loginPacket.writeData(client);
			
		}


	}
	public static void main(String []args){
		test t = new test();
		t.startGame();
	}
}
