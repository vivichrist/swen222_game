package ServerClients.UDPpackets;

import java.awt.Point;

import ServerClients.Client;
import ServerClients.Server;



public class Packet00Login extends UDPPakcet {

    private String username;
    private int x, y;
    private Point point;

    public Packet00Login(byte[] data) {
        super(00);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        point = new Point(x,y);
    }

    public Packet00Login(String username, Point point) {
        super(00);
        this.username = username;
        this.point = point;
    }

    @Override
    public void writeData(Client client) {
		System.out.println("Packet00Login>writeData....");
		if(client==null)System.out.println("null client");
		System.out.println("getdata().toString(): "+getData().length);
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
    	System.out.println("packet00Login>>getData..."+username+"  "+point.x+" "+point.y);
        return ("00" + this.username + "," + point.x + "," + point.y).getBytes();
    }

    public String getUsername() {
        return username;
    }

   public Point getPoint(){
	   return point;
   }

}
