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
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import ServerClients.Timers;
import ServerClients.WindowListeners;
//import ServerClients.test;
import ServerClients.UDPpackets.Packet00Login;
import ServerClients.UDPpackets.Packet01Disconnect;
import controllers.RendererController;
import controllers.UIController;
import controllers.NetworkController;
import ui.components.GameView;
import world.ColourPalette;
import world.components.GameObject;
import world.components.GameToken;
import world.components.Map;
import world.game.GameBuilder;
import world.game.GameState;
import world.game.MultyPlayer;
import world.game.Player;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
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

	// please comment these variables, sorry I can't do it as I don't know them. 
	// can we set the variables to private?
	private GameState gameState;//do not change this field for jacky only
	private static UIController controller;
	GameState state = null;
	MultyPlayer player1 = null;
	ArrayList<Player>players;
	Map[]floors;
	Server server = null;
	GLJPanel gameView;
	private Client client;

	private JFrame frame;	
	private JLayeredPane layeredPane;	// this is used to add panel onto the frame

	/**
	 * The following stores the panel shown on the bottom of the frame which has player's 
	 * collected tokens images and player's inventory images
	 */
	private SouthPanel southPanel;

	/**
	 * The following JPanels are the panels used to display on the frame according 
	 * to players' game entry choices
	 */
	private JPanel backgroundPanel;
	private JPanel startPanel;
	private JPanel choosePlayerPanel;
	private JPanel chooseNamePanel;
	private JPanel chooseServerPanel;
	private JPanel serverStartsPanel;
	private JPanel joinServerPanel;
	private JPanel waitClientsPanel;
	private JPanel gameOverPanel;
	private JPanel wrongInfoPanel;

	// buttons on all panels 
	private JButton jbNew;
	private JButton jbLoad;
	private JButton jbInfo;
	private JButton jbExit;
	private JButton jbSingle;
	private JButton jbMultiple;
	private JButton jbStartServer;
	private JButton jbJoinServer;
	private JButton jbStart;
	private JButton jbClientStart;

	// textFields on all panels
	private JTextField serverName;
	private JTextField portNum;
	private JTextField serverNameC;
	private JTextField portNumC;
	private JTextField textFieldNameC;
	private JTextField textFieldName;

	private Player player;	// the current player
	private int numPlayer;
	public static String name;	// the entered name of the player in single-player mode
	public static String nameC;	// the entered name of the player in multiple-player mode
	public String strServerName;	// the shown server name on serverStarts panel in multiple-player mode
	public String strPortNum;	// the shown port number on serverStarts panel in multiple-player mode
	public String strServerNameC;	// the player entered server name in multiple-player mode
	public String strPortNumC;	// the player entered port number in multiple-player mode
	
	public GUI(){
		new ColourPalette();
		setUp();
	}

	/**
	 * The following method sets up a frame to start the game entry
	 */
	public void setUp(){
		frame = new JFrame("Adventure Game");
		frame.setBackground(Color.BLACK);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		// add the background image to frame
		layeredPane = new JLayeredPane();
		ImageIcon background = new ImageIcon("Resource/bg1.png");
		backgroundPanel = new JPanel();
		backgroundPanel.setBounds(0, 0, width, height);
		JLabel jl = new JLabel(background);
		backgroundPanel.add(jl);
		layeredPane.add( backgroundPanel, JLayeredPane.DEFAULT_LAYER );

		frame.setLayeredPane(layeredPane);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setVisible( true );

		startPanel();
	}

	/**
	 * The following method sets up the first panel appears on the frame, 
	 * creates four buttons on it. Let players choose how to start the game.
	 */
	private void startPanel() {
		startPanel = new JPanel();
		setUpPanel(startPanel, 350, 180, 90, 600);

		// buttons used on startPanel
		jbNew = new JButton("New");
		jbLoad = new JButton("Load");
		jbInfo = new JButton("Info");
		jbExit = new JButton("Exit");

		setButtonStyle(jbNew, 75, startPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbLoad, 80, startPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbInfo, 65, startPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbExit, 65, startPanel, new Color(0, 135, 200).brighter());

		// set the panel to transparent and add the panel to frame
		startPanel.setOpaque(false);
		layeredPane.add(startPanel, JLayeredPane.MODAL_LAYER);
		addListennerStart();
	}

	/**
	 * The following method sets up the frame that let player choose 
	 * either starts a single-player game or starts a multiple-player game
	 */
	public void choosePlayerPanel(){
		choosePlayerPanel = new JPanel();
		setUpPanel(choosePlayerPanel, 325, 200, 150, 120);

		// buttons used on choosePlayerPanel
		jbSingle = new JButton("Single");
		jbMultiple = new JButton("Multiple");

		setButtonStyle(jbSingle, 95, choosePlayerPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbMultiple, 115, choosePlayerPanel, new Color(0, 135, 200).brighter());

		// set the panel to transparent and add the panel to frame
		choosePlayerPanel.setOpaque(false);
		layeredPane.add(choosePlayerPanel, JLayeredPane.MODAL_LAYER);
		addListennerChoosePlayer();
	}

	/**
	 * The following method sets up the frame that let player 
	 * enter the character's name and starts a single-player game.
	 */
	public void chooseNamePanel(){
		chooseNamePanel = new JPanel();
		setUpPanel(chooseNamePanel, 325, 200, 150, 600);

		// label, textField and button used on chooseNamePanel
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

		// set the panel to transparent and add the panel to frame
		chooseNamePanel.setOpaque(false);
		layeredPane.add(chooseNamePanel, JLayeredPane.MODAL_LAYER);
		addListennerChooseName();
	}

	/**
	 * The following method sets up the frame that let player choose 
	 * either starts a server of the game or join the server of the game
	 */
	public void chooseServerPanel(){
		chooseServerPanel = new JPanel();
		setUpPanel(chooseServerPanel, 300, 200, 200, 120);

		// buttons used on chooseServerPanel
		jbStartServer = new JButton("Start Server");
		jbJoinServer = new JButton("Join Server");

		setButtonStyle(jbStartServer, 170, chooseServerPanel, new Color(0, 135, 200).brighter());
		setButtonStyle(jbJoinServer, 170, chooseServerPanel, new Color(0, 135, 200).brighter());

		// set the panel to transparent and add the panel to frame
		chooseServerPanel.setOpaque(false);
		layeredPane.add(chooseServerPanel, JLayeredPane.MODAL_LAYER);
		addListennerChooseServer();
	}

	/**
	 * The following method sets up the frame that shows server has 
	 * been started and shows the server name and port number
	 */
	public void serverStartsPanel(){
		serverStartsPanel = new JPanel();
		setUpPanel(serverStartsPanel, 150, 180, 500, 500);

		// labels, textFields and button used on serverStartsPanel
		JLabel serverStarts = new JLabel("SERVER STARTS!");
		serverStarts.setPreferredSize(new Dimension(450, 100));
		serverStarts.setFont(new Font("Arial", Font.BOLD, 50));
		serverStarts.setForeground(new Color(100, 200, 100).brighter());

		JLabel information = new JLabel("The following information is for client to join server");
		information.setPreferredSize(new Dimension(500, 40));
		information.setFont(new Font("Arial", Font.BOLD, 20));
		information.setForeground(new Color(0, 135, 200).brighter());

		JLabel name = new JLabel("Server Name : ");
		serverName = new JTextField(18);

		name.setPreferredSize(new Dimension(150, 60));
		name.setFont(new Font("Arial", Font.PLAIN, 20));
		name.setForeground(new Color(0, 135, 200).brighter());

		serverName.setPreferredSize(new Dimension(250, 40));
		serverName.setFont(new Font("Arial", Font.PLAIN, 20));
		serverName.setForeground(new Color(30, 30, 30));
		serverName.setText(strServerName);
		serverName.setEditable(false);

		JLabel port = new JLabel("Port Number : ");
		portNum = new JTextField(18);

		port.setPreferredSize(new Dimension(150, 60));
		port.setFont(new Font("Arial", Font.PLAIN, 20));
		port.setForeground(new Color(0, 135, 200).brighter());

		portNum.setPreferredSize(new Dimension(130, 40));
		portNum.setFont(new Font("Arial", Font.PLAIN, 20));
		portNum.setForeground(new Color(30, 30, 30));
		portNum.setText(strPortNum);
		portNum.setEditable(false);

		serverStartsPanel.add(serverStarts);
		serverStartsPanel.add(information);
		serverStartsPanel.add(name);
		serverStartsPanel.add(serverName);
		serverStartsPanel.add(port);
		serverStartsPanel.add(portNum);

		// set the panel to transparent and add the panel to frame
		serverStartsPanel.setOpaque(false);
		layeredPane.add(serverStartsPanel, JLayeredPane.MODAL_LAYER);
	}

	/**
	 * The following method sets up the frame that let player enter 
	 * the player name, the server name and port number to join the 
	 * server to start game
	 */
	public void joinServerPanel(){
		joinServerPanel = new JPanel();
		setUpPanel(joinServerPanel, 200, 200, 500, 500);

		// labels, textFields and button used on joinServerPanel
		JLabel nameP = new JLabel("Player Name : ");
		textFieldNameC = new JTextField(18);

		nameP.setPreferredSize(new Dimension(150, 60));
		nameP.setFont(new Font("Arial", Font.PLAIN, 20));
		nameP.setForeground(new Color(0, 135, 200).brighter());

		textFieldNameC.setPreferredSize(new Dimension(250, 40));
		textFieldNameC.setFont(new Font("Arial", Font.PLAIN, 20));
		textFieldNameC.setForeground(new Color(30, 30, 30));

		JLabel name = new JLabel("Server Name : ");
		serverNameC = new JTextField(18);

		name.setPreferredSize(new Dimension(150, 60));
		name.setFont(new Font("Arial", Font.PLAIN, 20));
		name.setForeground(new Color(0, 135, 200).brighter());

		serverNameC.setPreferredSize(new Dimension(250, 40));
		serverNameC.setFont(new Font("Arial", Font.PLAIN, 20));
		serverNameC.setForeground(new Color(30, 30, 30));

		JLabel port = new JLabel("Port Number : ");
		portNumC = new JTextField(18);

		port.setPreferredSize(new Dimension(150, 60));
		port.setFont(new Font("Arial", Font.PLAIN, 20));
		port.setForeground(new Color(0, 135, 200).brighter());

		portNumC.setPreferredSize(new Dimension(130, 40));
		portNumC.setFont(new Font("Arial", Font.PLAIN, 20));
		portNumC.setForeground(new Color(30, 30, 30));

		jbClientStart = new JButton("START");

		joinServerPanel.add(nameP);
		joinServerPanel.add(textFieldNameC);
		joinServerPanel.add(name);
		joinServerPanel.add(serverNameC);
		joinServerPanel.add(port);
		joinServerPanel.add(portNumC);
		setButtonStyle(jbClientStart, 110, joinServerPanel, Color.MAGENTA);

		// set the panel to transparent and add the panel to frame
		joinServerPanel.setOpaque(false);
		layeredPane.add(joinServerPanel, JLayeredPane.MODAL_LAYER);
		addListennerJoinServer();
	}

	/**
	 * The following method sets up the frame that tells the player server
	 * is waiting for other players to join in to start the game
	 */
	public void waitClientsPanel(){
		waitClientsPanel = new JPanel();
		setUpPanel(waitClientsPanel, 130, 200, 600, 200);

		// label used on waitClientsPanel
		JLabel waitClients = new JLabel("Wait For Other Players...");
		waitClients.setPreferredSize(new Dimension(600, 200));
		waitClients.setFont(new Font("Arial", Font.BOLD, 50));
		waitClients.setForeground(new Color(100, 200, 100).brighter());
		waitClientsPanel.add(waitClients);

		// set the panel to transparent and add the panel to frame
		waitClientsPanel.setOpaque(false);
		layeredPane.add(waitClientsPanel, JLayeredPane.MODAL_LAYER);
	}

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
	 * over and who is the winner
	 * @param p the winner player
	 */
	public void gameOverPanel(Player p){
		gameOverPanel = new JPanel();
		setUpPanel(gameOverPanel, 100, 200, 600, 160);

		// label used on gameOverPanel
		JLabel gameOver = new JLabel("Game Over!!!");
		gameOver.setPreferredSize(new Dimension(350, 80));
		gameOver.setFont(new Font("Arial", Font.BOLD, 50));
		gameOver.setForeground(new Color(100, 200, 100).brighter());
		gameOverPanel.add(gameOver);

		JLabel wins = new JLabel(p.getName());
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
	 * The following method sets the button style by the given 
	 * characteristics and adds the button onto the given panel
	 * @param button	the given button to set style on
	 * @param buttonWidth	the given width of the button
	 * @param panel	the panel the button will be on
	 * @param defaultColor	the default color of the given button
	 */
	private void setButtonStyle(final JButton button, final int buttonWidth, final JPanel panel, final Color defaultColor){
		// set the button size and font
		button.setPreferredSize(new Dimension(buttonWidth, 60));
		button.setFont(new Font("Arial", Font.PLAIN, 30));
		button.setForeground(defaultColor);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBorder(null);

		// set the button to transparent
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);

		// add mouseListener onto the button 
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

		// add the button to the given panel
		panel.add(button);
	}

	/**
	 * The following method adds action listener onto buttons on startPanel
	 */
	public void addListennerStart(){
		jbNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbNew){	// if button New is clicked, startPanel will be removed and choosePlayerPanel will appear
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
	 * The following method adds action listener onto buttons on choosePlayerPanel
	 */
	public void addListennerChoosePlayer(){
		jbSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbSingle){	// if button Single is clicked, choosePlayerPanel will be removed and chooseNamePanel will appear
					layeredPane.remove(choosePlayerPanel);
					chooseNamePanel();
					frame.repaint();
				}}});

		jbMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbMultiple){	// if button Multiple is clicked, choosePlayerPanel will be removed and chooseServerPanel will appear
					layeredPane.remove(choosePlayerPanel);
					chooseServerPanel();
					frame.repaint();
				}}});

	}

	/**
	 * The following method adds action listener onto buttons on chooseNamePanel
	 */
	public void addListennerChooseName(){
		jbStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbStart){	// if button Start is clicked, chooseNamePanel will be removed and single-player mode game will be started
					if(!textFieldName.getText().equals("")){
						name = textFieldName.getText();
						System.out.println("Player name: " + name);
						layeredPane.remove(chooseNamePanel);
						System.out.println(name);
						layeredPane.remove(backgroundPanel);
						startGame();
						frame.repaint();
						//textFieldRealName.setText("");
					}}}});

		textFieldName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = textFieldName.getText();	// get the name player entered from the textField
			}
		});
	}

	/**
	 * The following method adds action listener onto buttons on chooseServerPanel
	 */
	public void addListennerChooseServer(){
		jbStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbStartServer){	// if button Starts Server is clicked, chooseServerPanel will be removed and serverStartsPanel will appear
					String nP= JOptionPane.showInputDialog(
					        frame, 
					        "Enter the number of players of the game", 
					        "Number of Players", 
					        JOptionPane.INFORMATION_MESSAGE
					    );
					numPlayer = Integer.parseInt(nP);
					
					layeredPane.remove(chooseServerPanel);
					try {
						strServerName = getSeverName().getHostAddress();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					strPortNum = "4768";
					int port = Integer.parseInt(strPortNum);
					while(!available(port)){
						port++;
					}
					strPortNum = ""+port;

					Server server = new Server(port, numPlayer);
					server.start();

					serverStartsPanel();

					frame.repaint();
				}}});

		jbJoinServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbJoinServer){	// if button Starts Server is clicked, chooseServerPanel will be removed and joinServerPanel will appear
					layeredPane.remove(chooseServerPanel);
					joinServerPanel();
					frame.repaint();
				}}});

	}

	/**
	 * The following method adds action listener onto buttons on joinServerPanel
	 */
	public void addListennerJoinServer(){
		jbClientStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbClientStart){	// if button Start is clicked, joinServerPanel will be removed and multiple-player mode game will be started
					if(!textFieldNameC.getText().equals("") && !serverNameC.getText().equals("") && !portNumC.getText().equals("")){
						nameC = textFieldNameC.getText();
						strServerNameC = serverNameC.getText();
						strPortNumC = portNumC.getText();
//						System.out.println("Player name: " + nameC);
//						System.out.println("Server name: " + strServerNameC);
//						System.out.println("Port number: " + strPortNumC);
//						System.out.println("Server name real: " + strServerName);
//						System.out.println("Port number real: " + strPortNum);
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						layeredPane.remove(joinServerPanel);
						layeredPane.remove(backgroundPanel);
						startGame2();
						frame.repaint();
						//textFieldRealName.setText("");
					}}}});

		textFieldNameC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameC = textFieldNameC.getText();	// get the player name player entered from the textField
			}
		});

		serverNameC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				strServerNameC = serverNameC.getText();	// get the server name player entered from the textField
			}
		});

		portNumC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				strPortNumC = portNumC.getText();	// get the port number player entered from the textField
			}
		});
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
		renCon.setUICon(controller);
		renCon.setState(state);
		renCon.setNetCon(netCon);
		netCon.setState(state);
		netCon.setGameView(gameView);


		//gameView = new GameView( glcapabilities, frame );

		gameView.setEnabled( true );
		gameView.setVisible( true );
		gameView.setFocusable( true );
		layeredPane.add( gameView, JLayeredPane.DEFAULT_LAYER );
		if ( !gameView.requestFocusInWindow() ) System.out.println( "GameView can't get focus" );

		// add the southPanel onto the bottom of the frame
		southPanel = new SouthPanel(player);
		layeredPane.add(southPanel.getPanel(), JLayeredPane.MODAL_LAYER);
		
		
		
		
//		Timers timer = new Timers();    
//		int count = timer.getCountDownGame();
//		System.out.println("=============="+count);
	}

	/**
	 * The following method starts the game for multiple-player mode
	 */
	protected void startGame2() {
		waitClientsPanel();

		int clientPortNumber = Integer.parseInt(strPortNumC);
		client = new Client(this,nameC,strServerNameC,clientPortNumber );
		client.start();
		
		Packet00Login loginPacket = new Packet00Login(nameC);
		loginPacket.writeData(client);
		if(client.getPlayer()==null)System.out.println("null player");
		System.out.println("1111111111111" +client.getPlayer().ipAddress);
	//	System.out.println("1111111111111" + client.getPlayer().port);
	}

	/**
	 * The following method will call by client once client class received broadcast package from server
	 * player will able to start the game
	 * @param name - current player name
	 * @param state -  after been called game state
	 * */
	public void startClientWindows(Player player, GameView gameView){
		this.gameView = gameView;

		layeredPane.add( gameView, JLayeredPane.DEFAULT_LAYER );
		if ( !gameView.requestFocusInWindow() ) System.out.println( "GameView can't get focus" );
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
				frame,
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
				southPanel.getCollectItemsCanvas().setFloorNum(j);
				southPanel.getCollectItemsCanvas().repaint();
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

		// pop up the window to show the infomation
		javax.swing.UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 18))); 
		JOptionPane.showMessageDialog(frame, info, "Container", JOptionPane.INFORMATION_MESSAGE, icon);
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

	private InetAddress getSeverName() throws UnknownHostException {
		// TODO for Jacky
		return InetAddress.getLocalHost();
	}

	/**
	 * this method is check the socket port 
	 * @param port the number is going to check
	 * \*/
	private static boolean available(int port) {
		System.out.println("--------------Testing port " + port);
		DatagramSocket s = null;
		try {
			s = new DatagramSocket(port);

			// If the code makes it this far without an exception it means
			// something is using the port and has responded.
			System.out.println("--------------Port " + port + " is  available");
			s.close();
			return true;
		} catch (IOException e) {
			System.out.println("--------------Port " + port + " is not available");
			return false;
		} 
	}

	/**
	 * this method is check player input server Ip address
	 * if input is not ip adress will return false
	 * */
	public static final boolean isIPAdd(final String ip) {
		boolean isIPv4;
		try {
			final InetAddress inet = InetAddress.getByName(ip);
			isIPv4 = inet.getHostAddress().equals(ip)
					&& inet instanceof Inet4Address;
		} catch (final UnknownHostException e) {
			isIPv4 = false;
		}
		return isIPv4;
	}

	public void addWindowListener(WindowListeners windowListeners) {
		// TODO Auto-generated method stub
		this.addWindowListener(windowListeners);

	}
	
	public JFrame getFrame(){
		return frame;
	}
}

