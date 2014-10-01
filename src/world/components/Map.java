package world.components;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import world.game.Player;


enum CellType {
	EMPTY, WALL, DOOR, TELEPORT, OPENDOOR, OUTOFBOUNDS;
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
public class Map implements java.io.Serializable{

	private int xLimit, yLimit;
	private CellType[][] map;
	private List<Point> emptyCells;
	private HashMap<Point, MoveableObject> moveableObjects = new HashMap<Point, MoveableObject>();
	private HashMap<Point, StationaryObject> stationaryObjects = new HashMap<Point, StationaryObject>();
	private HashMap<Point, Door> doors = new HashMap<Point, Door>();
	private HashMap<Point, GameToken> tokens = new HashMap<Point, GameToken>();
	private HashMap<Point, Player> players = new HashMap<Point, Player>();
	
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
			emptyCells = new ArrayList<Point>();
			
			// Read the map, populating the 2d map array, list of empty cells and hashmap of doors
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
					if(current == CellType.DOOR){
						doors.put(new Point(x, y), new Door(false));
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
	 * @param p the Point to add this Stationary Object to
	 * @param s the StationaryObject to add
	 * @return true if successfully added
	 */
	public boolean addStationary(Point p, StationaryObject s){
		if(moveableObjects.containsKey(p) | stationaryObjects.containsKey(p) | tokens.containsKey(p) | map[p.x][p.y] != CellType.EMPTY){
			return false;
		}
		else{
			stationaryObjects.put(p, s);
			return true;
		}
	}
	
	/**
	 * Adds a MoveableObject to this floor
	 * This is only allowed if the cell type is EMPTY and it is not occupied by another game world object
	 * @param p the Point to add this Stationary Object to
	 * @param m the MoveableObject to add
	 * @return true if successfully added
	 */
	public boolean addMoveable(Point p, MoveableObject m){
		if(stationaryObjects.containsKey(p) | moveableObjects.containsKey(p) | tokens.containsKey(p) | map[p.x][p.y]!= CellType.EMPTY){
			return false;
		}
		else{
			moveableObjects.put(p,  m);
			return true;
		}
	}
	
	/**
	 * Adds a Token to this floor
	 * This is only allowed if the cell type is EMPTY and it is not occupied by another game world object
	 * @param p the Point to add this Token to
	 * @param t the Token to add
	 * @return true if successfully added
	 */
	public boolean addGameToken(Point p, GameToken t){
		if(tokens.containsKey(p) | moveableObjects.containsKey(p) | stationaryObjects.containsKey(p) | map[p.x][p.y]!= CellType.EMPTY){
			return false;
		}
		else{
			tokens.put(p,  t);
			return true;
		}
	}
	
	/**
	 * Positions a Player on this floor.  Doesn't allow two Players to occupy the same cell
	 * @param p the Point to position the Player at
	 * @param player the Player to position
	 * @return true if successfully placed
	 */
	public boolean placePlayer(Point p, Player player){
		if(players.containsKey(p)){
			return false;
		}
		else{
			players.put(p,  player);
			return true;
		}
	}
	
	/**
	 * Moves a Player on this Map.  A Player cannot be moved if their origin position doesn't match the Map's record of the Player's position
	 * or the Player's destination is occupied by another Player.
	 * @param player the Player to move
	 * @param oldPos the Player's origin position
	 * @param newPos the Player's destination position
	 * @return true if successfully moved
	 */
	public boolean movePlayer(Player player, Point oldPos, Point newPos){
		if(!players.containsKey(oldPos) || players.containsKey(newPos)) return false;
		else{
			players.remove(oldPos);
			players.put(newPos, player);
			return true;
		}
	}
	
	/**
	 * Returns the xLimit for this Map
	 * @return the xLimit for this Map
	 */
	public int getXLimit(){
		return xLimit;
	}
	
	/**
	 * Returns the yLimit for this Map
	 * @return the yLimit for this Map
	 */
	public int getYLimit(){
		return yLimit;
	}
	
	/**
	 * Returns the CellType map for this Map
	 * @return
	 */
	public CellType[][] getMap(){
		return map;
	}
}
