package window.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import world.game.Player;

/**
 * The UsefulItemsCanvas is responsible for drawing inventories on canvas.
 * The canvas is empty at beginning. Once player picks up something, the 
 * item image will be drawn on the canvas.
 * 
 * @author Zhiheng Sun,  ID: 300256273
 * 
 */
public class UsefulItemsCanvas extends Canvas {

	private Player player;
	private ArrayList<ImageIcon> usefulItems;
	
	/**
	 * Sets up the UsefulItemsCanvas for the given player
	 * @param player	the player whose inventories will be drawn on canvas
	 */
	public UsefulItemsCanvas(Player player) {
		this.player = player;
		this.setBackground(Color.LIGHT_GRAY);
		this.setBounds(0, 600, 750, 70);
		//this.setSize(new Dimension(400, 70));
		usefulItems = new ArrayList<ImageIcon>();
		getUsefulItems();
	}

	/**
	 * The following method gets the inventories of the player, and adds the 
	 * corresponding images to the images list.
	 */
	private void getUsefulItems() {
		for (int i = usefulItems.size(); i < player.getInventory().size(); i++){
			String resource = "Resource/inventory/" + player.getInventory().get(i).toString().toLowerCase() + ".png";
			usefulItems.add(new ImageIcon(resource));
		}
	}

	/**
	 * The following method draws all the images on the UsefulItemsCanvas.
	 */
	public void paint(Graphics g) {
		getUsefulItems();
		int gap = 65;
		for(int i = 0; i < usefulItems.size(); i++){
			g.drawImage(usefulItems.get(i).getImage(), gap * i, 0, 65, 65, null);
		}
	}

}
