package world.game;

import java.awt.Point;
import java.net.InetAddress;

import world.components.Map;
import world.components.TokenType;


public class MultyPlayer extends Player {

	public InetAddress ipAddress;
	public int port;
	int x;
	int y;
	private Point position;
	//private Map floor;

	public MultyPlayer( String username,Point position, TokenType type,InetAddress ipAddress, int port) {
		super(username, type);
		this.ipAddress = ipAddress;
		this.port = port;
		this.position = position;
		//this.floor = fl;
	}

	public Point getPosition() {
		return position;
	}

}