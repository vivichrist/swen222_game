package ServerClients;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.swing.JOptionPane;

import ServerClients.UDPpackets.Packet00Login;
import ServerClients.UDPpackets.Packet01Disconnect;
import ServerClients.UDPpackets.Packet02Data;
import ServerClients.UDPpackets.UDPPakcet;
import ServerClients.UDPpackets.UDPPakcet.PacketTypes;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.PlayerMP;
/**
 * @author  Zhaojiang Chang
 * 
 * */
public class Client extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private static final int SERVER_PORT = 3000;
	private GameState state;
	public Client(GameState state, String ipAddress){
		this.state = state;
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
			//System.out.println("client>>run()");
			byte[]data = new byte[20000];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			///System.out.println("client>>run()>>new packet created");

			try{
				//System.out.println("client>>run()>>before socket receive packet");
				socket.receive(packet);
			//	System.out.println("client>>run()>>after receive packet");

			}catch(IOException e){
				//System.out.println("client>>run()>>IOException catched");

				e.printStackTrace();
			}
			//System.out.println("receive data from Server >" +new String(packet.getData()));
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());

		}


	}
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = UDPPakcet.lookupPacket(message.substring(0, 2));
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
			packet = new Packet02Data(data);
			handleData((Packet02Data) packet);
			

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
	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
				+ " has joined the game...");
		new PlayerMP( packet.getUsername(), packet.getPoint(),null,address, port,0);
	}

	private void handleData(Packet02Data packet) {

		byte[] realData = Arrays.copyOf( packet.getData(), 20000 );
		state.deserialize(realData);
	}
	
}

