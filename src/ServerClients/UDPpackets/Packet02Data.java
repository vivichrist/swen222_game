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

	private byte[] data;


	public Packet02Data( String username) {
		super(02);
		System.out.println("packet02Data con 1: ");
		this.username = username;


	}

	public Packet02Data(byte[] data) {
		super(02);
		this.data = data;
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
			if(newD[i-2] != oldD[i]){
			}
			
		}
		return newD;
	}
	@Override
	public byte[] getData() {

		byte[]newData =new byte[data.length];
		byte[] a = "02".getBytes();

		newData[0] = a[0];
		newData[1] = a[1];
		for(int i = 0; i<data.length;i++){
			newData[i] = data[i];
			
		}
		return newData;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}




}
