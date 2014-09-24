package ui.components;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;

public class GameListener implements KeyListener
{
	public float			direction = 0f;
	private boolean			keyUpdate = false;
	private boolean			w = false, a = false, s = false, d = false;
	private final float		speed = 0.5f;
	private final float		turnSpeed = 0.05f;
	private Point2D.Float	position;
	private GameCollision	map;
	
	public void setKeyUpdate( boolean keyUpdate )
	{
		this.keyUpdate = keyUpdate;
	}

	public boolean isKeyUpdate()
	{
		return keyUpdate;
	}

	// REQUIRES: x < map.xlimit and y < map.ylimit
	public void setLocation( float x, float y )
	{
		this.position.setLocation( x, y );
	}

	public GameListener( ArrayList<GraphicalObject> toDraw,
			Point2D.Float position, float direction, GameCollision map)
	{
		this.position = new Point2D.Float( position.x, position.y );
		this.direction = direction;
		this.map = map;
	}
	
	public boolean isKey( char ch )
	{
		switch( ch )
		{
		case 'w' : return w;
		case 'a' : return a;
		case 's' : return s;
		case 'd' : return d;
		}
		return false;
	}
	
	public float getNewX()
	{
		return position.x;
	}
	public float getNewY()
	{
		return position.y;
	}
	public float getDirection()
	{
		return direction;
	}

	public void setDirection( float direction )
	{
		if ( direction > GameFrame.PI2 ) return;
		this.direction = direction;
	}

	@Override
	public void keyTyped( KeyEvent arg0 ){}
	
	@Override
	public void keyReleased( KeyEvent key )
	{
		if ( key.getKeyCode() == KeyEvent.VK_W )
		{
			w = false;
		}
		else if ( key.getKeyCode() == KeyEvent.VK_S )
		{
			s = false;
		}
		else if ( key.getKeyCode() == KeyEvent.VK_A )
		{
			a = false;
		}
		else if ( key.getKeyCode() == KeyEvent.VK_D )
		{
			d = false;
		}
	}
	
	@Override
	public void keyPressed( KeyEvent key )
	{
		if ( key.getKeyCode() == KeyEvent.VK_W || (!keyUpdate && w) )
		{
			movePos( true );
		}
		if ( key.getKeyCode() == KeyEvent.VK_S || (!keyUpdate && s) )
		{
			movePos( false );
		}
		if ( key.getKeyCode() == KeyEvent.VK_A || (!keyUpdate && a) )
		{
			direction -= turnSpeed;
			direction %= GameFrame.PI2;
			keyUpdate = true;
			a = true;
		}
		if ( key.getKeyCode() == KeyEvent.VK_D || (!keyUpdate && d) )
		{
			direction += turnSpeed;
			direction %= GameFrame.PI2;
			keyUpdate = true;
			d = true;
		}
	}

	public void update()
	{
		if ( !keyUpdate )
    	{
	    	if ( w )
			{
	    		float newx = (float) ( position.x
						- Math.sin( direction ) * speed );
				float newy = (float) ( position.y
						- Math.cos( direction ) * speed );
				// collision detection
				if ( map.isCollidable( newx, newy ) )
				{
					if ( !map.isCollidable( position.x, newy ) )
						position.setLocation( position.x, newy );
					if ( !map.isCollidable( newx, position.y ) )
						position.setLocation( newx, position.y );
				}
				else position.setLocation( newx, newy );
			}
			if ( s )
			{
				float newx = (float) ( position.x
						+ Math.sin( direction ) * speed );
				float newy = (float) ( position.y
						+ Math.cos( direction ) * speed );
				// collision detection
				if ( map.isCollidable( newx, newy ) )
				{
					if ( !map.isCollidable( position.x, newy ) )
						position.setLocation( position.x, newy );
					if ( !map.isCollidable( newx, position.y ) )
						position.setLocation( newx, position.y );
				}
				else position.setLocation( newx, newy );
			}
			
			if ( a )
			{
				addToDirection( -turnSpeed );
			}
			if ( d )
			{
				addToDirection( turnSpeed );
			}
		}
	}
	
	private void movePos(boolean b)
	{
		int invert = b ? -1 : 1;
		float newx = (float) ( position.x
				+ invert * Math.sin( direction ) * speed );
		float newy = (float) ( position.y
				+ invert * Math.cos( direction ) * speed );
		// collision detection
		if ( map.isCollidable( newx, newy ) )
		{
			if ( !map.isCollidable( position.x, newy ) )
				position.setLocation( position.x, newy );
			if ( !map.isCollidable( newx, position.y ) )
				position.setLocation( newx, position.y );
		}
		else position.setLocation( newx, newy );
		keyUpdate = true;
		if ( b ) w = true;
		else s = true;
	}

	public void addToDirection( float f )
	{
		direction += f;
		direction %= GameFrame.PI2;
	}
}