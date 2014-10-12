package world;

import java.awt.Color;
import java.util.ArrayList;

public class ColourPalette {

	private static ArrayList<Color> colours;
	private static ArrayList<String> names;
	
	public ColourPalette(){
		colours = new ArrayList<Color>();
		names = new ArrayList<String>();
		colours.add(Color.BLUE);
		names.add("Blue");
		colours.add(Color.GREEN);
		names.add("Green");
		colours.add(Color.YELLOW);
		names.add("Yellow");
		colours.add(Color.MAGENTA);
		names.add("Magenta");
		colours.add(Color.RED);
		names.add("Red");
	}
	
	public static Color get(int i){
		if(i >= colours.size()) return null;
		return colours.get(i);
	}
	
	public static String getName(int i){
		if(i >= names.size()) return null;
		return names.get(i);
	}
	
	public static int size(){
		return colours.size();
	}
}
