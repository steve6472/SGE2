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

public class DynamicModel
{
	private FloatBuffer vert;
	private FloatBuffer tex;
	private FloatBuffer color;

	private List<Float> vertC;
	private List<Float> texC;
	private List<Float> colorC;
	
	boolean generated;

	public DynamicModel()
	{
		vertC = new ArrayList<Float>();
		texC = new ArrayList<Float>();
		colorC = new ArrayList<Float>();
	}

	public DynamicModel(FloatBuffer vert, FloatBuffer tex, FloatBuffer color)
	{
		this.vert = vert;
		this.tex = tex;
		this.color = color;
		
		generated = true;
	}
	
	public static void start()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
	}
	
	public static void finish()
	{
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	public void render(int mode)
	{
		if (!generated)
			return;
		
		start();

		glVertexPointer(2, GL_FLOAT, 0, vert);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, vert);

		glTexCoordPointer(2, GL_FLOAT, 0, tex);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, tex);

		glColorPointer(4, GL_FLOAT, 0, color);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, color);

		glDrawArrays(mode, 0, vert.capacity() / 2);
		
		finish();
	}
	
	public void render2(int mode)
	{
		glVertexPointer(2, GL_FLOAT, 0, vert);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, vert);

		glTexCoordPointer(2, GL_FLOAT, 0, tex);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, tex);

		glColorPointer(4, GL_FLOAT, 0, color);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, color);

		glDrawArrays(mode, 0, vert.capacity() / 2);
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

	public void add(float vx, float vy, float tx, float ty, float cr, float cg, float cb, float ca)
	{
		vertC.add(vx);
		vertC.add(vy);

		texC.add(tx);
		texC.add(ty);

		colorC.add(cr);
		colorC.add(cg);
		colorC.add(cb);
		colorC.add(ca);
	}

	public void add(float vx, float vy, float tx, float ty)
	{
		add(vx, vy, tx, ty, 1, 1, 1, 1);
	}

	public void add(float vx, float vy, float cr, float cg, float cb, float ca)
	{
		add(vx, vy, 0, 0, cr, cg, cb, ca);
	}

	public void add(float vx, float vy, float[] c)
	{
		add(vx, vy, 0, 0, c[0], c[1], c[2], c[3]);
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
