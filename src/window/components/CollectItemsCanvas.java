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
		this.setBounds(0, 600, 400, 60);
		//this.setSize(new Dimension(400, 70));
		getCollectItems();
	}
	
	private void getCollectItems() {
		collectItems = new ArrayList<ImageIcon>();
		ImageIcon temp = new ImageIcon("Resource/0.png");
		collectItems.add(new ImageIcon("Resource/1.png"));
		collectItems.add(new ImageIcon("Resource/2.png"));
		collectItems.add(new ImageIcon("Resource/3.png"));
		collectItems.add(new ImageIcon("Resource/4.png"));
		collectItems.add(new ImageIcon("Resource/5.png"));
		collectItems.add(new ImageIcon("Resource/6.png"));

	}

	public void paint(Graphics g) {
		int gap = 65;
		for(int i = 0; i < collectItems.size(); i++){
			g.drawImage(collectItems.get(i).getImage(), gap * i, 0, null);
		}
	}

}
