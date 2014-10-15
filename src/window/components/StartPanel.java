package window.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartPanel extends JPanel{
	
	// buttons on all panels 
	private JButton jbNew;
	private JButton jbInfo;
	private JButton jbExit;
	
	/**
	 * The following method sets up the first panel appears on the frame, 
	 * creates four buttons on it. Let players choose how to start the game.
	 */
	private void startPanel() {
		setBounds(350, 180, 90, 600);

		// buttons used on startPanel
		jbNew = new JButton("New");
		jbInfo = new JButton("Info");
		jbExit = new JButton("Exit");

		setButtonStyle(jbNew, 75, this, new Color(0, 135, 200).brighter());
		setButtonStyle(jbInfo, 65, this, new Color(0, 135, 200).brighter());
		setButtonStyle(jbExit, 65, this, new Color(0, 135, 200).brighter());

		// set the panel to transparent and add the panel to frame
		setOpaque(false);
		addListennerStart();
	}
	
	/**
	 * The following method adds action listener onto buttons on startPanel
	 */
	public void addListennerStart(){
		jbNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == jbNew){	// if button New is clicked, startPanel will be removed and choosePlayerPanel will appear
					
					
					//
					
					//
					
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
	 * The following method sets the button style by the given 
	 * characteristics and adds the button onto the given panel
	 * @param button	the given button to set style on
	 * @param buttonWidth	the given width of the button
	 * @param panel	the panel the button will be on
	 * @param defaultColor	the default color of the given button
	 */
	private void setButtonStyle (final JButton button, final int buttonWidth, final JPanel panel, final Color defaultColor){
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
}
