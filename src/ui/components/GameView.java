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
import javax.swing.JLayeredPane;

import world.game.GameState;

import com.jogamp.opengl.util.FPSAnimator;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * A simple line drawn 3D adventure board game program that draws with JOGL in
 * a Swing JFrame using the AWT GLJPanel.
 * @author Vivian Stewart
 */
@SuppressWarnings( "serial" )
public class GameView extends GLJPanel
{
	public static final int		cellsize = 10;
	public static final double
					PI2 = Math.PI * 2;
	public static final float
					DEG = 180.0f / (float) Math.PI;
	public static GameView instance = null;
	// actual direction before update
	private float 				direction = 0.0f;
	// actual position before update
	private Point2D.Float 		position;
	private GameViewData		data	= GameViewData.instance();
	private GameScene			map;
	// map boundaries in the positive x y directions
	private Point				extents;
	// keyInput (keyboard) is also responsible for position and direction changes
	private GameListener		keyInput;
	private int					staticID = 0;
	private GameState			state;

    public GameView( GLCapabilities gc, JFrame frame, GameState state )
    {
    	super( gc );

    	map = new GameScene(state);

    	extents = map.mapsize();
    	this.state = state;
    	Point p = state.getPlayer().getPosition();
        position = new Point2D.Float(
				p.x * cellsize, p.y * cellsize );
        keyInput = new GameListener( position, direction, map );
        addGLEventListener( new GLEventListener() {
            /* (non-Javadoc)
             * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable, int, int, int, int)
             */
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
            /* (non-Javadoc)
             * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
             */
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            	glautodrawable.getGL().setSwapInterval(1);
            	GL2 gl2 = glautodrawable.getGL().getGL2();
            	gl2.glPointParameterf( GL2.GL_POINT_SIZE_MIN, 1.0f );
            	gl2.glPointParameterf( GL2.GL_POINT_SIZE_MAX, 100.0f );
            	FloatBuffer fb = FloatBuffer.wrap( new float[]{0f,0f,0.01f} );
            	gl2.glPointParameterfv( GL2.GL_POINT_DISTANCE_ATTENUATION, fb );
            	gl2.glEnable( GL2.GL_POINT_SMOOTH );
            	gl2.glEnable( GL.GL_LINE_SMOOTH );
            	gl2.glEnable( GL2.GL_POLYGON_SMOOTH );
            	gl2.glEnable( GL2.GL_POLYGON_OFFSET_LINE );
            	gl2.glPolygonOffset( 0.1f, 100f );
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
            	map.addSurrounds();
            	gl2.glLineWidth( 2f );
            	System.out.println( "Dynamic Scene Object count:" + data.getDynamicScene().size() );
            	for( GraphicalObject go: data.getDynamicScene() )
            	{
            		go.initialise( gl2 );
            	}
            	staticID  = gl2.glGenLists( 1 );
            	gl2.glNewList(staticID, GL2.GL_COMPILE);
            	gl2.glLineWidth( 3.0f );
            	System.out.println( "Static Scene Object count:" + data.getStaticScene().size() );
            	for( GraphicalObject go: data.getStaticScene() )
            	{
            		go.initialise( gl2 );
            	}
            	gl2.glEndList();
            }

            /* (non-Javadoc)
             * @see javax.media.opengl.GLEventListener#dispose(javax.media.opengl.GLAutoDrawable)
             */
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            	GL2 gl = glautodrawable.getGL().getGL2();
            	gl.glDeleteLists( staticID, 1 );
            }

            /* (non-Javadoc)
             * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
             */
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
        addKeyListener( keyInput );

        frame.addWindowListener( new WindowAdapter()
        {
            public void windowClosing( WindowEvent windowevent )
            {
                dispose();
                instance = null;
                System.exit( 0 );
            }
        });
        // steady 60 frame animation
        FPSAnimator animator = new FPSAnimator(60);
        animator.add( this );
        animator.start();

        setSize( 800, 600 );
        setVisible( true );
        instance = this;
    }

    private void update()
	{
    	// keep moving and turning even if there are no key press or release events
    	Point click = keyInput.update();
    	if ( click != null )
    	{
    		// mouse selection
    		// TODO: gl.glUnProject(...
    	}
    	direction = keyInput.getDirection() * DEG;
    	// new position
    	float newx = keyInput.getNewX(), newy = keyInput.getNewY();
    	// new cell to occupy?
    	int cellx = (int) ( newx / cellsize ), celly = (int) ( newy / cellsize );
    	if ( 	   (int) ( position.x / cellsize ) != cellx
    			|| (int) ( position.y / cellsize ) != celly )
    		// tell the game server
    		state.movePlayer( state.getPlayer(), new Point( cellx, celly ) );
    	// do update
    	position.setLocation( newx, newy );
    	// update key input every frame unless input is received
    	keyInput.setKeyUpdate( false );
    }

	/**
	 * All game objects to render themselves
	 * @param gl2 - opengl rendering context
	 */
	private void render( GL2 gl2 )
	{
		gl2.glLineWidth( 2f );
		for( GraphicalObject go: data.getDynamicScene() )
        	go.draw( gl2 );
		if ( staticID == 0 ) return;
		gl2.glCallList(staticID );
    }

	// Kalo commented out for rough integration
	/*
	public static void main( String [] args ) {
		JFrame jf = new JFrame();
		GameView gv = new GameView( new GLCapabilities( GLProfile.getDefault() ), jf );
		jf.setSize( 800, 600 );
		jf.add( gv );
		jf.setVisible( true );
	}
	*/
}
