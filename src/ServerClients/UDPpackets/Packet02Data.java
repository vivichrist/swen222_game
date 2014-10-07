package ServerClients.UDPpackets;

import java.awt.Point;

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


    public Packet02Data( String username, int x, int y) {
        super(02);
    	System.out.println("packet02Data con 1: ");

        this.username = username;
        this.x = x;
        this.y = y;
        this.point = new Point(x,y);

    }



	public Packet02Data(GameState state) {
		super(02);
		// TODO Auto-generated constructor stub
		this.state = state;
		//state.deserialize(data);
	}
	public Packet02Data(byte[] data) {
		super(02);
    	System.out.println("packet02Data con 2: ");
		// TODO Auto-generated constructor stub
		//state.deserialize(data);
		String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        point = new Point(x,y);
       
		
		
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
//    	byte[]temp = state.serialize();
//    	byte[]newData =new byte[2+temp.length];
//    	newData[0] = '0';
//    	newData[1] = '2';
//    	int count = 2;
//
//    	for(byte b:temp){
//    		newData[count] = b;
//    		count++;
//    	}
//        return newData;
    	System.out.println("getData: ");
    	return ("02" + this.username + "," + this.x + "," + this.y).getBytes();

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
