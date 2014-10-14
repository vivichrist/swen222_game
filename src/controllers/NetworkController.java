package controllers;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.media.opengl.awt.GLJPanel;

import ServerClients.Client;
import ServerClients.UDPpackets.Packet03Move;
import ServerClients.UDPpackets.Packet06PickupObject;
import ui.components.GameView;
import window.components.GUI;
import world.components.MoveableObject;
import world.game.GameState;
import world.game.Player;
/**
 * A NetworkController to handle interactions between the Server/Client and Controller(GameState and the GUI)
 * @author zhaojiang chang - 300282984
 *
 */
public class NetworkController {
	private static Client client;
	private static GUI gui;
	private static GameState state;
	private GameView gameView;
	private Object PlayerAndObject;
	static Player player;
	static MoveableObject object;
	private static UIController controller;
	private static RendererController renCon;

	/**
	 * Constructor - creates a Network Controller for GameState/GUI/Client interaction in this game
	 * @param state the GameState of this game
	 * @param gui the GUI for this game
	 * @param client the Client for the game
	 */
	public NetworkController(UIController controller, RendererController renCon){
		this.controller = controller;
		this.renCon = renCon;
	}

	/**
	 * Receive action from gameView(user input) then create a new move package to send to server then broadcast to all other client
	 *  @param player - current player  
	 *  @param point -  the new position move to
	 */
	public static void movePlayer(Player player, Point point){
		Packet03Move move = new Packet03Move(player.getName(),point);
		move.writeData(client);

		//controller.movePlayer(player, point);	
	}

	/**
	 * Receive action from server then move other player (current may able to see other player movement 
	 *  @param player - other player  
	 *  @param point -  the new position move to
	 */
	public static void moveOtherPlayer(Player player, Point point){
		System.out.println("NetworkController->moveOtherPlayer "+ point.x+" "+point.y);
		renCon.moveOtherPlayer(player, point);
	}
	/**
	 * pass gameView from GUI
	 *  @param set gameView 

	 */
	public void setGameView(GLJPanel gameView) {
		this.gameView = (GameView)gameView;
	}
	/**
	 * set client from GUI
	 *  @param set client 

	 */
	public void setClient(Client client) {
		this.client = client;
	}
	/**
	 * server send the serialized data to server then through the networkController pass to controller then update the state
	 *  @param realData - 

	 */
	public GameState deserialize(byte[] realData) {
		return controller.deserialize(realData);
	}

	public void setState(GameState st) {
		controller.setState(st);

	}

	public Player getPlayer(String username) {
		return controller.getPlayer(username);
	}

	/**
	 * check server connection
	 *  @param boolean value - return true means connect to the server 

	 */
	public void setConnection(boolean connection) {
		controller.setConnection(connection);

	}

	/**
	 * Teleports a given Player to a user selected floor
	 * @param p the Player to Teleport
	 * @return the user selected floor
	 */
	public static boolean teleport(Player p){
		System.out.println("teleport called");
		return controller.teleport(p);
	}
	
	
	
	
	
	/**
	 * received action from server 
	 **/
	public void toOpenDoor(String doorAction, Point point) {
		// TODO: add openDoor method in renCon
		renCon.toOpenDoor(doorAction,point);
	}
	
	public void pickupObject(Player player, MoveableObject object){
		this.player = player;
		this.object = object;
		byte[]data = this.serialize(this.PlayerAndObject);
		Packet06PickupObject pickup = new Packet06PickupObject(data);
		pickup.writeData(client);
		
	}
	
	
	
	public void removeObjectFromClient(Packet06PickupObject packet) {
		PlayerAndObject p = (controllers.NetworkController.PlayerAndObject) this.deserialise(packet.getRealData());
		Player player = p.player;
		MoveableObject object = p.object;
		renCon.removeObject(player,object);
		
	}
	class PlayerAndObject implements java.io.Serializable {
		Player player;
		MoveableObject object;
		public PlayerAndObject(){
			player = NetworkController.player;
			object = NetworkController.object;
		}
	}
	
	public byte[] serialize(Object obj) {

		byte[] bytes = new byte[60000];
		try {
			//object to bytearray
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(obj);
			bytes = baos.toByteArray();
			out.flush();
			baos.close();
			out.close();
			return bytes;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * deserialize the player 
	 * 
	 * */
	public Object deserialise(byte[]bytes) {

		ByteArrayInputStream bais = null;
		ObjectInputStream in = null;
		try{
			bais = new ByteArrayInputStream(bytes);
			in = new ObjectInputStream(bais);
			Object obj =  in.readObject();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				bais.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	
	
}

