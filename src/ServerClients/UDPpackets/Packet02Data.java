package ServerClients.UDPpackets;

import java.awt.Point;

import world.game.GameState;
import ServerClients.Client;
import ServerClients.Server;


public class Packet02Data extends UDPPakcet {

    private String username;
    private GameState state;
    private int floor = 0;
    private Point point;


    public Packet02Data(String username, int x, int y,int floor) {
        super(02);
        this.username = username;
        this.floor = floor;
        this.point = new Point(x,y);
       
    }

    

	public Packet02Data(byte[] data) {
		super(02);
		// TODO Auto-generated constructor stub
		state.deserialize(data);
	}

	@Override
    public void writeData(Client client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(Server server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return state.serialize();

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
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}



}
