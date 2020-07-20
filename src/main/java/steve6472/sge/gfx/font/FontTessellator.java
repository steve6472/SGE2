package steve6472.sge.gfx.font;

import steve6472.sge.gfx.AbstractTessellator;

import java.nio.FloatBuffer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 03.05.2019
 * Project: SJP
 *
 ***********************/
public class FontTessellator extends AbstractTessellator
{
	private FloatBuffer pos;
	private FloatBuffer col;
	private FloatBuffer data;

	private float x, y;
	private float r, g, b, a;
	private float dx, dy;

	public FontTessellator(int maxLength)
	{
		pos = createBuffer(maxLength * 2);
		col = createBuffer(maxLength * 4);
		data = createBuffer(maxLength * 2);
	}

	@Override
	public void begin(int vertexCount)
	{
//		pos = createBuffer(vertexCount * 2);
//		col = createBuffer(vertexCount * 4);
//		data = createBuffer(vertexCount * 2);
		pos.clear();
		pos.limit(vertexCount * 2);
		pos.position(0);
		col.clear();
		col.limit(vertexCount * 4);
		col.position(0);
		data.clear();
		data.limit(vertexCount * 2);
		data.position(0);
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
		pos.put(x).put(y);
		col.put(r).put(g).put(b).put(a);
		data.put(dx).put(dy);
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
