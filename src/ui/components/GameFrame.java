package ui.components;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * A minimal program that draws with JOGL in a Swing JFrame using the AWT GLJPanel.
 *
 * @author Wade Walker
 */
@SuppressWarnings( "serial" )
public class GameFrame extends JFrame 
{
	private ArrayList<GraphicalObject> toDraw = new ArrayList<GraphicalObject>();
	public static final int		cellsize = 10;
	public static final double
					PI2 = Math.PI * 2;
	public static final float
					DEG = 180.0f / (float) Math.PI;
	// keyInput.setDirection( is a more current alias to direction
	private float direction = 0.0f;
	// forward reverse constant
	public static final float	speed = 0.5f;
	private Point2D.Float position;
	private GameCollision map = new GameCollision();
	private Point extents = map.mapsize();
	private GameListener	keyInput;
	public static final float	turnSpeed = 0.05f;
	
    public GameFrame( String str )
    {
    	super(str);
    	GLProfile.initSingleton();
    	GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        GLJPanel gameView = new GLJPanel( glcapabilities );
        position = new Point2D.Float( 
				(extents.x/ 2.0f) * cellsize, (extents.y / 2.0f) * cellsize );
        keyInput = new GameListener( toDraw, position, direction, map );
        gameView.addGLEventListener( new GLEventListener() {
            
            @Override
            public void reshape( GLAutoDrawable glautodrawable
            		, int x, int y, int width, int height ) {
            	GL2 gl2 = glautodrawable.getGL().getGL2();
            	GLU glu = GLU.createGLU( gl2 );
            	gl2.glViewport( 0, 0, width, height );
            	gl2.glClearColor( 0.0f, 0.0f, 0.01f, 0.0f );
            	gl2.glMatrixMode( GL2.GL_PROJECTION );
            	gl2.glLoadIdentity();
            	glu.gluPerspective( 65.0f, (float)width/(float)height, 1.0f, 1000.0f );
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            	glautodrawable.getGL().setSwapInterval(1);
            	GL2 gl2 = glautodrawable.getGL().getGL2();
            	gl2.glLineWidth( 3.0f );
            	gl2.glEnable( GL.GL_LINE_SMOOTH );
            	gl2.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
            	gl2.glHint( GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST );
            	// Antialias lines
            	gl2.glEnable( GL.GL_BLEND );
            	gl2.glPointSize( 10.0f );
            	gl2.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA );
            	gl2.glClearColor( 0.0f, 0.0f, 0.01f, 0.0f );
            	gl2.glEnable( GL.GL_DEPTH_TEST );
            	gl2.glDepthFunc(GL.GL_LEQUAL);
                gl2.glShadeModel(GL2.GL_SMOOTH);
            	toDraw.add( new Plane( extents, 0, cellsize ) );
            	toDraw.add( new Plane( extents, 2, cellsize ) );
            	map.addSurrounds( toDraw, cellsize );
            	for( GraphicalObject go: toDraw )
                	go.initialise( gl2 );
            }
            
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            	GL2 gl = glautodrawable.getGL().getGL2();
            	for ( GraphicalObject g: toDraw )
            		g.clean( gl );
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
            	update();
            	GL2 gl2 = glautodrawable.getGL().getGL2();
            	GLU glu = GLU.createGLU( gl2 );
            	gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
                // Draw some rotating stuff
            	gl2.glMatrixMode( GL2.GL_MODELVIEW );
            	gl2.glLoadIdentity();

            	glu.gluLookAt( 0.0f, 0.0f, 0.0f   // position
            				 , 0.0f,-1.0f, 0.0f   // direction
            				 , 0.0f, 0.0f, 1.0f );// up
            	gl2.glRotatef( direction, 0 ,0 ,1.0f );
            	gl2.glTranslatef( -position.x, -position.y, -10.0f );
                render( gl2 );
            }
        });
        gameView.addKeyListener( keyInput );
        
        addWindowListener( new WindowAdapter()
        {
            public void windowClosing( WindowEvent windowevent )
            {
                dispose();
                System.exit( 0 );
            }
        });
        
        FPSAnimator animator = new FPSAnimator(60);
        animator.add( gameView );
        animator.start();

        getContentPane().add( gameView, BorderLayout.CENTER );
        setSize( 800, 600 );
        setVisible( true );
    }
    
    private void update()
	{
    	if ( !keyInput.isKeyUpdate() )
    	{
	    	if ( keyInput.isKey( 'w' ) )
			{
	    		float newx = (float) ( keyInput.getNewX()
						- Math.sin( keyInput.getDirection() ) * speed );
				float newy = (float) ( keyInput.getNewY()
						- Math.cos( keyInput.getDirection() ) * speed );
				// collision detection
				if ( map.isCollidable( newx, newy ) )
				{
					if ( !map.isCollidable( keyInput.getNewX(), newy ) )
						keyInput.setLocation( keyInput.getNewX(), newy );
					if ( !map.isCollidable( newx, keyInput.getNewY() ) )
						keyInput.setLocation( newx, keyInput.getNewY() );
				}
				else keyInput.setLocation( newx, newy );
			}
			if ( keyInput.isKey( 's' ) )
			{
				float newx = (float) ( keyInput.getNewX()
						+ Math.sin( keyInput.getDirection() ) * speed );
				float newy = (float) ( keyInput.getNewY()
						+ Math.cos( keyInput.getDirection() ) * speed );
				// collision detection
				if ( map.isCollidable( newx, newy ) )
				{
					if ( !map.isCollidable( keyInput.getNewX(), newy ) )
						keyInput.setLocation( keyInput.getNewX(), newy );
					if ( !map.isCollidable( newx, keyInput.getNewY() ) )
						keyInput.setLocation( newx, keyInput.getNewY() );
				}
				else keyInput.setLocation( newx, newy );
			}
			
			if ( keyInput.isKey( 's' ) )
			{
				keyInput.addToDirection( -turnSpeed );
			}
			if ( keyInput.isKey( 's' ) )
			{
				keyInput.addToDirection( turnSpeed );
			}
		}
    	direction = keyInput.getDirection() * DEG;
    	position.setLocation( keyInput.getNewX(), keyInput.getNewY() );
    	keyInput.setKeyUpdate( false );
    }

	private void render( GL2 gl2 )
	{
        for( GraphicalObject go: toDraw )
        	go.draw( gl2 );
    }

	public static void main( String [] args ) {
		new GameFrame( "Swing GLJPanel" );
	}
}