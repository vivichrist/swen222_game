package ui.components;

import java.util.HashMap;

public class MeshStore
{
	private static MeshStore instance = null;
	HashMap<Type, Mesh> meshes = new HashMap<Type, Mesh>();
	HashMap<Type, String> loading = new HashMap<Type, String>()
	{
		private static final long	serialVersionUID	= 1L;
		{// add new meshes to load here...
			put( Type.KEY, "key.obj" );
			put( Type.DOOR, "door.obj" );
		}
	};
	private MeshStore(){
		for ( Type t: loading.keySet() )
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

	public Mesh getMesh( Type type )
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
