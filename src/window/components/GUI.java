package window.components;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ServerClients.Client;
import ServerClients.Server;
import ServerClients.test;
import ServerClients.UDPpackets.Packet00Login;
import ServerClients.UDPpackets.Packet01Disconnect;
import controllers.Controller;
import controllers.NetworkController;
import ui.components.GameView;
import world.components.Map;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.MultyPlayer;
import world.game.Player;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


/**
 * frame setup
 * @author sunzhih
 *
 */

public class GUI implements WindowListener {

	private static int width = 800;
	private static int height = 770;
	private GameState gameState;//do not change this field for jacky only
	private static Controller controller;
	GameState state;
	MultyPlayer player1;
	Server server = null;
	Canvas canvas = new Canvas();
	GLJPanel gameView;
	JFrame frame;
	JLayeredPane layeredPane;
	JPanel backgroundPanel;
	SouthPanel southPanel;
	JPanel startPanel;
	JPanel choosePlayerPanel;
	JPanel chooseNamePanel;
	JPanel chooseServerPanel;
	JPanel serverStartsPanel;

	private Player player;
	public static  String name;
	//private final BoardCanvasNorth canvas;
	//private final BoardCanvasSouth cardCanvas;

	JButton jbNew;
	JButton jbLoad;
	JButton jbInfo;
	JButton jbExit;
	JButton jbSingle;
	JButton jbMultiple;
	JButton jbStartServer;
	JButton jbJoinServer;
	JButton jbStart;

	JTextField textFieldName;
	private Client client;


	public GUI(){
		setUp();
	}

	public void setUp(){
		frame = new JFrame("Adventure Game");
		frame.setBackground(Color.BLACK);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		//		frame.validate();
		//		frame.repaint();
		//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		frame.setLayout(null);
		//		frame.setResizable(false);

		layeredPane = new JLayeredPane();
		ImageIcon background = new ImageIcon("Resource/bg1.png");
		backgroundPanel = new JPanel();
		backgroundPanel.setBounds(0, 0, width, height);

		JLabel jl = new JLabel(background);
		backgroundPanel.add(jl);

		layeredPane.add( backgroundPanel, JLayeredPane.DEFAULT_LAYER );
		//layeredPane.add(backgroundPanel,JLayeredPane.MODAL_LAYER);
		frame.setLayeredPane(layeredPane);

		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setVisible( true );

		startPanel();

		frame.setVisible(true);
	}

	/**
	 * set up the first panel appears on the frame, create four
	 * buttons on it. Let players choose how to start the game.
	 */
	private void startPanel() {
		int startPanelLeft = 350;
		int startPanelTop = 180;
		int startPanelWidth = 90;  //150
		int startPanelHeight = 600;

		startPanel = new JPanel();
		startPanel.setBounds(startPanelLeft, startPanelTop, startPanelWidth, startPanelHeight);
		//startPanel.setLayout(null);
		//		startPanel.setBounds(startPanelLeft, startPanelTop, startPanelWidth, startPanelHeight);
		//		startPanel.setLayout(null);

		jbNew = new JButton("New");
		jbLoad = new JButton("Load");
		jbInfo = new JButton("Info");
		jbExit = new JButton("Exit");

		/*		jbNewGame.setOpaque(false);
		jbNewGame.setContentAreaFilled(false);
		jbNewGame.setBorderPainted(false);
		jbLoad.setOpaque(false);
		jbLoad.setContentAreaFilled(false);
		jbLoad.setBorderPainted(false);
		jbInfo.setOpaque(false);
		jbInfo.setContentAreaFilled(false);
		jbInfo.setBorderPainted(false);
		jbExit.setOpaque(false);
		jbExit.setContentAreaFilled(false);
		jbExit.setBorderPainted(false);

		jbNewGame.setBackground(new Color(100, 100, 100));
		jbNewGame.set
		jbNewGame.setLayout(null);
		jbNewGame.setBounds(350, 200, 100, 400);
		jbLoad.setLayout(null);
		jbLoad.setBounds(350, 200, 30, 30);
		 */

		setButtonStyle(jbNew, 75, startPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbLoad, 80, startPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbInfo, 65, startPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbExit, 65, startPanel, new Color(0, 135, 200).brighter());


		/*		frame.getRootPane().setDefaultButton(jbNew);
		jbNew.registerKeyboardAction(jbStart.getActionForKeyStroke(
				KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		 */




		startPanel.setOpaque(false);
		layeredPane.add(startPanel, JLayeredPane.MODAL_LAYER);
		addListennerStart();

	}

	/**
	 * set up the frame that let player choose either starts a
	 * single-player game or starts a multiple-player game
	 */
	public void choosePlayerPanel(){
		int choosePlayerPanelLeft = 325;
		int choosePlayerPanelTop = 200;
		int choosePlayerPanelWidth = 150;
		int choosePlayerPanelHeight = 120;

		choosePlayerPanel = new JPanel();
		choosePlayerPanel.setBounds(choosePlayerPanelLeft, choosePlayerPanelTop, choosePlayerPanelWidth, choosePlayerPanelHeight);

		jbSingle = new JButton("Single");
		jbMultiple = new JButton("Multiple");

		setButtonStyle(jbSingle, 95, choosePlayerPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbMultiple, 115, choosePlayerPanel, new Color(0, 135, 200).brighter());

		choosePlayerPanel.setOpaque(false);
		layeredPane.add(choosePlayerPanel, JLayeredPane.MODAL_LAYER);
		addListennerChoosePlayer();
	}

	/**
	 * set up the frame that let player enter the character's name
	 * and starts the game.
	 */
	public void chooseNamePanel(String number){
		int chooseNamePanelLeft = 325;
		int chooseNamePanelTop = 200;
		int chooseNamePanelWidth = 150;
		int chooseNamePanelHeight = 600;

		chooseNamePanel = new JPanel();
		chooseNamePanel.setBounds(chooseNamePanelLeft, chooseNamePanelTop, chooseNamePanelWidth, chooseNamePanelHeight);

		JLabel chooseName = new JLabel("Name:");
		textFieldName = new JTextField(6);
		jbStart = new JButton("START");

		chooseName.setPreferredSize(new Dimension(100, 60));
		chooseName.setFont(new Font("Arial", Font.PLAIN, 30));
		chooseName.setForeground(new Color(0, 135, 200).brighter());

		textFieldName.setPreferredSize(new Dimension(130, 40));
		textFieldName.setFont(new Font("Arial", Font.PLAIN, 30));
		textFieldName.setForeground(new Color(30, 30, 30));

		JLabel space = new JLabel("");
		space.setPreferredSize(new Dimension(100, 20));

		chooseNamePanel.add(chooseName);
		chooseNamePanel.add(textFieldName);
		chooseNamePanel.add(space);
		setButtonStyle(jbStart, 110, chooseNamePanel, Color.MAGENTA);

		//		frame.getRootPane().setDefaultButton(jbStart);
		//		jbStart.setFocusable(true);
		//		jbStart.registerKeyboardAction(jbStart.getActionForKeyStroke(
		//				KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
		//				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
		//				JComponent.WHEN_FOCUSED);

		chooseNamePanel.setOpaque(false);
		layeredPane.add(chooseNamePanel, JLayeredPane.MODAL_LAYER);
		addListennerChooseName(number);
	}

	/**
	 * set up the frame that let player choose either starts a
	 * server of the game or join the server of the game
	 */
	public void chooseServerPanel(){
		int chooseServerPanelLeft = 300;
		int chooseServerPanelTop = 200;
		int chooseServerPanelWidth = 200;
		int chooseServerPanelHeight = 120;

		chooseServerPanel = new JPanel();
		chooseServerPanel.setBounds(chooseServerPanelLeft, chooseServerPanelTop, chooseServerPanelWidth, chooseServerPanelHeight);

		jbStartServer = new JButton("Start Server");
		jbJoinServer = new JButton("Join Server");

		setButtonStyle(jbStartServer, 170, chooseServerPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbJoinServer, 170, chooseServerPanel, new Color(0, 135, 200).brighter());

		chooseServerPanel.setOpaque(false);
		layeredPane.add(chooseServerPanel, JLayeredPane.MODAL_LAYER);
		addListennerChooseServer();
	}
	
	/**
	 * set up the frame that shows server has been started
	 */
	public void serverStartsPanel(){
		int serverStartsPanellLeft = 200;
		int serverStartsPanelTop = 200;
		int serverStartsPanelWidth = 500;
		int serverStartsPanelHeight = 200;

		serverStartsPanel = new JPanel();
		serverStartsPanel.setBounds(serverStartsPanellLeft, serverStartsPanelTop, serverStartsPanelWidth, serverStartsPanelHeight);

		JLabel serverStarts = new JLabel("SERVER STARTS!");

		serverStarts.setPreferredSize(new Dimension(500, 200));
		serverStarts.setFont(new Font("Arial", Font.BOLD, 50));
		serverStarts.setForeground(new Color(100, 200, 100).brighter());

		serverStartsPanel.add(serverStarts);
		serverStartsPanel.setOpaque(false);
		layeredPane.add(serverStartsPanel, JLayeredPane.MODAL_LAYER);
	}

	/**
	 * set the button style by the given characteristics, add the button
	 * onto the given panel
	 * @param button	the given button to set style on
	 * @param buttonWidth	the given width of the button
	 * @param panel	the panel the button will be on
	 * @param defaultColor	the default color of the given button
	 */
	private void setButtonStyle(final JButton button, final int buttonWidth, final JPanel panel, final Color defaultColor){
		button.setPreferredSize(new Dimension(buttonWidth, 60));
		button.setFont(new Font("Arial", Font.PLAIN, 30));
		button.setForeground(defaultColor);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBorder(null);

		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);

		button.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setForeground(defaultColor);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				button.setForeground(new Color(100, 200, 100).brighter());
			}

			@Override
			public void mouseClicked(MouseEvent e) {}
		});

		panel.add(button);
	}

	/**
	 * add action listener onto buttons on startPanel
	 */
	public void addListennerStart(){
		jbNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbNew){
					layeredPane.remove(startPanel);
					choosePlayerPanel();
					frame.repaint();
				}}});

		jbExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * add action listener onto buttons on choosePlayerPanel
	 */
	public void addListennerChoosePlayer(){
		jbSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbSingle){
					layeredPane.remove(choosePlayerPanel);
					chooseNamePanel("single");
					frame.repaint();
				}}});

		jbMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbMultiple){
					layeredPane.remove(choosePlayerPanel);
					ArrayList<Player>players = new ArrayList<Player>();
					Map[]floors =new Map[1];
					floors[0] = new Map(new File("map1.txt"));
					state = new GameState(players,floors);
					
					chooseServerPanel();
					
					

//					if(!state.ServerConnection()){
//						//if (JOptionPane.showConfirmDialog(frame, "Do you want to run the server") == 0) {
//						serverStartsPanel();
//
//						server = new Server();
//						server.start();
//					}
//					else {
//						chooseNamePanel("multiple");
//						//chooseNamePanel2();
//					}
//					//test t = new test();
//					//t.testServerPlayerListName();
					frame.repaint();
				}}});

	}

	/**
	 * add action listener onto buttons on choosePlayerPanel
	 */
	public void addListennerChooseServer(){
		jbStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbStartServer){
					layeredPane.remove(chooseServerPanel);
					serverStartsPanel();
					frame.repaint();
				}}});

		jbJoinServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbJoinServer){
					layeredPane.remove(chooseServerPanel);
					chooseNamePanel("multiple");
					frame.repaint();
				}}});

	}
	
	/**
	 * add action listener onto buttons on chooseNamePanel
	 */
	public void addListennerChooseName(final String number){
		jbStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbStart){
					if(!textFieldName.getText().equals("")){
						name = textFieldName.getText();
						System.out.println("Player name: " + name);
						layeredPane.remove(chooseNamePanel);
						System.out.println(name);
						layeredPane.remove(backgroundPanel);
						if (number.equalsIgnoreCase("single")){
							startGame();
						} else if (number.equalsIgnoreCase("multiple")){
							startGame2();
						}
						frame.repaint();
						//textFieldRealName.setText("");
					}}}});

		textFieldName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = textFieldName.getText();
			}
		});
	}

	/**
	 *
	 */
	protected void startGame() {
		GLProfile.initSingleton();
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities( glprofile );

		//Code added by Kalo
		GameState state = new GameBuilder(name).getGameState();
		//state.setController(controller);
		controller = new Controller(state, this);
		gameView = new GameView( glcapabilities, frame, state );
		NetworkController netCon = new NetworkController(controller);
		netCon.setState(state);
		netCon.setGameView(gameView);


		player = state.getPlayer(name);
		//gameView = new GameView( glcapabilities, frame );

		gameView.setEnabled( true );
		gameView.setVisible( true );
		gameView.setFocusable( true );
		layeredPane.add( gameView, JLayeredPane.DEFAULT_LAYER );
		if ( !gameView.requestFocusInWindow() ) System.out.println( "GameView can't get focus" );
		southPanel = new SouthPanel(player);
		layeredPane.add(southPanel.getPanel(), JLayeredPane.MODAL_LAYER);
	}

	protected void startGame2() {
		System.out.println("Server Start?    >>>>>>>>>>"+ Server.serverStart);

		GLProfile.initSingleton();
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities( glprofile );

		//Code added by Kalo
		//		GameState state = null;
		//		MultyPlayer player1 = null;
		//		ArrayList<Player>players = new ArrayList<Player>();
		//		Map[]floors =new Map[1];
		//		floors[0] = new Map(new File("map1.txt"));
		//		player1 = new MultyPlayer(name, null,null, -1);
		//		state = new GameState(players,floors);
		player1 = new MultyPlayer(name, null,null, -1);

		controller = new Controller(state, this);
		NetworkController networkController = new NetworkController(controller);
		client = new Client("localhost",networkController );
		client.start();
		networkController.setClient(client);
		Packet00Login loginPacket = new Packet00Login(player1.getName());

		loginPacket.writeData(client);
		System.out.println("state.getPlayers().size(): "+ state.getPlayers().size());


		//client.
		if(state.getPlayers().size()>1){
			System.out.println("state.getPlayers().size()>=2"+ state.getPlayers().size());
			gameView = new GameView( glcapabilities, frame, state );
			gameView.setEnabled( true );
			gameView.setVisible( true );
			gameView.setFocusable( true );
			layeredPane.add( gameView, JLayeredPane.DEFAULT_LAYER );
			if ( !gameView.requestFocusInWindow() ) System.out.println( "GameView can't get focus" );
			southPanel = new SouthPanel(player1);
			layeredPane.add(southPanel.getPanel(), JLayeredPane.MODAL_LAYER);


		}
		networkController.setGameView(gameView);

	}

	public int getFloor(int number){

		String[] floorsName = new String[]{"Ground", "First", "Second",
				"Third", "Fourth"};

		String[] floors = new String[number];
		for(int i = 0; i < number; i++){
			floors[i] = floorsName[i] + " Floor";
		}

		String s = (String)JOptionPane.showInputDialog(
				frame,
				"Which floor you want to go to?",
				"Floor Chooser",
				JOptionPane.PLAIN_MESSAGE,
				null, floors,
				"Ground Floor");

		if (s == null){
			System.exit(0);
		}

		for (int j = 0; j < floors.length; j++){
			if (s.equalsIgnoreCase(floors[j])){
				return j;
			}
		}
		return -1;
	}

	public GameState getState(){
		return gameState;
	}

	public void redrawCollectItemCanvas(){
		southPanel.getCollectItemsCanvas().repaint();
	}

	public void redrawUsefulItemCanvas(){
		southPanel.getUsefulItemsCanvas().repaint();
	}

	public static void main(String[] args){
		GUI gui = new GUI();
	}

	public String getName(){
		return name;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		Packet01Disconnect packet = new Packet01Disconnect(name);
		packet.writeData(client);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}

