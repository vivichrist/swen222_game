package ServerClients;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.media.opengl.awt.GLJPanel;

import controllers.NetworkController;
import ServerClients.UDPpackets.Packet00Login;
import ServerClients.UDPpackets.Packet01Disconnect;
import ServerClients.UDPpackets.Packet02Data;
import ServerClients.UDPpackets.Packet03Move;
import ServerClients.UDPpackets.UDPPakcet;
import ServerClients.UDPpackets.UDPPakcet.PacketTypes;
import ui.components.GameView;
import window.components.GUI;
import world.game.GameState;
import world.game.MultyPlayer;
/**
 * @author  Zhaojiang Chang
 *
 * */
public class Client extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private static final int SERVER_PORT = 4768;
	private GameState state;
	private byte[] data;
	private GUI gui;
	private NetworkController networkController;
	private GameView gameView;
	public static boolean isConnectToServer = false;
	//public Client(GameState state, String ipAddress){
	public Client(String ipAddress,NetworkController networkController){
		this.networkController = networkController;
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public void run(){

		while(true){
//			if(Server.socket.isConnected()){
//				isConnectToServer=true;
//				System.out.println("connect to server? "+isConnectToServer);
//			}
//			else{
//				isConnectToServer=false;
//				System.out.println("connect to server? "+isConnectToServer);
//
//			}
			byte[]data = new byte[60000];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			///System.out.println("client>>run()>>new packet created");

			try{
				//System.out.println("client>>run()>>before socket receive packet");
				System.out.println("2");
				socket.receive(packet);

				System.out.println("3");

				//	System.out.println("client>>run()>>after receive packet");

			}catch(IOException e){
				//System.out.println("client>>run()>>IOException catched");
				e.printStackTrace();
			}
			System.out.println("receive data from Server >");
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());


		}


	}
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = UDPPakcet.lookupPacket(message.substring(0, 2));
		System.out.println("client type: "+message.substring(0,2)+ "  package size: " + message.length());

		UDPPakcet packet = null;
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			handleLogin((Packet00Login) packet, address, port);

			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet01Disconnect) packet).getUsername() + " has left the game...");
			break;
		case DATA:
			this.data = data;
			packet = new Packet02Data(state,data);
			handleData((Packet02Data) packet);
			break;

		case MOVE:
			packet = new Packet03Move(state,data);
			handleMove((Packet03Move) packet);
			break;
		}
	}

	public void sendData(byte[]data){
		//System.out.println("Client>>sendData");
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, SERVER_PORT);
		//System.out.println("Client>>sendData>> new packet created"+ packet.getSocketAddress());
		try {
			//System.out.println("Client>>sendData>>in try block");
			socket.send(packet);
			//System.out.println("Client>>sendData>>in try>>socket send packet");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void checkState(){

		if(state.isMoved()){
			System.out.println("player moved==========");
			byte[] data = state.serialize();
			Packet02Data dataP = new Packet02Data(state,data);
			dataP.writeData(this);
		}

	}
	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
				+ " has joined the game...");
		MultyPlayer player = new MultyPlayer( packet.getUsername(), packet.getPoint(),null,address, port);
		//player.setFloor(state.getMap());
		//state.addPlayer(player);

	}

	private void handleData(Packet02Data packet) {

		byte[] realData = new byte[packet.getData().length];
		realData = packet.getRealData();
		GameState st = state.deserialize(realData);
		state.setState(st);
		System.out.println(state.getPlayers().get(1).getName());

	}

	private void handleMove(Packet03Move packet) {

		MultyPlayer p = (MultyPlayer) state.getPlayer(packet.getUsername());
		if(p!=null){
			if(GUI.name.equalsIgnoreCase(p.getName())){
				networkController.movePlayer(p, packet.getPoint());
			}else{
				networkController.moveOtherPlayer(p, packet.getPoint());
			}
		}else{
			System.out.println("Client --->  player name not exist in the system");
		}
	}
	public void setState(GameState state){
		this.state = state;
	}
	public void setGameView(GLJPanel gameView){
		this.gameView = (GameView) gameView;
		
	}
	


}

