package world.components;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

enum CellType {
	EMPTY, WALL, DOOR, OPENDOOR, OUTOFBOUNDS;
}

/**
 * A Map for a "level" or floor in the game.  Created by reading from valid map files.
 * A valid map file consists of a "header" line with the map's x and y values (width and height), with consecutive lines 
 * containing space delimited ints representing rows of map cell types (as enumerated).
 * A Map contains the floor layout for the level, along with the positions of objects on the level.
 * @author Kalo Pilato - ID: 300313803
 * 		& Vivian Stewart
 *
 */
public class Map {

	private int xLimit, yLimit;
	private CellType[][] map;
	private List<Point> emptyCells;
	private MoveableObject[][] moveObj;
	private StationaryObject[][] statObj;
	
	/**
	 * Constructor - scans in the floor layout from a given map file.
	 * @param file the map file for this floor
	 */
	public Map(File file){	
		try{
			Scanner scan = new Scanner(file);
			
			// First read header for map width and height
			if(!scan.hasNextInt()) throw new Exception("Map format error: first header token should be an int");
			else xLimit = scan.nextInt();
			if(!scan.hasNext("x")) throw new Exception("Map format error: second header toden should be 'x'");
			else scan.next();
			if(!scan.hasNextInt()) throw new Exception("Map format error: third header token should be an int");
			else yLimit = scan.nextInt();
			
			// Initialise arrays
			map = new CellType[xLimit][yLimit];
			moveObj = new MoveableObject[xLimit][yLimit];
			statObj = new StationaryObject[xLimit][yLimit];
			emptyCells = new ArrayList<Point>();
			
			// Read the map, populating the 2d map array and list of empty cells
			for(int y = 0; y < yLimit; y++){
				for(int x = 0; x < xLimit; x++){
					if(!scan.hasNextInt()){
						scan.close();
						throw new Exception("Map format error, not enough data");
					}
					CellType current = CellType.values()[scan.nextInt()];
					if(current == CellType.EMPTY){
						emptyCells.add(new Point(x, y));
					}
					map[x][y] = current;
				}
			}
			scan.close();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the Point corresponding to a randomly selected empty cell on this floor.
	 * This Point will now be seen as "occupied" by this floor.  If the Point has not been populated/occupied after being removed
	 * it should be set back to empty with setEmpty(). 
	 * @return the Point corresponding to a randomly selected empty cell on this floor
	 */
	public Point randomEmptyCell(){
		if(emptyCells.isEmpty()){
			return null;
		}
		else{
			Random random = new Random();
			return(emptyCells.remove(random.nextInt(emptyCells.size())));
		}
	}
	
	/**
	 * Sets the empty cell on this floor corresponding to a given Point to "unoccupied". 
	 * @param p the Point of the cell to be set to "unoccupied"
	 */
	public void setEmpty(Point p){
		CellType current = map[p.x][p.y];
		//TODO: how should this be handled? Throw an exception? 
		if(current != CellType.EMPTY){
			System.out.println("Cannot set a cell of type '" + current + "' to empty"); 
		}
		else{
			emptyCells.add(p);
		}
	}
	
	/**
	 * Adds a StationaryObject to this floor.  
	 * This is only allowed if the cell type is EMPTY and it is not occupied by another game world object
	 * @param x the x coordinate of the cell 
	 * @param y the y coordinate of the cell
	 * @param s the StationaryObject to place at x, y
	 * @return true if successfully added
	 */
	public boolean addStationary(int x, int y, StationaryObject s){
		if(statObj[x][y] != null || moveObj[x][y] != null || map[x][y] != CellType.EMPTY){
			return false;
		}
		else{
			statObj[x][y] = s;
			return true;
		}
	}
	
	/**
	 * Adds a MoveableObject to this floor.  
	 * This is only allowed if the cell type is EMPTY and it is not occupied by another game world object
	 * @param x the x coordinate of the cell 
	 * @param y the y coordinate of the cell
	 * @param s the MoveableObject to place at x, y
	 * @return true if successfully added
	 */
	public boolean addMoveable(int x, int y, MoveableObject m){
		if(moveObj[x][y] != null || statObj[x][y] != null || map[x][y] != CellType.EMPTY){
			return false;
		}
		else{
			moveObj[x][y] = m;
			return true;
		}
	}
	
}
