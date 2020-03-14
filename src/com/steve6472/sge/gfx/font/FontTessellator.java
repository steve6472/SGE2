package com.steve6472.sge.gfx.font;

import com.steve6472.sge.gfx.TessellatorCreator;

import java.nio.FloatBuffer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 03.05.2019
 * Project: SJP
 *
 ***********************/
public class FontTessellator extends TessellatorCreator
{
	private FloatBuffer pos;
	private FloatBuffer col;
	private FloatBuffer data;

	private float x, y;
	private float r, g, b, a;
	private float dx, dy;

	@Override
	public void begin(int vertexCount)
	{
		pos = createBuffer(vertexCount * 2);
		col = createBuffer(vertexCount * 4);
		data = createBuffer(vertexCount * 2);
		super.begin(vertexCount);
	}

	public FontTessellator pos(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public FontTessellator col(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return this;
	}

	public FontTessellator data(float dx, float dy)
	{
		this.dx = dx;
		this.dy = dy;
		return this;
	}

	@Override
	public void endVertex()
	{
		put(pos, x, y);
		put(col, r, g, b, a);
		put(data, dx, dy);
		super.endVertex();
	}

	public void loadPos(int index)
	{
		loadBuffer(pos, index, 2);
	}

	public void loadCol(int index)
	{
		loadBuffer(col, index, 4);
	}

	public void loadData(int index)
	{
		loadBuffer(data, index, 2);
	}

	@Override
	public void draw(int mode)
	{
		super.draw(mode);

		pos.clear();
		col.clear();
		data.clear();
	}
}
