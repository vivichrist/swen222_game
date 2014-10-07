package window.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import world.components.TokenType;
import world.game.Player;

public class CollectItemsCanvas extends Canvas {
	
	private Player player;
	private TokenType type;
	private ArrayList<ImageIcon> collectItems;
	
	public CollectItemsCanvas(Player player) {
		this.player = player;
		type = player.getType();
		this.setBackground(Color.LIGHT_GRAY);
		this.setBounds(0, 600, 750, 60);
		//this.setSize(new Dimension(400, 70));
		collectItems = new ArrayList<ImageIcon>();
		getCollectItems();
	}
	
	private void getCollectItems() {
		// should get the collected items from a method call;
		collectItems.add(new ImageIcon("Resource/diamond/diamond grey.png"));
		collectItems.add(new ImageIcon("Resource/diamond/diamond grey.png"));
		collectItems.add(new ImageIcon("Resource/diamond/diamond grey.png"));
		collectItems.add(new ImageIcon("Resource/diamond/diamond grey.png"));
		collectItems.add(new ImageIcon("Resource/diamond/diamond grey.png"));
		collectItems.add(new ImageIcon("Resource/diamond/diamond grey.png"));

	}

	public void paint(Graphics g) {
		int gap = 65;
		for(int i = 0; i < collectItems.size(); i++){
			g.drawImage(collectItems.get(i).getImage(), gap * i, 0, 65, 60, null);
		}
	}

}
