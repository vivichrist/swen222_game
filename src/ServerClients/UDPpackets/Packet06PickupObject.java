package ServerClients.UDPpackets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import world.components.MoveableObject;
import world.game.Player;
import ServerClients.Client;
import ServerClients.Server;

public class Packet06PickupObject extends UDPPakcet {

	private String username;
	private byte[] data;
	
	private Player player;
	/**
	 * Constructor - create a Packet06PickupObject package
	 * @param player  - player
	 * @param object - object to pickup
	 
	 */
	public Packet06PickupObject(Player player, MoveableObject object) {
		super(06);
		this.player = player;

	}
	/**
	 * Constructor - creates a Packet05OpenDoor package
	 * @param data  - received from server and change byte array to string message
	 */
	public Packet06PickupObject(byte[] data) {
		super(06);
		this.data = data;
		System.out.println("Packet06PickupObject con 2: ");
	}


	/**
	 * writeData(client) - this method is going to send data from client to server
	 * @param client - once openDoor package created will call this method to send data to client
	 */
	@Override
	public void writeData(Client client) {
		client.sendData(getData());
		System.out.println("Packet06PickupObject con 3: ");

	}

	/**
	 * writeData(server) - this method is going to send data from server to all client (except current player)
	 * @param server - once package received from client and broadcast to all client
	 */
	@Override
	public void writeData(Server server) {
		System.out.println("Packet06PickupObject con 4: ");
		server.sendDataToAllClients(getData());

		System.out.println("Packet06PickupObject con 5: ");
	}
	/**
	 * getRealData - this method is going to return a bytes array with door open message and location
	 * @return byte array
	 */

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
	/**
	 * getData - this method is going to return a bytes array with PacketTypes and door open message and location
	 * @return byte array
	 */
	@Override
	public byte[] getData() {

		byte[]newData =new byte[data.length];
		byte[] a = "06".getBytes();

		newData[0] = a[0];
		newData[1] = a[1];
		for(int i = 0; i<data.length;i++){
			newData[i] = data[i];

		}
		return newData;
	}

	/**
	 * 
	 * serialize the player into byte code
	 * */

	public byte[] serialize(Object obj) {

		byte[] bytes = new byte[60000];
		try {
			//object to bytearray
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(obj);
			bytes = baos.toByteArray();
			out.flush();
			baos.close();
			out.close();
			return bytes;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * deserialize the player 
	 * 
	 * */
	public Object deserialize(byte[]bytes) {

		ByteArrayInputStream bais = null;
		ObjectInputStream in = null;
		try{
			bais = new ByteArrayInputStream(bytes);
			in = new ObjectInputStream(bais);
			Object obj =  in.readObject();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				bais.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

}


