package world.components;

import java.awt.Point;
import java.util.ArrayList;

public class Furniture implements StationaryObject {

	private final Direction facing;
	private ArrayList<Point> points;
	private CellType type;
	
	/**
	 * Constructor - creates a Couch object
	 * @param origin the "top left" square that this couch occupies
	 * @param facing the Direction that this couch faces
	 */
	public Furniture(CellType type, Point origin, Direction facing){
		points = new ArrayList<Point>();
		this.type = type;
		points.add(origin);
		this.facing = facing;
		populatePoints(origin);
	}
	
	@Override
	public Direction getFacing() {
		return facing;
	}
	
	public CellType getType(){
		return type;
	}
	
	/**
	 * Generates and stores the points that this Furniture object occupies on the map
	 */
	private void populatePoints(Point origin){
		switch(type){
			case COUCH:{
				switch(facing){
					case NORTH:	points.add(new Point(origin.x + 1, origin.y));
					case EAST: points.add(new Point(origin.x, origin.y + 1));
					case SOUTH: points.add(new Point(origin.x - 1, origin.y));
					case WEST: points.add(new Point(origin.x, origin.y - 1));
				}
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
