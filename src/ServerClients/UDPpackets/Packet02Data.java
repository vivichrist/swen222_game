package ServerClients.UDPpackets;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import world.components.Map;
import world.game.GameState;
import world.game.Player;
import ServerClients.Client;
import ServerClients.Server;


public class Packet02Data extends UDPPakcet {

	private String username;
	private GameState state;
	private Point point;
	private int x;
	private int y;
	private byte[] data;


	public Packet02Data( String username, int x, int y) {
		super(02);
		System.out.println("packet02Data con 1: ");

		this.username = username;
		this.x = x;
		this.y = y;
		this.point = new Point(x,y);

	}

	public Packet02Data(GameState state,byte[] data) {
		super(02);
		this.data = data;
		this.state = state;
		System.out.println("packet02Data con 2: ");
	}

	
	@Override
	public void writeData(Client client) {
		client.sendData(getData());
		System.out.println("packet02Data con 5: ");
	}

	@Override
	public void writeData(Server server) {

		System.out.println("packet02Data con 3: ");
		server.sendDataToAllClients(getData());
		
		System.out.println("packet02Data con 4: ");
	}
	public byte[] getRealData(){
		byte[]oldD = getData();
		byte[]newD =new byte[oldD.length-2];
		

		for(int i = 2; i<oldD.length;i++){
			newD[i-2] = oldD[i];
			
		}
		//if(data!=newD)System.out.println("Packet02Data: not equals");

		return newD;
	}
	@Override
	public byte[] getData() {

		byte[]newData =new byte[data.length];
		byte[] a = "02".getBytes();

		newData[0] = a[0];
		newData[1] = a[1];
		for(int i = 2; i<data.length;i++){
			newData[i] = data[i];
			if(newData[i] != data[i]){
				System.out.println("aa");
			}
		}
		return newData;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Point getPosition(){
		return point;
	}




}
