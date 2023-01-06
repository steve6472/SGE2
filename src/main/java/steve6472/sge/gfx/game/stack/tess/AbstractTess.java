package steve6472.sge.gfx.game.stack.tess;

import steve6472.sge.gfx.VertexObjectCreator;
import steve6472.sge.gfx.game.stack.SmallModel;
import steve6472.sge.gfx.game.stack.buffer.AbstractBuffer;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.glDrawArrays;

/**********************
 * Created by steve6472
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public abstract class AbstractTess
{
	private final AbstractBuffer[] buffers;
	private int size;

	public AbstractTess(int maxSize)
	{
		createBuffers(maxSize);
		this.buffers = buffers();
	}

	protected abstract void createBuffers(int maxSize);

	protected abstract AbstractBuffer[] buffers();

	/**
	 * Loads all buffers, draws them, and unloads them
	 * @param mode drawMode
	 */
	public void draw(int mode)
	{
		for (AbstractBuffer buffer : buffers)
		{
			buffer.loadBuffer();
		}

		glDrawArrays(mode, 0, size);

		for (AbstractBuffer buffer : buffers)
		{
			buffer.unloadBuffer();
		}
	}

	/**
	 * Creates new model on GPU with current values
	 * @return Object containing vao, vbos and vertex count at creation
	 */
	public SmallModel createVAO()
	{
		int vao = VertexObjectCreator.createVAO();
		int[] vbos = new int[buffers.length];
		for (int i = 0; i < buffers.length; i++)
		{
			AbstractBuffer buffer = buffers[i];
			vbos[i] = VertexObjectCreator.storeFloatDataInAttributeList(buffer.getIndex(), buffer.vertexSize(), buffer.getBuffer());
		}
		return new SmallModel(vao, vbos, size);
	}

	public void endVertex()
	{
		size++;
		for (AbstractBuffer buffer : buffers)
		{
			buffer.endVertex();
		}
	}

	/**
	 * Clears all buffers
	 */
	public void begin()
	{
		size = 0;
		for (AbstractBuffer buffer : buffers)
		{
			buffer.clear();
		}
	}

	public AbstractBuffer[] getBuffers()
	{
		return buffers;
	}

	@Override
	public String toString()
	{
		return "AbsTess{" + "buffers=" + Arrays.toString(buffers) + ", size=" + size + '}';
	}
}
