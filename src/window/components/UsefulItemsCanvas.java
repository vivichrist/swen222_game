package window.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import world.game.Player;

public class UsefulItemsCanvas extends Canvas {
	
	private Player player;
	private ArrayList<ImageIcon> usefulItems;

	public UsefulItemsCanvas(Player player) {
		this.player = player;
		this.setBackground(Color.LIGHT_GRAY);
		this.setBounds(0, 600, 750, 70);
		//this.setSize(new Dimension(400, 70));
		usefulItems = new ArrayList<ImageIcon>();
		getUsefulItems();
	}

	private void getUsefulItems() {
		usefulItems.add(new ImageIcon("Resource/11.png"));
		usefulItems.add(new ImageIcon("Resource/22.png"));
		usefulItems.add(new ImageIcon("Resource/33.png"));
		usefulItems.add(new ImageIcon("Resource/44.png"));
		usefulItems.add(new ImageIcon("Resource/55.png"));

	}

	public void paint(Graphics g) {
		int gap = 65;
		for(int i = 0; i < usefulItems.size(); i++){
			g.drawImage(usefulItems.get(i).getImage(), gap * i, 0, 65, 65, null);
		}
	}

}
