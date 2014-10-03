package ui.components;

import java.util.HashMap;

import world.components.CellType;

public class MeshStore
{
	private static MeshStore instance = null;
	HashMap<CellType, Mesh> meshes = new HashMap<CellType, Mesh>();
	HashMap<CellType, String> loading = new HashMap<CellType, String>()
	{
		private static final long	serialVersionUID	= 1L;
		{// add new meshes to load here...
			put( CellType.KEY, "key.obj" );
			put( CellType.DOOR, "door.obj" );
			put( CellType.CONE, "cone.obj" );
			put( CellType.CUBE, "cube.obj" );
			put( CellType.BALL, "ball.obj" );
			put( CellType.DIAMOND, "diamond.obj" );
			put( CellType.CHEST, "chest.obj" );
			put( CellType.BREIFCASE, "breifcase.obj" );
			put( CellType.DRAWERS, "drawers.obj" );
			put( CellType.TABLE, "table.obj" );
			put( CellType.DRAWERS, "drawers.obj" );
			put( CellType.TICKET, "ticket.obj" );
			put( CellType.TORCH, "torch.obj" );
			put( CellType.TELEPORT, "teleport.obj" );
			put( CellType.PLAYER, "player.obj" );
		}
	};
	private MeshStore(){
		for ( CellType t: loading.keySet() )
		{
			ObjReader read = new ObjReader( "mesh/" +  loading.get( t ) );
			meshes.put( t, new Mesh( read.getIndices(), read.getVertices() ) );
		}
	}

	public static MeshStore instance()
	{
		if ( instance == null ) instance = new MeshStore();
		return instance;
	}

	public Mesh getMesh( CellType type )
	{
		if ( !meshes.containsKey( type ) )
			throw new RuntimeException( "There is no mesh for that type." );
		return meshes.get( type );
	}
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{

	}
}
