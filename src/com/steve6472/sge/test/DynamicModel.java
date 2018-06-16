package com.steve6472.sge.test;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

public class DynamicModel
{
	private FloatBuffer vert;
	private IntBuffer tex;
	private FloatBuffer color;

	private List<Float> vertC;
	private List<Integer> texC;
	private List<Float> colorC;
	
	boolean generated;

	public DynamicModel()
	{
		vertC = new ArrayList<Float>();
		texC = new ArrayList<Integer>();
		colorC = new ArrayList<Float>();
	}

	public DynamicModel(FloatBuffer vert, IntBuffer tex, FloatBuffer color)
	{
		this.vert = vert;
		this.tex = tex;
		this.color = color;
		
		generated = true;
	}
	
	public void render(int mode)
	{
		if (!generated)
			return;
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		glVertexPointer(3, GL_FLOAT, 0, vert);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, vert);

		glTexCoordPointer(2, GL_INT, 0, tex);
		glVertexAttribPointer(1, 2, GL_INT, false, 0, tex);

		glColorPointer(4, GL_FLOAT, 0, color);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, color);

		glDrawArrays(mode, 0, vert.capacity() / 3);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}

	public void generate()
	{
		FloatBuffer vertBuffer = BufferUtils.createFloatBuffer(vertC.size());
		for (float f : vertC)
		{
			vertBuffer.put(f);
		}
		vertBuffer.flip();
		vert = vertBuffer;

		IntBuffer textBuffer = BufferUtils.createIntBuffer(texC.size());
		for (int i : texC)
		{
			textBuffer.put(i);
		}
		textBuffer.flip();
		tex = textBuffer;

		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colorC.size());
		for (float f : colorC)
		{
			colorBuffer.put(f);
		}
		colorBuffer.flip();
		color = colorBuffer;
		
		generated = true;
	}

	public void add(float vx, float vy, float vz, int tx, int ty, float cr, float cg, float cb, float ca)
	{
		vertC.add(vx);
		vertC.add(vy);
		vertC.add(vz);

		texC.add(tx);
		texC.add(ty);

		colorC.add(cr);
		colorC.add(cg);
		colorC.add(cb);
		colorC.add(ca);
	}

	public void add(float vx, float vy, float vz, int tx, int ty)
	{
		add(vx, vy, vz, tx, ty, 1, 1, 1, 1);
	}

	public void add(float vx, float vy, float vz, float cr, float cg, float cb, float ca)
	{
		add(vx, vy, vz, 0, 0, cr, cg, cb, ca);
	}

	public void add(float vx, float vy, float vz, float[] c)
	{
		add(vx, vy, vz, 0, 0, c[0], c[1], c[2], c[3]);
	}
	
	public FloatBuffer getColor()
	{
		return color;
	}
	
	public IntBuffer getTex()
	{
		return tex;
	}
	
	public FloatBuffer getVert()
	{
		return vert;
	}
}
