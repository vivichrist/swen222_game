package ServerClients.UDPpackets;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import world.components.Map;
import ServerClients.Client;
import ServerClients.Server;



public class Packet00Login extends UDPPakcet {

	private String username;
	private int x, y;
	private Point point;
	private Map floor;

	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		point = new Point(x,y);
		byte[] temp = new byte [data.length-3];
		for(int i = 3; i<data.length; i++){
			temp[i-3] = data[i];
		}
		ByteArrayInputStream bi = new ByteArrayInputStream(temp);
		ObjectInputStream oi;
		try {
			oi = new ObjectInputStream(bi);

			floor = (Map) oi.readObject();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}

	public Packet00Login(String username, Point point, Map floor) {
		super(00);
		this.username = username;
		this.point = point;
		this.floor = floor;
	}

	@Override
	public void writeData(Client client) {
		//System.out.println("Packet00Login>writeData....");
		if(client==null)System.out.println("null client");
		//System.out.println("getdata().toString(): "+getData().length);
		//client.start();
		client.sendData(getData());
		System.out.println("Packet00Login>writeData.>>client sendData...");

	}

	@Override
	public void writeData(Server server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		//System.out.println("packet00Login>>getData..."+username+"  "+point.x+" "+point.y+ "  "+floor);
		return ("00" + this.username + "," + point.x + "," + point.y + floor).getBytes();
	}

	public String getUsername() {
		return username;
	}

	public Point getPoint(){
		return point;
	}
	public Map getFloor(){
		return floor;
	}

}
