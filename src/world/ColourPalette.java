package world;

import java.awt.Color;
import java.util.ArrayList;

/**
 * The palette of Colors for this game.  Includes a mapping of the java.awt.Color object to a simple String name.
 * @author Kalo Pilato - ID: 300313803
 *
 */
public class ColourPalette {

	private static ArrayList<Color> colours;
	private static ArrayList<String> names;
	
	/**
	 * Constructor - builds the predefined collection of Colors for this ColourPalette
	 */
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
	
	/**
	 * Returns the Color at a given index in this ColourPalette
	 * @param index the index of the Color
	 * @return the Color at index
	 */
	public static Color get(int index){
		System.out.println(index);
		if(index >= colours.size()) return null;
		return colours.get(index);
	}
	
	/**
	 * Returns the name of the Color at a given index in this ColourPalette
	 * @param index the index of the Color
	 * @return the name of the Color
	 */
	public static String getName(int index){
		if(index >= names.size()) return null;
		return names.get(index);
	}
	
	/**
	 * Returns the size of this ColourPalette
	 * @return the size of this ColourPalette
	 */
	public static int size(){
		return colours.size();
	}
	
}
