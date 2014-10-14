package window.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
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

	private Player player;	// the current player
	
	/**
	 * The following is a list of ImageIcons of the inventory of the current player
	 */
	private ArrayList<ImageIcon> usefulItems;
	
	
	
	
	
	private int time;
	
	
	/**
	 * Sets up the UsefulItemsCanvas for the given player
	 * @param player	the player whose inventories will be drawn on canvas
	 */
	public UsefulItemsCanvas(Player player) {
		this.player = player;
		this.setBackground(Color.LIGHT_GRAY);
		this.setBounds(0, 600, 750, 65);
		
		// Kalo bugfix:
		//usefulItems = new ArrayList<ImageIcon>();
		
		getUsefulItems();
	}

	/**
	 * The following method gets the inventories of the player, and adds the 
	 * corresponding images to the images list.
	 */
	private void getUsefulItems() {
		//Kalo bugfix:
		usefulItems = new ArrayList<ImageIcon>();
		
		for (int i = usefulItems.size(); i < player.getInventory().size(); i++){	// go through the player's inventory, add images to collectItems
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
		for(int i = 0; i < usefulItems.size(); i++){	// go through the player's inventory images, draw the images on canvas with given width and height
			g.drawImage(usefulItems.get(i).getImage(), gap * i, 0, 65, 65, null);
		}
		
		// set the floor name string style and draw it on canvas
				g.setFont(new Font("Arial", Font.BOLD, 31));
				g.setColor(new Color(100, 200, 100));
				g.drawString("" + time, 420, 45);
		
	}

	public void setTime(int time){
		this.time = time;
	}
}
