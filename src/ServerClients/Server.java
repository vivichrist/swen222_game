/**
 * 
 */
package ServerClients;

import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import ServerClients.UDPpackets.Packet00Login;
import ServerClients.UDPpackets.Packet01Disconnect;
import ServerClients.UDPpackets.Packet02Data;
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
	private static final int SERVER_PORT = 3000;
	private DatagramSocket socket;
	private GameState state;
	private List<MultyPlayer> connectedPlayers = new ArrayList<MultyPlayer>();
	public boolean serverStart = false;


	public Server(GameState state){
		this.state = state;
		try {
			this.socket = new DatagramSocket(SERVER_PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		while(connectedPlayers.size()<3){
			byte[]data = new byte[20000];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try{
				//System.out.println("server >>run()>>before socket receive packet");

				socket.receive(packet);
				//System.out.println("server >>run()>>after socket receive packet"+packet.getSocketAddress()+packet.getPort());

			}catch(IOException e){
				//System.out.println("server >>run()>>socket did not receive packet IOException catched");

				e.printStackTrace();
			}
			this.parsePacketLogin(packet.getData(), packet.getAddress(), packet.getPort());
		}
		
	}

	public void run(){
		serverStart = true;
		while(true){
			
			byte[]data = new byte[20000];
			//System.out.println("server >>run()--"+data.length);

			DatagramPacket packet = new DatagramPacket(data, data.length);
			try{
				//System.out.println("server >>run()>>before socket receive packet");

				socket.receive(packet);
				//System.out.println("server >>run()>>after socket receive packet"+packet.getSocketAddress()+packet.getPort());

			}catch(IOException e){
				//System.out.println("server >>run()>>socket did not receive packet IOException catched");

				e.printStackTrace();
			}
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
			//System.out.println("server >>run()>>after socket receive packet"+packet.getSocketAddress()+packet.getPort());
		}


	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		System.out.println("server>>parsePacket");
		String message = new String(data).trim();
		PacketTypes type = UDPPakcet.lookupPacket(message.substring(0,2));
		UDPPakcet packet = null;
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
			System.out.println("Server>parsePacket>LOGIN....");
			packet = new Packet00Login(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet00Login) packet).getUsername() + " has connected...");
			MultyPlayer player = new MultyPlayer( ((Packet00Login) packet).getUsername(),new Point(18,20), null, address, port,null);
			this.addConnection(player, (Packet00Login) packet);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet01Disconnect) packet).getUsername() + " has left...");
			this.removeConnection((Packet01Disconnect) packet);
			break;
		case DATA:
			packet = new Packet02Data(data);
			this.handleData(((Packet02Data) packet));
			
		}
	}

	private void parsePacketLogin(byte[] data, InetAddress address, int port) {
		System.out.println("server>>parsePacketLogin");
		String message = new String(data).trim();
		PacketTypes type = UDPPakcet.lookupPacket(message.substring(0,2));
		UDPPakcet packet = null;
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
			System.out.println("Server>parsePacket>LOGIN....");
			packet = new Packet00Login(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet00Login) packet).getUsername() + " has connected...");
			MultyPlayer player = new MultyPlayer( ((Packet00Login) packet).getUsername(),new Point(18,20), null, address, port,null);
			this.addConnection(player, (Packet00Login) packet);
			break;
		}
	}
	private void sendData(byte[]data, InetAddress ipAddress, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, SERVER_PORT);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void sendDataToAllClients(byte[] data) {
		for (MultyPlayer p : connectedPlayers) {
			sendData(data, p.ipAddress, p.port);
			System.out.println(p.ipAddress+ "  "+p.port);
		}
	}
	public MultyPlayer getPlayerMP(String username) {
		for (MultyPlayer player : this.connectedPlayers) {
			if (player.getName().equals(username)) {
				System.out.println(player.getPosition().x+"   "+player.getPosition().y);
				return player;
			}
		}
		return null;
	}
	   public int getPlayerMPIndex(String username) {
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
		if (getPlayerMP(packet.getUsername()) != null) {
			int index = getPlayerMPIndex(packet.getUsername());
			MultyPlayer player = this.connectedPlayers.get(index);
			player.setPosition(packet.getPosition());
			player.setFloor(packet.getFloor());
			
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
            } else {
                // relay to the current connected player that there is a new
                // player
                sendData(packet.getData(), p.ipAddress, p.port);

                // relay to the new player that the currently connect player
                // exists
                packet = new Packet00Login(p.getName(), p.getPosition(), p.getFloor());
                sendData(packet.getData(), player.ipAddress, player.port);
            }
        }
        if (!alreadyConnected) {
            this.connectedPlayers.add(player);
            for(Player p: connectedPlayers){
            	System.out.println(p.getName()+" "+p.getFloor()+"  "+p.getPosition().x+"  "+p.getPosition().y);
            }
        }
    }
	

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
        packet.writeData(this);
    }

	public List<MultyPlayer> getConnectedPlayers() {
		return connectedPlayers;
	}
    

}

