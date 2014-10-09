package ServerClients;

import world.game.GameState;
import ServerClients.UDPpackets.Packet02Data;

public class Event {
	private GameState state;


	public Event(GameState state){
		this.state = state;
	}
	public Packet02Data getData02Packet(){


		byte[] data = state.serialize();
		Packet02Data dataPacket = new Packet02Data(state, data);
		return dataPacket;		
	}

}
