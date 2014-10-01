/**
 * 
 */
package ServerClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import world.game.GameBuilder;

/**
 * @author  Zhaojiang Chang
 *
 */
public class Server extends Thread {
	private static final int SERVER_PORT = 3000;
	private DatagramSocket socket;
	private GameBuilder game;

	//public Server(GameBuilder game){
	//	this.game = game;
	public Server(){
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		} 
	}
	public void run(){

		while(true){
			byte[]data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try{
				socket.receive(packet);
			}catch(IOException e){
				e.printStackTrace();
			}
			String message = new String(packet.getData());
			if(message.equalsIgnoreCase("ping")){
				System.out.println("CLIENT > "+ new String(packet.getData()));
				sendData("Pong".getBytes(), packet.getAddress(), packet.getPort());
			}
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
	public static void main(String[]arg0){
		Server c = new Server();
	}
}

