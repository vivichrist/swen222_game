package ServerClients.UDPpackets;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import world.components.Map;
import world.game.GameState;
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
		this.state = state;
		this.data = data;
		state = getState(data);
		System.out.println("packet02Data con 2: ");
		// TODO Auto-generated constructor stub
		//state.deserialize(data);
		//		String[] dataArray = readData(data).split(",");
		//		this.username = dataArray[0];
		//		this.x = Integer.parseInt(dataArray[1]);
		//		this.y = Integer.parseInt(dataArray[2]);
		//		point = new Point(x,y);



	}

	private GameState getState(byte[] data) {
		// TODO Auto-generated method stub
		byte[]newData =new byte[data.length-2];

		for(int i = 2; i<data.length;i++){
			newData[i-2] = data[i];
		}
		GameState obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream (newData);
			ObjectInputStream ois = new ObjectInputStream (bis);
			obj = (GameState) ois.readObject();
		}
		catch (IOException ex) {
			//TODO: Handle the exception
		}
		catch (ClassNotFoundException ex) {
			//TODO: Handle the exception
		}
		return obj;
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
		byte[]newData =new byte[2+data.length];
		byte[] realData = data;

		byte[]newD =new byte[realData.length];

		for(int i = 2; i<realData.length;i++){
			newD[i-2] = realData[i];
		}
			
			return newData;
		}
		@Override
		public byte[] getData() {

			byte[]newData =new byte[2+data.length];
			newData[0] = '0';
			newData[1] = '2';
			for(int i = 2; i<data.length;i++){
				newData[i] = data[i-2];
			}
			
			return newData;
			// 	return ("02" + this.username + "," + this.x + "," + this.y).getBytes();

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
