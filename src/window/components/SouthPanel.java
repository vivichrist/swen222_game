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

/**
 *
 * @author sunzhih
 *
 */
public class SouthPanel extends JPanel {
	/**
	 *
	 */
	private static int left = 0;
	private static int top = 600;
	private static int width = 800;
	private static int height = 770;
	

	private JPanel panel;
	private JLabel characterShow = new JLabel();


	public SouthPanel() {
		panel = new JPanel();
		panel.setBounds(left, top, width, height);
		panel.setBackground(Color.DARK_GRAY);
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
	
	
	