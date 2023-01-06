/**********************
* Created by steve6472
* On date: 1. 6. 2018
* Project: SGE2
*
***********************/

package steve6472.sge.gfx;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Tessellator
{
	public List<Float> vertices = new ArrayList<>();
	public List<Float> texture = new ArrayList<>();
	public List<Float> color = new ArrayList<>();

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
	
	public static final Tessellator INSTANCE = new Tessellator();

	public Tessellator()
	{
	}
	
	public static Tessellator getInstance()
	{
		return INSTANCE;
	}

	public void put(float vx, float vy, float tx, float ty, float cr, float cg, float cb, float ca)
	{
		vertices.add(vx);
		vertices.add(vy);
		
		texture.add(tx);
		texture.add(ty);
		
		color.add(cr);
		color.add(cg);
		color.add(cb);
		color.add(ca);
	}
	
	public void put(float vx, float vy, float tx, float ty)
	{
		put(vx, vy, tx, ty, 1, 1, 1, 1);
	}
	
	public void putPixelPerfect(float vx, float vy, float tx, float ty, float cr, float cg, float cb, float ca, float screenWidth, float screenHeight)
	{
		float w = 1f / screenWidth;
		float h = 1f / screenHeight;

		put(w * vx * 2f - 1, h * -vy * 2f + 1, tx, ty, cr, cg, cb, ca);
	}
	
	public void render(int mode)
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		FloatBuffer ver = toFloatBuffer(vertices);
		FloatBuffer tex = toFloatBuffer(texture);
		FloatBuffer col = toFloatBuffer(color);
		
		glVertexPointer(2, GL_FLOAT, 0, ver);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, ver);

		glTexCoordPointer(2, GL_FLOAT, 0, tex);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, tex);

		glColorPointer(4, GL_FLOAT, 0, col);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, col);

		glDrawArrays(mode, 0, vertices.size() / 2);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		
		vertices.clear();
		color.clear();
		texture.clear();
	}
	
	private FloatBuffer toFloatBuffer(List<Float> arr)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(arr.size());
		
		for (float i : arr)
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
