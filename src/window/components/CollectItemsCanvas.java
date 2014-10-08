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
		this.setBounds(0, 600, 750, 70);
		//this.setSize(new Dimension(400, 70));
		initialiseItems();
		getCollectItems();
	}
	
	private void initialiseItems() {
		collectItems = new ArrayList<ImageIcon>();
		for(int i = 0; i < player.getTokenList().size(); i++){
			String resource = "Resource/" + type.toString() + "/" + type.toString() + " grey" + ".png";
			collectItems.add(i, new ImageIcon(resource));			
		}
	}

	private void getCollectItems() {		
		for (int i = 0; i < player.getTokenList().size(); i++){
			if (player.getTokenList().get(i).isFound()){
				String color = identifyColor(player.getTokenList().get(i).getColor());
				String resource = "Resource/" + type.toString() + "/" + type.toString() + " " + color + ".png";
				collectItems.add(i, new ImageIcon(resource));
			}
		}
	}

	private String identifyColor(Color color) {
		if (color.equals(Color.BLUE)){
			return "blue";
		} else if (color.equals(Color.GREEN)){
			return "green";
		} else if (color.equals(Color.MAGENTA)){
			return "magenta";
		} else if (color.equals(Color.RED)){
			return "red";
		} else if (color.equals(Color.YELLOW)){
			return "yellow";
		}
		return null;
	}

	public void paint(Graphics g) {
		System.out.println("paint called");
		getCollectItems();
		int gap = 65;
		for(int i = 0; i < collectItems.size(); i++){
			g.drawImage(collectItems.get(i).getImage(), gap * i, 0, 65, 65, null);
		}
		//g.drawRect(0, 600, 750, 70);
	}

}
