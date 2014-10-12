package world;

import java.awt.Color;
import java.util.ArrayList;

public class ColourPalette {

	private static ArrayList<Color> colours;
	
	public ColourPalette(){
		colours = new ArrayList<Color>();
		colours.add(Color.BLUE);
		colours.add(Color.GREEN);
		colours.add(Color.YELLOW);
		colours.add(Color.MAGENTA);
		colours.add(Color.RED);
	}
	
	public static Color get(int i){
		if(i >= colours.size()) return null;
		return colours.get(i);
	}
	
	public static int size(){
		return colours.size();
	}
}
