/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 1. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

public class Tessellator3D
{
	
	public List<Float> vertices = new ArrayList<Float>();
	public List<Integer> texture = new ArrayList<Integer>();
	public List<Float> color = new ArrayList<Float>();

	public static final int POINTS 				= GL_POINTS; 				// 0
	public static final int LINES 				= GL_LINES;					// 1
	public static final int LINE_LOOP 			= GL_LINE_LOOP;				// 2
	public static final int LINE_STRIP 			= GL_LINE_STRIP;			// 3
	public static final int TRIANGLES 			= GL_TRIANGLES;				// 4
	public static final int TRIANGLE_STRIP 		= GL_TRIANGLE_STRIP;		// 5
	public static final int TRIANGLE_FAN 		= GL_TRIANGLE_FAN;			// 6
	public static final int QUADS 				= GL_QUADS;					// 7
	public static final int QUAD_STRIP 			= GL_QUAD_STRIP;			// 8
	public static final int POLYGON 			= GL_POLYGON;				// 9
	
	public static final Tessellator3D INSTANCE = new Tessellator3D();

	public Tessellator3D()
	{
	}
	
	public static Tessellator3D getInstance()
	{
		return INSTANCE;
	}
	
	public void put(float[] ver, int[] tex, float[] col)
	{
		for (int i = 0; i < ver.length / 3; i++)
		{
			if (tex != null && col != null)
			{
				put(
						ver[0 + i * 3], ver[1 + i * 3], ver[2 + i * 3], 
						tex[0 + i * 2], tex[1 + i * 2], 
						col[0 + i * 4], col[1 + i * 4], col[2 + i * 4], col[3 + i * 4]
								);
			} else if (tex == null && col != null)
			{
				put(
						ver[0 + i * 3], ver[1 + i * 3], ver[2 + i * 3],  
						col[0 + i * 4], col[1 + i * 4], col[2 + i * 4], col[3 + i * 4]
								);
			} else if (col == null && tex != null)
			{
				put(
						ver[0 + i * 3], ver[1 + i * 3], ver[2 + i * 3], 
						tex[0 + i * 2], tex[1 + i * 2]
								);
			} else
			{
				put(
						ver[0 + i * 3], ver[1 + i * 3], ver[2 + i * 3]
								);
			}
			
		}
	}

	public void put(float vx, float vy, float vz, int tx, int ty, float cr, float cg, float cb, float ca)
	{
		vertices.add(vx);
		vertices.add(vy);
		vertices.add(vz);
		
		texture.add(tx);
		texture.add(ty);
		
		color.add(cr);
		color.add(cg);
		color.add(cb);
		color.add(ca);
	}
	
	public void put(float vx, float vy, float vz, int tx, int ty)
	{
		put(vx, vy, vz, tx, ty, 1, 1, 1, 1);
	}
	
	public void put(float vx, float vy, float vz)
	{
		put(vx, vy, vz, 0, 0, 1, 1, 1, 1);
	}
	
	public void put(float vx, float vy, float vz, float r, float g, float b, float a)
	{
		put(vx, vy, vz, 0, 0, r, g, b, a);
	}
	
	public void put(float vx, float vy, float vz, float r, float g, float b)
	{
		put(vx, vy, vz, 0, 0, r, g, b, 1);
	}
	
	public void render(int mode)
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		FloatBuffer ver = toFloatBuffer(vertices);
		IntBuffer tex = toIntBuffer(texture);
		FloatBuffer col = toFloatBuffer(color);
		
		glVertexPointer(3, GL_FLOAT, 0, ver);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, ver);

		glTexCoordPointer(2, GL_INT, 0, tex);
		glVertexAttribPointer(1, 2, GL_INT, false, 0, tex);

		glColorPointer(4, GL_FLOAT, 0, col);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, col);

		glDrawArrays(mode, 0, vertices.size() / 3);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		
		vertices.clear();
		color.clear();
		texture.clear();
	}
	
	public void render(int mode, FloatBuffer ver, IntBuffer tex, FloatBuffer col, int size)
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		glVertexPointer(3, GL_FLOAT, 0, ver);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, ver);

		glTexCoordPointer(2, GL_INT, 0, tex);
		glVertexAttribPointer(1, 2, GL_INT, false, 0, tex);

		glColorPointer(4, GL_FLOAT, 0, col);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, col);

		glDrawArrays(mode, 0, size);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	public static FloatBuffer toFloatBuffer(List<Float> arr)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(arr.size());
		
		for (float i : arr)
		{
			buff.put(i);
		}
		buff.flip();
		
		return buff;
	}
	
	public static IntBuffer toIntBuffer(List<Integer> arr)
	{
		IntBuffer buff = BufferUtils.createIntBuffer(arr.size());
		
		for (int i : arr)
		{
			buff.put(i);
		}
		buff.flip();
		
		return buff;
	}
	
	public static int getRenderMode(int index)
	{
		switch (index)
		{
			default: return LINES;
			case 0: return POINTS;
			case 1: return LINES;
			case 2: return LINE_LOOP;
			case 3: return LINE_STRIP;
			case 4: return TRIANGLES;
			case 5: return TRIANGLE_STRIP;
			case 6: return TRIANGLE_FAN;
			case 7: return QUADS;
			case 8: return QUAD_STRIP;
			case 9: return POLYGON;
		}
	}
	
	public static String getRenderModeName(int index)
	{
		switch (index)
		{
			default: return "NULL";
			case 0: return "POINTS";
			case 1: return "LINES";
			case 2: return "LINE_LOOP";
			case 3: return "LINE_STRIP";
			case 4: return "TRIANGLES";
			case 5: return "TRIANGLE_STRIP";
			case 6: return "TRIANGLE_FAN";
			case 7: return "QUADS";
			case 8: return "QUAD_STRIP";
			case 9: return "POLYGON";
		}
	}
	
}
