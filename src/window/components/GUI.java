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


import controllers.Controller;
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

public class GUI  {

	private static int width = 800;
	private static int height = 770;
	private GameState gameState;//do not change this field for jacky only
	private static Controller controller;
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

	private Player player;
	public  String name;
	//private final BoardCanvasNorth canvas;
	//private final BoardCanvasSouth cardCanvas;

	JButton jbNew;
	JButton jbLoad;
	JButton jbInfo;
	JButton jbExit;
	JButton jbSingle;
	JButton jbMultiple;
	JButton jbStart;

	JTextField textFieldName;


	public GUI(){

		//cardCanvas = new BoardCanvasSouth(board, canvas);

		setUp();
	}
	public void setUp(){
		//canvas.repaint();
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
		ImageIcon background = new ImageIcon("Resource/background.png");
		backgroundPanel = new JPanel();
		backgroundPanel.setBounds(0, 0, width, height);
		
		JLabel jl = new JLabel(background);
		backgroundPanel.add(jl);

		//backgroundPanel=new JButton("AAA");
		//jb.setBounds(100,100,100,100);

		layeredPane.add( backgroundPanel, JLayeredPane.DEFAULT_LAYER );
		//layeredPane.add(backgroundPanel,JLayeredPane.MODAL_LAYER);
		frame.setLayeredPane(layeredPane);

		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setVisible( true );



		startPanel();

		//frame.add(canvas, BorderLayout.CENTER);

		frame.setVisible(true);
	}

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
		
		startPanel.setOpaque(false);
		layeredPane.add(startPanel, JLayeredPane.MODAL_LAYER);
		addListennerStart();

	}

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

	public void chooseNamePanel(){
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
		
		chooseNamePanel.setOpaque(false);
		layeredPane.add(chooseNamePanel, JLayeredPane.MODAL_LAYER);
		addListennerChooseName();
	}
	
	
	
	public void chooseNamePanel2(){
		int chooseNamePanelLeft = 325;
		int chooseNamePanelTop = 200;
		int chooseNamePanelWidth = 150;
		int chooseNamePanelHeight = 120;

		chooseNamePanel = new JPanel();
		chooseNamePanel.setBounds(chooseNamePanelLeft, chooseNamePanelTop, chooseNamePanelWidth, chooseNamePanelHeight);

		JLabel chooseName = new JLabel("Name:   ");
		textFieldName = new JTextField(8);
		jbStart = new JButton("Start");

		jbStart.setOpaque(false);
		jbStart.setContentAreaFilled(false);
		jbStart.setBorderPainted(false);

		chooseNamePanel.add(chooseName);
		chooseNamePanel.add(textFieldName);
		chooseNamePanel.add(jbStart);

		chooseNamePanel.setOpaque(false);
		layeredPane.add(chooseNamePanel, JLayeredPane.MODAL_LAYER);
		addListennerChooseName2();
	}

	
	
	
	
	private void setButtonStyle(final JButton button, final int buttonWidth, final JPanel panel, final Color defaultColor){
		button.setPreferredSize(new Dimension(buttonWidth, 60));
		button.setFont(new Font("Arial", Font.PLAIN, 30));
		button.setForeground(defaultColor);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBorder(null);
		
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		
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

	public void addListennerChoosePlayer(){
		jbSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbSingle){
					layeredPane.remove(choosePlayerPanel);
					chooseNamePanel();
					frame.repaint();
				}}});

		jbMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbMultiple){
					layeredPane.remove(choosePlayerPanel);

					GameState state = null;
					MultyPlayer player1 = null;
					if (JOptionPane.showConfirmDialog(frame, "Do you want to run the server") == 0) {
						server = new Server();
						server.start();
					}else   chooseNamePanel2();
					//test t = new test();
					//t.testServerPlayerListName();
					frame.repaint();
				}}});

	}

	public void addListennerChooseName(){
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
						startGame();
						frame.repaint();
						//textFieldRealName.setText("");
					}}}});

		textFieldName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = textFieldName.getText();
			}
		});
	}
	public void addListennerChooseName2(){
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
						startGame2();
						frame.repaint();
						//textFieldRealName.setText("");
					}}}});

		textFieldName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = textFieldName.getText();
			}
		});
	}

	protected void startGame() {
		GLProfile.initSingleton();
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities( glprofile );

		//Code added by Kalo
		GameState state = new GameBuilder(name).getGameState();
		//state.setController(controller);
		controller = new Controller(state, this);
		gameView = new GameView( glcapabilities, frame, state );


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
		GLProfile.initSingleton();
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities( glprofile );

		//Code added by Kalo
		GameState state = null;
		MultyPlayer player1 = null;
		ArrayList<Player>players = new ArrayList<Player>();
		Map[]floors =new Map[1];
		floors[0] = new Map(new File("map1.txt"));
		player1 = new MultyPlayer(name, new Point(18,20),null,null, -1);
		state = new GameState(players,floors);
		Client client = new Client(state,"localhost");
		client.start();
		Packet00Login loginPacket = new Packet00Login(player1.getName(), player1.getPosition().x,player1.getPosition().y);
		if (server.serverStart==99) {
			server.addConnection(player1, loginPacket);
			int size1 = server.getConnectedPlayers().size();

			System.out.println("size1: "+size1);

		}else System.out.println("server== null");
		loginPacket.writeData(client);
		System.out.println("state.getPlayers().size(): "+ state.getPlayers().size());


		//client.
		controller = new Controller(state, this);

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
			while(state.isMoved()){
				client.checkState();
			}
		}
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

}