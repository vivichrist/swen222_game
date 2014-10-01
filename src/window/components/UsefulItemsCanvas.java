package window.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class UsefulItemsCanvas extends Canvas {

	private ArrayList<ImageIcon> usefulItems;
	
	
	public UsefulItemsCanvas() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setBounds(0, 600, 400, 70);
		//this.setSize(new Dimension(400, 70));
		getUsefulItems();
	}
	
	private void getUsefulItems() {
		usefulItems = new ArrayList<ImageIcon>();
		usefulItems.add(new ImageIcon("Resource/torch.png"));
		usefulItems.add(new ImageIcon("Resource/key.png"));
	}

	public void paint(Graphics g) {
		int gap = 100;
		for(int i = 0; i < usefulItems.size(); i++){
			g.drawImage(usefulItems.get(i).getImage(), gap * i, 0, null);
		}
	}

}
