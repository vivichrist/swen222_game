package world;

import java.io.File;

import world.components.Map;

public class TemporaryMain {

	public static void main(String[] args){
		new Map(new File("map.txt"));
	}
}
