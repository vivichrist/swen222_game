package window.components;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

import com.jogamp.graph.font.Font;

import ui.components.GameView;
import world.game.GameBuilder;
import world.game.GameState;

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
	Canvas canvas = new Canvas();
	GLJPanel gameView;
	JFrame frame;
	JLayeredPane layeredPane;
	JPanel backgroundPanel;
	SouthPanel southPanel;
	JPanel startPanel;
	JPanel choosePlayerPanel;
	JPanel chooseNamePanel;

	String name;
	//private final BoardCanvasNorth canvas;
	//private final BoardCanvasSouth cardCanvas;

	JButton jbNewGame;
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
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);

//		frame.validate();
//		frame.repaint();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLayout(null);
//		frame.setResizable(false);

		layeredPane = new JLayeredPane();
		ImageIcon background = new ImageIcon("Resource/Background.png");
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
		int startPanelLeft = 325;
		int startPanelTop = 180;
		int startPanelWidth = 150;
		int startPanelHeight = 160;

		startPanel = new JPanel();
		startPanel.setBounds(startPanelLeft, startPanelTop, startPanelWidth, startPanelHeight);
//		startPanel.setLayout(null);
//		startPanel.setBounds(startPanelLeft, startPanelTop, startPanelWidth, startPanelHeight);
//		startPanel.setLayout(null);
		
		
		jbNewGame = new JButton("New Game");
		jbLoad = new JButton("Load");
		jbInfo = new JButton("Info");
		jbExit = new JButton("Exit");

		jbNewGame.setOpaque(false);
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
//		jbNewGame.setPreferredSize(new Dimension(100, 40));
//		jbNewGame.setFont((java.awt.Font) new Font("Arial", Font.PLAIN, 40));
		
		
//		jbNewGame.setLayout(null);
//		jbNewGame.setBounds(350, 200, 30, 30);
//		jbLoad.setLayout(null);
//		jbLoad.setBounds(350, 200, 30, 30);
		
		
		
		
		startPanel.add(jbNewGame);
		startPanel.add(jbLoad);
		startPanel.add(jbInfo);
		startPanel.add(jbExit);

		
		jbNewGame.setOpaque(false);
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

		jbSingle.setOpaque(false);
		jbSingle.setContentAreaFilled(false);
		jbSingle.setBorderPainted(false);
		
		jbMultiple.setOpaque(false);
		jbMultiple.setContentAreaFilled(false);
		jbMultiple.setBorderPainted(false);
		
		choosePlayerPanel.add(jbSingle);
		choosePlayerPanel.add(jbMultiple);

		choosePlayerPanel.setOpaque(false);
		layeredPane.add(choosePlayerPanel, JLayeredPane.MODAL_LAYER);
		addListennerChoosePlayer();
	}

	public void chooseNamePanel(){
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
		addListennerChooseName();
	}

	public void addListennerStart(){
		jbNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbNewGame){
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
					chooseNamePanel();
					frame.repaint();
				}}});

	}

	public void addListennerChooseName(){
		jbStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbStart){
					if(!textFieldName.getText().equals("")){
						layeredPane.remove(chooseNamePanel);
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


	protected void startGame() {
		GLProfile.initSingleton();
    	GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        
        //Code added by Kalo
        GameState state = new GameBuilder(name).getGameState();
        gameView = new GameView( glcapabilities, frame, state );
        
        //gameView = new GameView( glcapabilities, frame );
        gameView.setEnabled( true );
        gameView.setVisible( true );
        gameView.setFocusable( true );
        layeredPane.add( gameView, JLayeredPane.DEFAULT_LAYER );
        if ( !gameView.requestFocusInWindow() ) System.out.println( "GameView can't get focus" );
        southPanel = new SouthPanel();
		layeredPane.add(southPanel.getPanel(), JLayeredPane.MODAL_LAYER);
	}

	public static void main(String[] args){
		GUI gui = new GUI();
	}

}