package ServerClients;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import ServerClients.UDPpackets.Packet05OpenDoor;
import ServerClients.UDPpackets.Packet06PickupObject;
import ServerClients.UDPpackets.UDPPacket;
import ServerClients.UDPpackets.UDPPacket.PacketTypes;
import ui.components.GameView;
import window.components.GUI;
import world.game.GameState;
import world.game.MultyPlayer;
import world.game.Player;
/**
 * A Client to handle connection between server and player
 * @author zhaojiang chang - ID:300282984
 *
 */
public class Client extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private  int port;
	private GameState state;
	private NetworkController networkController;
	public boolean connection;
	public static boolean isConnectToServer = false;
	public String name;
	public GUI gui;
	public MultyPlayer p;
	//public Client(GameState state, String ipAddress){
	public Client(GUI gui,String name,String ipAddress,NetworkController networkController,int port){
		this.port = port;
		this.gui = gui;
		this.networkController = networkController;
		this.name = name;
		this.networkController.setClient(this);
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	/**
	 * client check the receive packet
	 * if received packet is valid then pass to parsePacket method 
	 * 
	 */
	public void run(){

		while(true){

			byte[]data = new byte[60000];
			DatagramPacket packet = new DatagramPacket(data, data.length);

			try{
				socket.receive(packet);

			}catch(IOException e){
				e.printStackTrace();
			}
			System.out.println("receive data from Server >");
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	/**
	 * the parsePacket will check the first two byte 
	 * byte will identify the type of data received
	 * 
	 * */
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = UDPPacket.lookupPacket(message.substring(0, 2));
		System.out.println("client type: "+message.substring(0,2)+ "  package size: " + message.length());

		UDPPacket packet = null;
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
			packet = new Packet02Data(data);
			handleData((Packet02Data) packet);
			break;

		case MOVE:
			packet = new Packet03Move(data);
			handleMove((Packet03Move) packet);
			break;
		
		
		case OPENDOOR:
			packet = new Packet05OpenDoor(data);
			handleOpenDoor((Packet05OpenDoor) packet);
			break;
		case PICKUP:
			packet = new Packet06PickupObject(data);
			handlePickupObject((Packet06PickupObject)packet);
			break;
		}
	}

	
	
	/**
	 * this method will send message from client to server
	 * */
	public void sendData(byte[]data){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * this method is handle login package 
	 * */
	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
				+ " has joined the game...");
		new MultyPlayer( packet.getUsername(),null,address, port);
		System.out.println("=============>" +  packet.getUsername() + address + port);
		 p = new MultyPlayer( packet.getUsername(),null,address, port);
	}

	private void handleData(Packet02Data packet) {

		GameState st = NetworkController.deserialize(packet.getRealData());
		networkController.setState(st);
		if(st.getPlayers().size()>1){
			gui.startClientWindows(name,st);
		}
	}
	private void handleOpenDoor(Packet05OpenDoor packet) {
		//Player player = (Player) this.deserialize(packet.getData());
		networkController.toOpenDoor(packet.getDoorAction(), packet.getPoint());
	}
	
	private void handlePickupObject(Packet06PickupObject packet) {
		networkController.removeObjectFromClient(packet);
	}
	
	private void handleMove(Packet03Move packet) {

		MultyPlayer p = (MultyPlayer) networkController.getPlayer(packet.getUsername());
		if(p!=null){
			if(!GUI.name.equalsIgnoreCase(p.getName())){
				networkController.moveOtherPlayer(p, packet.getPoint());
			}else{
				System.out.println("local player should not move by server");
			}
		}

	}
	
	public MultyPlayer getPlayer() {
		// TODO Auto-generated method stub
		return p;
	}

}

