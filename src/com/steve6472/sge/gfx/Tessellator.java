/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 1. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

import com.steve6472.sge.main.SGArray;
import com.steve6472.sge.test.Camera;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Tessellator
{
	
	public SGArray<Float> vertices = new SGArray<Float>(0, false, false);
	public SGArray<Integer> texture = new SGArray<Integer>(0, false, false);
	public SGArray<Float> color = new SGArray<Float>(0, false, false);

	public static final int POINTS 				= GL_POINTS;
	public static final int LINES 				= GL_LINES;
	public static final int LINE_LOOP 			= GL_LINE_LOOP;
	public static final int LINE_STRIP 			= GL_LINE_STRIP;
	public static final int TRIANGLES 			= GL_TRIANGLES;
	public static final int TRIANGLE_STRIP 		= GL_TRIANGLE_STRIP;
	public static final int TRIANGLE_FAN 		= GL_TRIANGLE_FAN;
	public static final int QUADS 				= GL_QUADS;
	public static final int QUAD_STRIP 			= GL_QUAD_STRIP;
	public static final int POLYGON 			= GL_POLYGON;
	
	public static final Tessellator INSTANCE = new Tessellator();

	public Tessellator()
	{
	}
	
	public static Tessellator getInstance()
	{
		return INSTANCE;
	}

	public void put(float vx, float vy, int tx, int ty, float cr, float cg, float cb, float ca)
	{
		vertices.addObject(vx);
		vertices.addObject(vy);
		
		texture.addObject(tx);
		texture.addObject(ty);
		
		color.addObject(cr);
		color.addObject(cg);
		color.addObject(cb);
		color.addObject(ca);
	}
	
	public void put(float vx, float vy, int tx, int ty)
	{
		put(vx, vy, tx, ty, 1, 1, 1, 1);
	}
	
	public void putPixelPerfect(float vx, float vy, int tx, int ty, float cr, float cg, float cb, float ca, Camera camera)
	{
		float w = 1f / (float) camera.getWidth();
		float h = 1f / (float) camera.getHeight();
		
		put(w * vx * 2f - 1, h * -vy * 2f + 1, tx, ty, cr, cg, cb, ca);
	}
	
	public void render(int mode)
	{
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		FloatBuffer ver = toFloatBuffer(vertices);
		IntBuffer tex = toIntBuffer(texture);
		FloatBuffer col = toFloatBuffer(color);
		
		glVertexPointer(2, GL_FLOAT, 0, ver);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, ver);

		glTexCoordPointer(2, GL_INT, 0, tex);
		glVertexAttribPointer(1, 2, GL_INT, false, 0, tex);

		glColorPointer(4, GL_FLOAT, 0, col);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, col);

		glDrawArrays(mode, 0, vertices.getSize() / 2);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		
		vertices.clear();
		color.clear();
		texture.clear();
	}
	
	private FloatBuffer toFloatBuffer(SGArray<Float> arr)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(arr.getSize());
		
		for (float i : arr)
		{
			buff.put(i);
		}
		buff.flip();
		
		return buff;
	}
	
	private IntBuffer toIntBuffer(SGArray<Integer> arr)
	{
		IntBuffer buff = BufferUtils.createIntBuffer(arr.getSize());
		
		for (int i : arr)
		{
			buff.put(i);
		}
		buff.flip();
		
		return buff;
	}
	
}
