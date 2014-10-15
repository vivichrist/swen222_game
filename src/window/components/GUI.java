package window.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ServerClients.Client;
import ServerClients.Server;
//import ServerClients.test;
import ServerClients.UDPpackets.Packet00Login;
import controllers.RendererController;
import controllers.UIController;
import controllers.NetworkController;
import ui.components.GameView;
import world.ColourPalette;
import world.components.GameToken;
import world.components.Map;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.MultyPlayer;
import world.game.Player;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.event.*;


/**
 * The GUI is responsible for setting up the game entry. This class generates 
 * different options to let player choose different game mode. The class also 
 * records players's information and pass to other classes.
 * 
 * @author Zhiheng Sun,  ID: 300256273
 * 
 */
public class GUI {

	// the dimension of the frame 
	private static int width = 800;
	private static int height = 770;

	public JFrame frame;	// this is the frame the game will be shown on 
	private JLayeredPane layeredPane;	// this is used to add panel onto the frame
	private GLJPanel gameView;	// the rendering window of the game

	private static UIController controller;	// the handler between the GameState and the GUI
	private Client client;	// is client side of the game

	/**
	 * The following stores the panel shown on the bottom of the frame which has player's 
	 * collected tokens images and player's inventory images
	 */
	private SouthPanel southPanel;

	/**
	 * The following JPanels are the panels used to display on the frame according 
	 * to players' game entry choices
	 */	
	private Panel backgroundPanel;
	private Panel waitClientsPanel;

	private JPanel gameOverPanel;
	private JPanel wrongInfoPanel;

	// textFields on all panels
	private JTextField serverName;
	private JTextField portNum;
	private JTextField serverNameC;
	private JTextField portNumC;
	private JTextField textFieldNameC;

	private Player player;	// the current player
	private int numPlayer;
	private static String name;	// the entered name of the player in single-player mode
	public static String nameC;	// the entered name of the player in multiple-player mode
	public String strServerName;	// the shown server name on serverStarts panel in multiple-player mode
	public String strPortNum;	// the shown port number on serverStarts panel in multiple-player mode
	public String strServerNameC;	// the player entered server name in multiple-player mode
	public String strPortNumC;	// the player entered port number in multiple-player mode
	public String winner;	// the winner of the game
	
	public GUI(){
		new ColourPalette();
		setUp();
	}

	/**
	 * The following method sets up a frame to start the game entry
	 */
	public void setUp(){
		frame = new JFrame();
		frame.setTitle("Adventure Game");
		frame.getRootPane().setBackground(Color.BLACK);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		// add the background image to frame
		layeredPane = new JLayeredPane();
		backgroundPanel = new BackgroundPanel(this);
		layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

		frame.setLayeredPane(layeredPane);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setVisible( true );

		serverName = new JTextField(18);
		portNum = new JTextField(18);
		textFieldNameC = new JTextField(18);
		serverNameC = new JTextField(18);
		portNumC = new JTextField("4768",18);

		StartPanel startPanel = new StartPanel(this);
		addPanel(startPanel);
	}

//	/**
//	 * The following method sets up the frame that tells the player server
//	 * is waiting for other players to join in to start the game
//	 */
//	public void waitClientsPanel(){
//
//		// label used on waitClientsPanel
//		JLabel waitClients = new JLabel("Wait For Other Players...");
//		waitClients.setPreferredSize(new Dimension(600, 200));
//		waitClients.setFont(new Font("Arial", Font.BOLD, 50));
//		waitClients.setForeground(new Color(100, 200, 100).brighter());
//		waitClientsPanel.add(waitClients);
//
//		// set the panel to transparent and add the panel to frame
//		waitClientsPanel.setOpaque(false);
//		layeredPane.add(waitClientsPanel, JLayeredPane.MODAL_LAYER);
//	}

	/**
	 * The following method sets up the frame that tells the player server
	 * is waiting for other players to join in to start the game
	 */
	public void wrongInfoPanel(){
		wrongInfoPanel = new JPanel();
		setUpPanel(wrongInfoPanel, 30, 200, 760, 200);

		// label used on waitClientsPanel
		JLabel wrongInfo = new JLabel("Incorrent Information Entered!");
		wrongInfo.setPreferredSize(new Dimension(760, 200));
		wrongInfo.setFont(new Font("Arial", Font.BOLD, 50));
		wrongInfo.setForeground(new Color(100, 200, 100).brighter());
		wrongInfoPanel.add(wrongInfo);

		// set the panel to transparent and add the panel to frame
		wrongInfoPanel.setOpaque(false);
		layeredPane.add(wrongInfoPanel, JLayeredPane.MODAL_LAYER);
	}

	/**
	 * The following method sets up the frame that tells the player game is
	 * over and who is the winner or everyone has run out of time
	 * @param p	the winner player if it exists
	 * @param hasWinner	whether is game has a winner or not
	 */
	public void gameOverPanel(){
		gameOverPanel = new JPanel();
		setUpPanel(gameOverPanel, 100, 200, 600, 160);

		// label used on gameOverPanel
		JLabel gameOver = new JLabel("Game Over!!!");
		gameOver.setPreferredSize(new Dimension(350, 80));
		gameOver.setFont(new Font("Arial", Font.BOLD, 50));
		gameOver.setForeground(new Color(100, 200, 100).brighter());
		gameOverPanel.add(gameOver);

		JLabel wins = new JLabel(winner);
		wins.setPreferredSize(new Dimension(310, 80));
		wins.setFont(new Font("Arial", Font.BOLD, 50));
		wins.setForeground(new Color(100, 200, 100).brighter());
		gameOverPanel.add(wins);

		// set the panel to transparent and add the panel to frame
		gameOverPanel.setOpaque(false);
		layeredPane.add(gameOverPanel, JLayeredPane.MODAL_LAYER);
	}

	/**
	 * The following method sets the bounds of the given panel with the 
	 * given values
	 * @param panel	the given panel to set bounds to 
	 * @param left	the left position of the panel
	 * @param top	the top position of the panel
	 * @param width	the width of the panel
	 * @param height	the height of the panel
	 */
	private void setUpPanel(JPanel panel, int left, int top, int width, int height){
		panel.setBounds(left, top, width, height);
	}

	/**
	 * The following method starts the game for single-player mode
	 */
	protected void startGame() {
		GLProfile.initSingleton();
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities( glprofile );

		//Code added by Kalo
		GameState state = new GameBuilder(name).getGameState();
		controller = new UIController(state, this);
		player = state.getPlayer(name);
		gameView = new GameView( glcapabilities, frame, state,player );

		RendererController renCon = new RendererController(true);
		NetworkController netCon = new NetworkController(controller, renCon);
		renCon.setState(state);
		renCon.setNetCon(netCon);
		renCon.setUICon(controller);

		netCon.setGameView(gameView);
		gameView.setEnabled( true );
		gameView.setVisible( true );
		gameView.setFocusable( true );
		layeredPane.add( gameView, JLayeredPane.DEFAULT_LAYER );

		// add the southPanel onto the bottom of the frame
		southPanel = new SouthPanel(player);
		layeredPane.add(southPanel.getPanel(), JLayeredPane.MODAL_LAYER);

		WindowListeners listeners = new WindowListeners(this);
		listeners.setClient(client);
	}

	/**
	 * The following method starts the game for multiple-player mode
	 */
	protected void startGame2() {
		//waitClientsPanel();

		waitClientsPanel = new WaitClientsPanel(this);
		layeredPane.add(waitClientsPanel, JLayeredPane.MODAL_LAYER);

		
		int clientPortNumber = Integer.parseInt(strPortNumC);
		client = new Client(this,nameC,strServerNameC,clientPortNumber );
		client.start();

		Packet00Login loginPacket = new Packet00Login(nameC);
		loginPacket.writeData(client);
	}

	/**
	 * The following method will call by client once client class received broadcast package from server
	 * player will able to start the game
	 * @param name - current player name
	 * @param state -  after been called game state
	 * */
	public void startClientWindows(Player player, GameView gameView){
		this.gameView = gameView;
		this.player = player;

		layeredPane.add( gameView, JLayeredPane.DEFAULT_LAYER );
		southPanel = new SouthPanel(player);
		layeredPane.add(southPanel.getPanel(), JLayeredPane.MODAL_LAYER);
		layeredPane.remove(waitClientsPanel);
	}

	/**
	 * The following method pops up a window to ask the player to choose which floor
	 * he wants to go to when the player enters teleport
	 * @param number	how many floors the game current holds
	 * @return	the floor player chose 
	 */
	public int getFloor(int number){
		int currentFloor = player.getFloor().floorNumber();
		String[] floorsName = new String[]{"Ground", "First", "Second", "Third", "Fourth"};
		String[] floors = new String[number - 1];

		// add the floors into list except the player's current floor
		int count = 0;
		for(int i = 0; i < number; i++){
			if (i != currentFloor){
				floors[count] = floorsName[i] + " Floor";
				count++;
			}
		}
		// let player choose a floor and assign it to s
		String s = (String)JOptionPane.showInputDialog(
				null,
				"Which floor you want to go to?",
				"Floor Chooser",
				JOptionPane.PLAIN_MESSAGE,
				null, floors,
				"Ground Floor");

		if (s == null){
			return -1;
		}
		// change the string s back to corresponding integer and call the canvas to rewrite the floor name 
		for (int j = 0; j < floorsName.length; j++){
			if (s.equalsIgnoreCase(floorsName[j] + " Floor")){
				southPanel.getUsefulItemsCanvas().setFloorNum(j);
				southPanel.getUsefulItemsCanvas().repaint();
				return j;
			}
		}
		return -1;
	}

	/**
	 * The following method would be called when the player walks up a container.
	 * The item in the container is passed to the method, the method would pop up
	 * a window and tell the player what is found in the container.
	 * @param item	the item found in the container
	 */
	public void findContainer(GameToken item){
		// get the image of the item found
		String color = southPanel.getCollectItemsCanvas().identifyColor(item.getColor());
		String info = "Find " + item.toString().toUpperCase() + " in " + color.toUpperCase();
		String resource = "Resource/" + item.toString().toLowerCase() + "/" + item.toString().toLowerCase() + " " + color + ".png";
		ImageIcon icon = new ImageIcon(resource);
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH) ;  
		icon = new ImageIcon(newimg);

		// pop up the window to show the information
		javax.swing.UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 18))); 
		JOptionPane.showMessageDialog(frame, info, "Container", JOptionPane.INFORMATION_MESSAGE, icon);
	}



	protected void addPanel(Panel panel){
		layeredPane.add(panel, JLayeredPane.MODAL_LAYER);
		frame.repaint();
	}

	protected void removePanel(Panel panel){
		layeredPane.remove(panel);
		frame.repaint();
	}







	public void redrawCollectItemCanvas(){
		southPanel.getCollectItemsCanvas().repaint();
	}

	public void redrawUsefulItemCanvas(){
		southPanel.getUsefulItemsCanvas().repaint();
	}

	public static void main(String[] args){
		new GUI();
	}

	public String getName(){
		return name;
	}

	/**
	 * this method is going to return the current local host address
	 * 
	 * */
	protected InetAddress getSeverName() throws UnknownHostException {
		return InetAddress.getLocalHost();
	}

	public void addWindowListener(WindowListeners windowListeners) {
		// TODO Auto-generated method stub
		//this.addWindowListener(windowListeners);
	}

	//=========================
	//getter and setter
	//=========================

	public JFrame getFrame(){
		return frame;
	}

	protected void setName(String name){
		this.name = name;
	}

	protected Panel getBackgroundPanel(){
		return backgroundPanel;
	}

	public void setNumPlayer(int parseInt) {
		this.numPlayer = parseInt;
	}

	public void setStrServerName(String s) {
		this.strServerName = s;
	}

	public void setStrPortNum(String s) {
		this.strPortNum = s;
	}

	public String getStrPortNum() {
		return this.strPortNum;
	}

	public int getNumPlayer() {
		return numPlayer;
	}

	public JTextField getServerName() {
		return this.serverName;
	}

	public String getStrServerName() {
		return this.strServerName;
	}

	public JTextField getPortNum() {
		return this.portNum;
	}

	public JTextField getTextFieldNameC() {
		return textFieldNameC;
	}

	public JTextField getServerNameC() {
		return serverNameC;
	}

	public JTextField getPortNumC() {
		return portNumC;
	}

	public void setNameC(String text) {
		nameC = text;		
	}

	public void setStrPortNumC(String text) {
		strPortNumC = text;
	}

	public void setStrServerNameC(String text) {
		strServerNameC = text;	
	}

	public String getStrServerNameC() {
		return strServerNameC;
	}
	
	public void setWinner(String w){
		winner = w;
	}
}

