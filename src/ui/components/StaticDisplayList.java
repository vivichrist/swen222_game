package ui.components;

import javax.media.opengl.GL2;

public class StaticDisplayList
{
	private int	staticID;

	public static StaticDisplayList	instance()
	{
		if ( instance == null ) 
			instance = new StaticDisplayList();
		return instance;
	}

	private static StaticDisplayList instance = null;
	
	private StaticDisplayList()
	{
		staticID = 0;
	}
	
	public boolean createDisplaylist( GL2 gl )
	{
		GameViewData data	= GameViewData.instance();
		staticID  = gl.glGenLists( 1 );
		if ( staticID == 0 ) return false;
    	gl.glNewList(staticID, GL2.GL_COMPILE);
    	gl.glLineWidth( 3.0f );
    	System.out.println( "Static Scene Object count:" + data.getStaticScene().size() );
    	for( GraphicalObject go: data.getStaticScene() )
    	{
    		go.initialise( gl );
    	}
    	gl.glEndList();
    	return true;
	}
	
	public int destroy( GL2 gl )
	{
		if ( staticID == 0 ) return staticID;
		gl.glDeleteLists( staticID, 1 );
		instance = null;
		return staticID;
	}
	
	public void drawDisplayList( GL2 gl )
	{
		if ( staticID == 0 ) return;
		gl.glCallList(staticID );
	}

}
