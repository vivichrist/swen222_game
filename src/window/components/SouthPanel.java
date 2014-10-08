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
 *
 * @author sunzhih
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
	
	public SouthPanel(Player player) {
		panel = new JPanel();
		panel.setBounds(left, top, width, height);
		panel.setBackground(Color.LIGHT_GRAY);
		collectItemsCanvas = new CollectItemsCanvas(player);
		panel.add(collectItemsCanvas, BorderLayout.WEST);
		usefulItemsCanvas = new UsefulItemsCanvas(player);
		panel.add(usefulItemsCanvas, BorderLayout.WEST);
		panel.repaint();
	}
	
	public CollectItemsCanvas getCollectItemsCanvas() {
		return collectItemsCanvas;
	}

	public UsefulItemsCanvas getUsefulItemsCanvas() {
		return usefulItemsCanvas;
	}

	public JPanel getPanel() {
		return panel;
	}
}
	
	
	