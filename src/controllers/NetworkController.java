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
import ServerClients.UDPpackets.Packet04Teleport;
import ServerClients.UDPpackets.Packet05OpenDoor;
import ServerClients.UDPpackets.Packet06PickupObject;
import ServerClients.UDPpackets.Packet07DropObject;
import ServerClients.UDPpackets.UDPPacket;
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
	public static Point point = null;

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
	 * 
	 * 
	 * */
	public static void teleportOtherPlayer(String name, int floorNumber){
		renCon.teleportOtherPlayer(name, floorNumber);
	}
	public static void teleport(String name, int floorNumber){
		
		Packet04Teleport teleport = new Packet04Teleport(name,floorNumber);
		teleport.writeData(client);

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
	 * get player
	 * @param get player
	 * */

	public Player getPlayer(String username) {
		return controller.getPlayer(username);
	}

//	/**
//	 * Teleports a given Player to a user selected floor
//	 * @param p the Player to Teleport
//	 * @return the user selected floor
//	 */
//	public static boolean teleport(Player p){
//		System.out.println("teleport called");
//		return controller.teleport(p);
//	}


	/**
	 * gameview will call this method through RendererController and 
	 * pass the action to client then send to server
	 * 
	 * */
	public void openDoor(String name, Point point){
		Packet05OpenDoor openDoor = new Packet05OpenDoor(name, point);
		openDoor.writeData(client);
	}

	/**
	 * received action from server, need update the gameview and gamestate
	 *Instruction from server (other player trigger the door open action)
	 *this method will pass to RendererController
	 **/
	public void toOpenDoor(String doorAction, Point point) {
		// TODO: add openDoor method in renCon
		renCon.toOpenDoor(doorAction,point);
	}
	/**
	 * this method will called from rendererController
	 * serialize the PlayerAndObject
	 * end packet to client, then broadcast to all clients
	 * @param player - current player
	 * @param object - 
	 * */
	public void pickupObject(Player player, Point point){
		this.player = player;
		this.point = point;
		PlayerAndObject p = new PlayerAndObject();
		byte[]data = this.serialize(p);
		Packet06PickupObject pickup = new Packet06PickupObject(data);
		pickup.writeData(client);

	}
	/**
	 * this method is going to create a dropObject package and send to server through the client
	 * 
	 * */

	public void dropObject(Player player, MoveableObject object, Point point) {
		this.player = player;
		this.object = object;
		this.point = point;
		byte[]data = this.serialize(this.PlayerAndObject);
		Packet07DropObject drop = new Packet07DropObject(data);
		drop.writeData(client);
	}
	
	
	public void addObjectToView(byte[]data){
		
		PlayerAndObject object =(controllers.NetworkController.PlayerAndObject) this.deserialise(data);
		//TODO: kalo call this method to update the 
		//renCon.addObjectToView(object.player, object.object);
	}

	/**
	 * this method will called after server send a drop message, 
	 * 
	 * */

	public void pickupObjectOtherPlayer(Packet06PickupObject packet) {
			PlayerAndObject p = (controllers.NetworkController.PlayerAndObject) this.deserialise(((Packet06PickupObject)packet).getRealData());
			Player player = p.player;
			MoveableObject object = p.object;
			Point point = p.point;
			renCon.pickupObjectOtherPlayer(player,point);
		
		

	}
	class PlayerAndObject implements java.io.Serializable {
		Player player;
		MoveableObject object;
		Point point;
		public PlayerAndObject(){
			player = NetworkController.player;
			object = NetworkController.object;
			point = NetworkController.point ;
		}
		
	}
	/**
	 * serialize object
	 * 
	 * */
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
	 * deserialize object 
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

