package ui.components;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import com.jogamp.common.nio.Buffers;


public class Tree implements GraphicalObject
{
	private final int numBranches = 4;
	private final double branchAngle = Math.PI*0.3333333333f;
	private double shrinkage = 0.7d;
	private Color col;
	private FloatBuffer vertices;
	private FloatBuffer colours;
	private IntBuffer indices;
	
	public Tree(float x, float y)
	{
		col = new Color( (float)Math.random()/1.0f
						, (float)Math.random()/1.0f
						, (float)Math.random()/1.0f );
		long length = leftFactorial( numBranches );
		System.out.println( (int) (length * 6) );
		vertices = Buffers.newDirectFloatBuffer( (int) (length * 3) );
		colours = Buffers.newDirectFloatBuffer( (int) (length * 3) );
		indices = Buffers.newDirectIntBuffer( (int) ((length - 1) << 1) );
		vertices.put( x );
		vertices.put( y );
		vertices.put( 0.0f ); // base vertex
		colours.put( col.getRed() );
		colours.put( col.getGreen() );
		colours.put( col.getBlue() ); // base colour
		buildTree(x, y, 0.0f, length,
				1, 1, 1, 0);
		
	}
	
	private int factorial(int n)
	{
	  return (n < 2) ? 1 : factorial(n - 1) * n;
	}
	
	private long leftFactorial(int factor){
		return (factor < 1) ? 1 : leftFactorial(factor - 1) + factorial(factor);
	}
	public int buildTree( float x, float y, float z, double dist,
					int subBranches, int index, int arrayIndex,
					int previousArrIndex)
	{
		double radians = (2*Math.PI)*((double)index/(double)subBranches); // equal spaced branching horizontally
		double spacing = (double)((1+numBranches)-subBranches)
								*(branchAngle-((double)subBranches*0.1d)); // changing vertical branching
		vertices.put( (float) (z==0?x:x+Math.sin(radians)*spacing) );
		vertices.put( (float) (z==0?y:y+Math.cos(radians)*spacing) );
		vertices.put( (float) (z+dist) );
		// add colour to the vertex array
		colours.put( col.getRed()*(1.0f/(float)subBranches) );
		colours.put( col.getGreen()*(0.5f/(float)subBranches) );
		colours.put( col.getBlue()*(1.0f/(float)subBranches) );
		// lines are one fewer than points
		indices.put( previousArrIndex );
		indices.put( arrayIndex );
		int actualIndex = arrayIndex*3;
		if (subBranches < numBranches) {
			int oldIndex = arrayIndex;
			for (int i=1;i<(subBranches+2);++i) {
				arrayIndex = buildTree(
						vertices.get( actualIndex )
						, vertices.get( actualIndex+1 )
						, vertices.get( actualIndex+2 )
						, (Math.abs(Math.sin(vertices.get( actualIndex ))
								+ Math.cos(vertices.get( actualIndex + 1 ))))
								+ (dist * shrinkage)
						, subBranches+1, i
						, arrayIndex+1, oldIndex);
			}
		}
		return arrayIndex;
	}
	@Override
	public boolean draw( GL2 gl )
	{
		vertices.rewind();
		colours.rewind();
		gl.glColorPointer( 3, GL.GL_FLOAT, 0, colours );
		gl.glVertexPointer( 3, GL.GL_FLOAT, 0, vertices );
		gl.glEnableClientState( GL2.GL_COLOR_ARRAY );
		gl.glEnableClientState( GL2.GL_VERTEX_ARRAY );
		indices.rewind();
		gl.glDrawElements( GL.GL_LINES, indices.capacity(), GL.GL_UNSIGNED_INT, indices );
		return true;
	}

	@Override
	public boolean makeDisplayList( GL2 gl )
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clean( GL2 gl )
	{
		// TODO Auto-generated method stub
		
	}
}
