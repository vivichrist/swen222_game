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
	 * Returns an ArrayList of the Points occupied by this piece of Furniture
	 * @return an ArrayList of the Poinst occupied by this piece of Furniture
	 */
	public ArrayList<Point> getPoints(){
		return points;
	}

	/**
	 * Generates and stores the points that this Furniture object occupies on the map
	 */
	private void populatePoints(Point origin){
		switch(type){
			case COUCH:{
				points.add(origin);
				switch(facing){
					case NORTH:	points.add(new Point(origin.x + 1, origin.y));
					case EAST: points.add(new Point(origin.x, origin.y + 1));
					case SOUTH: points.add(new Point(origin.x - 1, origin.y));
					case WEST: points.add(new Point(origin.x, origin.y - 1));
				}
			}
			case BED:{
				switch(facing){
					case NORTH:	
						for(int y = 0; y < 2; y++){
							for(int x = 0; x < 3; x++){
								points.add(new Point(origin.x + x, origin.y + y));
							}
						}
					case EAST: 
						for(int y = 0; y < 3; y++){
							for(int x = 0; x < 2; x++){
								points.add(new Point(origin.x - x, origin.y + y));
							}
						}
					case SOUTH: 
						for(int y = 0; y < 2; y++){
							for(int x = 0; x < 3; x++){
								points.add(new Point(origin.x - x, origin.y - y));
							}
						}
					case WEST: 
						for(int y = 0; y < 3; y++){
							for(int x = 0; x < 2; x++){
								points.add(new Point(origin.x + x, origin.y - y));
							}
						}
				}
			}
			case TABLE:{
				points.add(origin);
				switch(facing){
					case NORTH:	points.add(new Point(origin.x + 1, origin.y));
					case EAST: points.add(new Point(origin.x, origin.y + 1));
					case SOUTH: points.add(new Point(origin.x - 1, origin.y));
					case WEST: points.add(new Point(origin.x, origin.y - 1));
				}
			}
			case DRAWERS:{
				points.add(origin);
			}
		}
	}

}
