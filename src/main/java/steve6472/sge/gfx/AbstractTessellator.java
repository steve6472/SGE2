package steve6472.sge.gfx;

import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**********************
 * Created by steve6472
 * On date: 25.11.2018
 * Project: Test3D
 *
 ***********************/
public abstract class AbstractTessellator
{
	int size;

	public void begin(int vertexCount)
	{
		size = 0;
	}
	
	protected void setBuffer(Buffer buffer, int limit)
	{
		buffer.clear();
		buffer.limit(limit);
		buffer.position(0);
	}

	protected FloatBuffer createBuffer(int size)
	{
		return BufferUtils.createFloatBuffer(size);
	}

	protected AbstractTessellator put(FloatBuffer buffer, List<Float> floats)
	{
		for (float f : floats)
		{
			buffer.put(f);
		}
		return this;
	}

	protected void loadBuffer(FloatBuffer buffer, int index, int size)
	{
		buffer.flip();
		enable(index);
		glVertexPointer(size, GL_FLOAT, 0, buffer);
		glVertexAttribPointer(index, size, GL_FLOAT, false, 0, buffer);
	}

	public void enable(int index)
	{
		glEnableVertexAttribArray(index);
	}

	public void disable(int... indices)
	{
		for (int index : indices)
		{
			glDisableVertexAttribArray(index);
		}
	}

	public void endVertex()
	{
		size++;
	}

	public void draw(int mode)
	{
		glDrawArrays(mode, 0, size);
	}
}
