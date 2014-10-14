package ServerClients.UDPpackets;

import java.awt.Point;
import ServerClients.Client;
import ServerClients.Server;



public class Packet00Login extends UDPPacket {

	private String username;


	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		System.out.println("username = "+ username);
	}

	public Packet00Login(String username) {
		super(00);
		this.username = username;

	}

	@Override
	public void writeData(Client client) {
		if(client==null)System.out.println("null client");
		client.sendData(getData());

	}

	@Override
	public void writeData(Server server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		//System.out.println("packet00Login>>getData..."+username+"  "+point.x+" "+point.y+ "  "+floor);
		return ("00" + this.username).getBytes();
	}

	public String getUsername() {
		return username;
	}




}
