package window.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class CollectItemsCanvas extends Canvas {
	
	private ArrayList<ImageIcon> collectItems;
	
	
	public CollectItemsCanvas() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setBounds(0, 600, 400, 70);
		//this.setSize(new Dimension(400, 70));
		getCollectItems();
	}
	
	private void getCollectItems() {
		collectItems = new ArrayList<ImageIcon>();
		collectItems.add(new ImageIcon("Resource/coin.png"));
		collectItems.add(new ImageIcon("Resource/ring.png"));
		collectItems.add(new ImageIcon("Resource/nicklace.png"));
	}

	public void paint(Graphics g) {
		int gap = 100;
		for(int i = 0; i < collectItems.size(); i++){
			g.drawImage(collectItems.get(i).getImage(), gap * i, 0, null);
		}
	}

}
