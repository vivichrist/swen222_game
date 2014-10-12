package ServerClients.UDPpackets;

import ServerClients.Client;
import ServerClients.Server;

public class Packet04Connection extends UDPPakcet {

	private boolean connection;
	private String connections;

	public Packet04Connection(byte[]data) {
		super(04);
		String [] dataArray = readData(data).split(",");
		connections = dataArray[0];
		if(connections.equalsIgnoreCase("ture")){
			this.connection = true;
		}else this.connection = false;
		// TODO Auto-generated constructor stub
	}
	public Packet04Connection(String connections){
		super(04);
		this.connections = connections;
		if(connections.equalsIgnoreCase("ture")){
			this.connection = true;
		}else this.connection = false;
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
        return ("04" + this.connections).getBytes();
    }
	public boolean isConnection() {
		return connection;
	}

   
}
