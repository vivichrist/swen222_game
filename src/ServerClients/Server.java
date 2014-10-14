/**
 *
 */
package ServerClients;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import ServerClients.UDPpackets.Packet00Login;
import ServerClients.UDPpackets.Packet01Disconnect;
import ServerClients.UDPpackets.Packet02Data;
import ServerClients.UDPpackets.Packet03Move;
import ServerClients.UDPpackets.Packet04Connection;
import ServerClients.UDPpackets.Packet05OpenDoor;
import ServerClients.UDPpackets.Packet06PickupObject;
import ServerClients.UDPpackets.UDPPakcet;
import ServerClients.UDPpackets.UDPPakcet.PacketTypes;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.MultyPlayer;
import world.game.Player;

/**
 * @author  Zhaojiang Chang
 *
 */
public class Server extends Thread {
	static DatagramSocket socket;
	public Client client;
	//private GameState state;
	private List<MultyPlayer> connectedPlayers;
	private GameState state;
	private boolean serverOpen = false;
	private String name;
	private int portNumber;
	public static int serverStart = 0;


	public Server(int portNumber){
		this.portNumber = portNumber;
		connectedPlayers = new ArrayList<MultyPlayer>();

		//this.state = state;
		try {
			this.socket = new DatagramSocket(portNumber);
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("server socket"+ e);
		}


	}

	public void run(){

		while(true){
//			if(connectedPlayers.size()>1){
//
//					sentStateToAllClients();
//				
//			}

			this.serverStart = 99;
			System.out.println("Server Start>>>>>>>>>>"+ serverStart);

			byte[]data = new byte[60000];

			DatagramPacket packet = new DatagramPacket(data, data.length);
			try{
				if(connectedPlayers.size()<1){

					Packet04Connection pack = new Packet04Connection("false");
					pack.writeData(this);
				}else{
					Packet04Connection pack = new Packet04Connection("true");
					pack.writeData(this);

				}

				socket.receive(packet);


			}catch(IOException e){

				e.printStackTrace();
			}

			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
			System.out.println("server class connected players size:  "+connectedPlayers.size());
		}


	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		//System.out.println("bbb9");

		//System.out.println("server>>parsePacket");
		String message = new String(data).trim();
		PacketTypes type = UDPPakcet.lookupPacket(message.substring(0,2));
		System.out.println("server type: "+message.substring(0,2));
		UDPPakcet packet = null;
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
			//System.out.println("Server>parsePacket>LOGIN....");
			packet = new Packet00Login(data);
			name = ((Packet00Login) packet).getUsername();
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet00Login) packet).getUsername() + " has connected...");
			MultyPlayer player = new MultyPlayer( ((Packet00Login) packet).getUsername(), null, address, port);
			this.addConnection(player, (Packet00Login) packet);
			System.out.println("Server>parsePacket>LOGIN seccucssfully");
			if(connectedPlayers.size()==2 && serverOpen==false){
					sentStateToAllClients();
			}
				break;
			case DISCONNECT:
				packet = new Packet01Disconnect(data);
				System.out.println("[" + address.getHostAddress() + ":" + port + "] "
						+ ((Packet01Disconnect) packet).getUsername() + " has left...");
				this.removeConnection((Packet01Disconnect) packet);
				break;
			case DATA:
				packet = new Packet02Data(data);
				name = ((Packet02Data) packet).getUsername();//may through a exception 
				this.handleData(((Packet02Data) packet));
				break;
			case MOVE:
				packet = new Packet03Move(data);
				name = ((Packet03Move) packet).getUsername();
				handleMove((Packet03Move) packet);
				break;
			case OPENDOOR:
				packet = new Packet05OpenDoor(data);
				handleOpenDoor((Packet05OpenDoor) packet);
				break;
			case PICKUP:
				packet = new Packet06PickupObject(data);
				handlePickupObject((Packet06PickupObject)packet);
				
			}
		}




		

		private void sentStateToAllClients() {

			System.out.println("serverOpen = "+ serverOpen);
			ArrayList<String>names = new ArrayList<String>();
			for(MultyPlayer p:connectedPlayers ){
				names.add(p.getName());
			}
			for(String name: names){
				System.out.println("names: "+ name);
			}
			System.out.println("new gamebuilder builded");
			GameBuilder builder =new GameBuilder(names);
			state = builder.getGameState();

			byte[] temp =state.serialize();

			byte[]newData =new byte[temp.length+2];
			byte[] b = "02".getBytes();
			newData[0] = b[0];
			newData[1] = b[1];

			for(int i = 0; i<temp.length;i++){
				newData[i+2] = temp[i];
			}
			Packet02Data p = new Packet02Data(newData);
			p.writeData(this);
			serverOpen  = true;


		}

		private void sendData(byte[]data, InetAddress ipAddress, int port){
			DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void sendActionDataToAllClients(byte[] data) {
			for (MultyPlayer p : connectedPlayers) {
				System.out.println(name+"  "+ p.getName());
				if(!name.equals(p.getName())){
					sendData(data, p.ipAddress, p.port);
					System.out.println(p.ipAddress+ "  "+p.port);
				}
			}

		}
		public void sendDataToAllClients(byte[] data) {
			for (MultyPlayer p : connectedPlayers) {
				//System.out.println(name+"  "+ p.getName());
				//if(!name.equals(p.getName())){
				sendData(data, p.ipAddress, p.port);
				//System.out.println(p.ipAddress+ "  "+p.port);

			}

		}
		public MultyPlayer getPlayer(String username) {
			for (MultyPlayer player : this.connectedPlayers) {
				if (player.getName().equals(username)) {
					//System.out.println(player.getPosition().x+"   "+player.getPosition().y);
					return player;
				}
			}
			return null;
		}
		public int getPlayerIndex(String username) {
			int index = 0;
			for (MultyPlayer player : this.connectedPlayers) {
				if (player.getName().equals(username)) {
					break;
				}
				index++;
			}
			return index;
		}
		private void handleData(Packet02Data packet) {

			byte[] temp = packet.getData();
			Packet02Data pk = new Packet02Data(temp);
			packet.writeData(this);

		}
		private void handleOpenDoor(Packet05OpenDoor packet) {
			byte[] temp = packet.getData();
			Packet05OpenDoor pk = new Packet05OpenDoor(temp);
			packet.writeData(this);
		}		
		private void handlePickupObject(Packet06PickupObject packet) {
			byte[] temp = packet.getData();
			Packet06PickupObject pk = new Packet06PickupObject(temp);
			pk.writeData(this);
			
		}
		private void handleMove(Packet03Move packet) {
			if(getPlayer(packet.getUsername())!=null){
				int index = getPlayerIndex(packet.getUsername());
				MultyPlayer player = this.connectedPlayers.get(index);
				state.movePlayer((Player)player, packet.getPoint());
				packet.writeData(this);
			}

		}
		public void addConnection(MultyPlayer player, Packet00Login packet) {
			boolean alreadyConnected = false;
			for (MultyPlayer p : this.connectedPlayers) {
				if (player.getName().equalsIgnoreCase(p.getName())) {
					if (p.ipAddress == null) {
						p.ipAddress = player.ipAddress;
					}
					if (p.port == -1) {
						p.port = player.port;
					}
					alreadyConnected = true;
					System.out.println("allrady connected");
				}
				else {
					//relay to the current connected player that there is a new player
					sendData(packet.getData(), p.ipAddress, p.port);

					// relay to the new player that the currently connect player exists
					packet = new Packet00Login(p.getName());
					sendData(packet.getData(), player.ipAddress, player.port);
				}
			}
			if (!alreadyConnected) {
				this.connectedPlayers.add(player);
				System.out.println("palyer: "+ player.getName());

				for(MultyPlayer p: connectedPlayers){
					System.out.println(p.getName()+" "+" "+p.ipAddress+ "  "+ p.port);
				}
			}
		}
		public ArrayList<String> getPlayerNames(){
			ArrayList<String>names = new ArrayList<String>();
			for(MultyPlayer p: connectedPlayers){
				names.add(p.getName());

			}
			return names;
		}

		public void removeConnection(Packet01Disconnect packet) {
			this.connectedPlayers.remove(getPlayerIndex(packet.getUsername()));
			packet.writeData(this);
		}

		public List<MultyPlayer> getConnectedPlayers() {
			return connectedPlayers;
		}
		public int getServerStart(){
			System.out.println("GetServerStart: "+ this.serverStart);
			return this.serverStart;
		}
		public void setServerStart(int num){
			this.serverStart = num;
		}
		}

