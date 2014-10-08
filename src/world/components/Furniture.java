package world.components;

import java.awt.Point;
import java.util.ArrayList;

public class Furniture implements StationaryObject {

	private final Direction facing;
	private ArrayList<Point> points;
	private FurnitureTypes type;
	
	/**
	 * Constructor - creates a Couch object
	 * @param origin the "top left" square that this couch occupies
	 * @param facing the Direction that this couch faces
	 */
	public Furniture(FurnitureTypes type, Point origin, Direction facing){
		points = new ArrayList<Point>();
		this.type = type;
		this.facing = facing;
		populatePoints();
	}
	
	@Override
	public Direction facing() {
		return facing;
	}
	
	/**
	 * Generates and stores the points that this Furniture object occupies on the map
	 */
	private void populatePoints(){
		switch(type){
			case COUCH:{
				
			}
			case BED:{
				
			}
			case TABLE:{
				
			}
			case DRAWERS:{
				
			}
		}
	}

}
