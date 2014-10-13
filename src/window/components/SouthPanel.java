package window.components;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

import world.game.Player;

/**
 * The SouthPanel is responsible for setting up a JPanel to hold CollectItemsCanvas
 * and UsefulItemsCanvas. This class is added to the bottom of the game window.
 * 
 * @author Zhiheng Sun,  ID: 300256273
 * 
 */
public class SouthPanel {
	/**
	 *
	 */
	private static int left = 0;
	private static int top = 600;
	private static int width = 800;
	private static int height = 170;

	private JPanel panel;

	private CollectItemsCanvas collectItemsCanvas;
	private UsefulItemsCanvas usefulItemsCanvas;
	
	/**
	 * Sets up the panel for the given player
	 * @param player	the player whose tokens and inventories will be drawn on canvases
	 */
	public SouthPanel(Player player) {
		panel = new JPanel();
		panel.setBounds(left, top, width, height);
		panel.setBackground(Color.BLACK);
		collectItemsCanvas = new CollectItemsCanvas(player);
		panel.add(collectItemsCanvas, BorderLayout.WEST);
		usefulItemsCanvas = new UsefulItemsCanvas(player);
		panel.add(usefulItemsCanvas, BorderLayout.WEST);
		panel.repaint();
	}
	
	/**
	 * The following method returns the current CollectItemsCanvas on the panel
	 * @return	the current CollectItemsCanvas
	 */
	public CollectItemsCanvas getCollectItemsCanvas() {
		return collectItemsCanvas;
	}
	
	/**
	 * The following method returns the current UsefulItemsCanvas on the panel
	 * @return	the current UsefulItemsCanvas
	 */
	public UsefulItemsCanvas getUsefulItemsCanvas() {
		return usefulItemsCanvas;
	}

	/**
	 * The following method returns the current panel 
	 * @return	the current panel
	 */
	public JPanel getPanel() {
		return panel;
	}
}
	
	
	