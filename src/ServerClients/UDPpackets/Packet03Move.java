/**
 *
 */
package ServerClients.UDPpackets;

import java.awt.Point;

import world.game.GameState;
import ServerClients.Client;
import ServerClients.Server;

/**
 * @author changzhao
 *
 */
public class Packet03Move extends UDPPacket {

	private String username;
	private Point point;
	private byte[] data;
	private int x;
	private int y;

	/**
	 * @param packetId
	 */
	public Packet03Move(String username, Point point) {
		super(03);
		this.username = username;
		this.point = point;
		for(int i = 0; i<getData().length; i++){
			System.out.println(getData()[i]);
		}
	}
	public Packet03Move(byte[] data) {
		super(03);
		this.data = data;
		String[]dataArray = readData(data).split(",");
		for(int i = 0; i<dataArray.length; i++){
			System.out.println(dataArray[i]);
		}
		this.username = dataArray[1];

		this.x = Integer.parseInt(dataArray[2]);
		this.y = Integer.parseInt(dataArray[3]);
		this.point = new Point(x,y);
		System.out.println("Packet03Move con 2: ");
	}


	/* (non-Javadoc)
	 * @see ServerClients.UDPpackets.UDPPakcet#writeData(ServerClients.Client)
	 */
	@Override
	public void writeData(Client client) {
		client.sendData(getData());
		System.out.println("Packet03Move con 3: ");

	}

	/* (non-Javadoc)
	 * @see ServerClients.UDPpackets.UDPPakcet#writeData(ServerClients.Server)
	 */
	@Override
	public void writeData(Server server) {
		System.out.println("Packet03Move con 4: ");
		server.sendActionDataToAllClients(getData());

		System.out.println("Packet03Move con 5: ");
	}

	/* (non-Javadoc)
	 * @see ServerClients.UDPpackets.UDPPakcet#getData()
	 */
	@Override
	public byte[] getData() {
		// TODO Auto-generated method stub
		return ("03"+ ","+this.username +","+this.point.x+","+this.point.y).getBytes();
	}
	/* (non-Javadoc)
	 * @see ServerClients.UDPpackets.UDPPakcet#getRealData()
	 */
	public byte[] getRealData(){
		return(this.username +","+this.point.x+","+this.point.y).getBytes();
	}
	public String getUsername() {
		return username;
	}
	public Point getPoint() {
		return point;
	}


}
