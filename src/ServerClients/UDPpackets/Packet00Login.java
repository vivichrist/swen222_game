package ServerClients.UDPpackets;

import java.awt.Point;
import ServerClients.Client;
import ServerClients.Server;

/**
 * A Packet00Login to handle player login 
 * @author zhaojiang chang - 300282984
 *
 */

public class Packet00Login extends UDPPacket {

	private String username;

	/**
	 * Constructor - unpack login package
	 * @param data - byte array  name of the player
	 */
	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		System.out.println("username = "+ username);
	}

	/**
	 * Constructor - create a login package with given name
	 * @param username - player name
	 */
	public Packet00Login(String username) {
		super(00);
		this.username = username;

	}

	/**
	 * 
	 * */
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
