package steve6472.sge.gfx.game.stack.buffer;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL20.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public abstract class AbstractBuffer
{
	protected final FloatBuffer buffer;
	protected int index;

	public AbstractBuffer(int size, int index)
	{
		this.buffer = BufferUtils.createFloatBuffer(size * vertexSize());
		this.index = index;
	}

	public abstract void endVertex();

	public void clear()
	{
		buffer.clear();
	}

	public abstract int vertexSize();

	public final void loadBuffer()
	{
		buffer.flip();
		glEnableVertexAttribArray(index);
		glVertexPointer(vertexSize(), GL_FLOAT, 0, buffer);
		glVertexAttribPointer(index, vertexSize(), GL_FLOAT, false, 0, buffer);
	}

	public final void unloadBuffer()
	{
		glDisableVertexAttribArray(index);
	}

	public int getIndex()
	{
		return index;
	}

	public FloatBuffer getBuffer()
	{
		return buffer;
	}

	@Override
	public String toString()
	{
		return "AbsBuffer{" + "buffer=" + buffer + ", index=" + index + '}';
	}
}
