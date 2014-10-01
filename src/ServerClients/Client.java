package ServerClients;

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

import javax.swing.JOptionPane;

import world.game.GameBuilder;
/**
 * @author  Zhaojiang Chang
 * 
 * */
public class Client extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private static final int SERVER_PORT = 3000;
	private GameBuilder game;
	private PrintStream ps;
	private BufferedReader brServer;
	private BufferedReader brClient;
	//public Client(GameBuilder game, String ipAddress){
	public Client(String ipAddress){
		//this.game = game;
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
			byte[]data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try{
				socket.receive(packet);
			}catch(IOException e){
				e.printStackTrace();
			}
			System.out.println("Server >" +new String(packet.getData()));
			}
	
		
	}
	private void sendData(byte[]data){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, SERVER_PORT);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public static void main(String[]arg0){
		Client c = new Client("127.0.0.1");
		c.start();
	}
}
		
