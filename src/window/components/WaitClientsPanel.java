package window.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class WaitClientsPanel extends Panel{

	public WaitClientsPanel(GUI gui) {
		super(gui);
		setBounds(130, 200, 600, 200);	}

	@Override
	protected void setUpComponents() {
		// label used on waitClientsPanel
		JLabel waitClients = new JLabel("Wait For Other Players...");
		waitClients.setPreferredSize(new Dimension(600, 200));
		waitClients.setFont(new Font("Arial", Font.BOLD, 50));
		waitClients.setForeground(new Color(100, 200, 100).brighter());
		add(waitClients);
	}

	@Override
	protected void addListenner() {}

}
