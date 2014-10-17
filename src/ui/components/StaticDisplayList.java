package ui.components;

import javax.media.opengl.GL2;

/**
 * @author Vivian Stewart
 * Maintains the OpenGL display list of graphical objects that do not change.
 */
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

	/**
	 * @param gl - OpenGL context
	 * @return true if successful
	 */
	public boolean createDisplaylist( GL2 gl )
	{
		GameViewData data	= GameViewData.instance();
		if ( staticID != 0 )
		{
			destroy( gl );
		}
		staticID  = gl.glGenLists( 1 );
		if ( staticID == 0 ) return false;
    	gl.glNewList(staticID, GL2.GL_COMPILE);
    	gl.glLineWidth( 3.0f );
    	for( GraphicalObject go: data.getStaticScene() )
    	{
    		go.initialise( gl );
    	}
    	gl.glEndList();
    	return true;
	}

	/**
	 * @param gl - OpenGL context
	 * @return staticID should be 0
	 */
	public int destroy( GL2 gl )
	{
		if ( staticID == 0 ) return staticID;
		gl.glDeleteLists( staticID, 1 );
		staticID = 0;
		return staticID;
	}

	public void drawDisplayList( GL2 gl )
	{
		if ( staticID == 0 ) return;
		gl.glCallList(staticID );
	}

}
