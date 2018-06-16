package com.steve6472.sge.test;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

public class DynamicModel3D
{
	private FloatBuffer vert;
	private FloatBuffer tex;
	private FloatBuffer color;

	private List<Float> vertC;
	private List<Float> texC;
	private List<Float> colorC;
	
	boolean generated;

	public DynamicModel3D()
	{
		vertC = new ArrayList<Float>();
		texC = new ArrayList<Float>();
		colorC = new ArrayList<Float>();
	}

	public DynamicModel3D(FloatBuffer vert, FloatBuffer tex, FloatBuffer color)
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

		glTexCoordPointer(2, GL_FLOAT, 0, tex);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, tex);

		glColorPointer(4, GL_FLOAT, 0, color);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, color);

		glDrawArrays(mode, 0, vert.capacity() / 3);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}

	public void generate()
	{
		vert = toFloatBuffer(vertC);

		tex = toFloatBuffer(texC);

		color = toFloatBuffer(colorC);
		
		generated = true;
	}
	
	private FloatBuffer toFloatBuffer(List<Float> list)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(list.size());
		for (float f: list)
		{
			buff.put(f);
		}
		buff.flip();
		return buff;
	}

	public void add(float vx, float vy, float vz, float tx, float ty, float cr, float cg, float cb, float ca)
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

	public void add(float vx, float vy, float vz, float tx, float ty)
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
	
	public FloatBuffer getTex()
	{
		return tex;
	}
	
	public FloatBuffer getVert()
	{
		return vert;
	}
}
