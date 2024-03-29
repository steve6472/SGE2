package steve6472.sge.gfx;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**********************
 * Created by steve6472
 * On date: 31.8.2018
 * Project: 3DTest
 *
 ***********************/
public class VertexObjectCreator
{
	private static List<Integer> vaos = new ArrayList<>();
	private static List<Integer> vbos = new ArrayList<>();

	/*
	 * Float
	 */

	public static int storeFloatDataInAttributeList(int attributeNumber, int size, List<Float> data)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		FloatBuffer buffer = toFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static void storeFloatDataInAttributeList(int attributeNumber, int size, int vboId, List<Float> data)
	{
		FloatBuffer buffer = toFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public static void storeFloatDataInAttributeList(int attributeNumber, int size, int vboId, FloatBuffer buffer)
	{
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public static int storeFloatDataInAttributeList(int attributeNumber, int size, float[] data)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		FloatBuffer buffer = toFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static int storeFloatDataInAttributeList(int attributeNumber, int size, FloatBuffer data)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		final FloatBuffer buffer = data.duplicate();
		buffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static void storeFloatDataInAttributeList(int attributeNumber, int size, int vboId, float[] data)
	{
		FloatBuffer buffer = toFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	/*
	 * Integer
	 */

	public static int storeIntDataInAttributeList(int attributeNumber, int size, List<Integer> data)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		IntBuffer buffer = toIntBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribIPointer(attributeNumber, size, GL_INT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static void storeIntDataInAttributeList(int attributeNumber, int size, int vboId, List<Integer> data)
	{
		IntBuffer buffer = toIntBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribIPointer(attributeNumber, size, GL_INT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public static int storeIntDataInAttributeList(int attributeNumber, int size, int[] data)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		IntBuffer buffer = toIntBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribIPointer(attributeNumber, size, GL_INT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static void storeIntDataInAttributeList(int attributeNumber, int size, int vboId, int[] data)
	{
		IntBuffer buffer = toIntBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribIPointer(attributeNumber, size, GL_INT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public static int storeByteDataInAttributeList(int attributeNumber, int size, List<Byte> data)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		ByteBuffer buffer = toByteBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribIPointer(attributeNumber, size, GL_INT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static int createVBO()
	{
		int vboId = glGenBuffers();
		vbos.add(vboId);
		return vboId;
	}

	public static int createVAO()
	{
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}

	public static void unbindVAO()
	{
		glBindVertexArray(0);
	}

	public static void bindVAO(int id)
	{
		glBindVertexArray(id);
	}

	private static FloatBuffer toFloatBuffer(List<Float> arr)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(arr.size());

		for (float i : arr)
			buff.put(i);
		buff.flip();

		return buff;
	}

	private static FloatBuffer toFloatBuffer(float[] list)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(list.length);
		for (float f : list)
			buff.put(f);
		buff.flip();
		return buff;
	}

	private static IntBuffer toIntBuffer(List<Integer> list)
	{
		IntBuffer buff = BufferUtils.createIntBuffer(list.size());
		for (int f : list)
			buff.put(f);
		buff.flip();
		return buff;
	}

	private static IntBuffer toIntBuffer(int[] list)
	{
		IntBuffer buff = BufferUtils.createIntBuffer(list.length);
		for (int f : list)
			buff.put(f);
		buff.flip();
		return buff;
	}

	public static void cleanUp()
	{
		for (int vao : vaos)
			glDeleteVertexArrays(vao);

		for (int vbo : vbos)
			glDeleteBuffers(vbo);
	}

	public static void delete(int vao, int...vbos)
	{
		vaos.remove((Integer) vao);
		glDeleteVertexArrays(vao);

		for (int i : vbos)
		{
			glDeleteBuffers(i);
			VertexObjectCreator.vbos.remove((Integer) i);
		}
	}

	public static ByteBuffer toByteBuffer(List<Byte> arr)
	{
		ByteBuffer buff = BufferUtils.createByteBuffer(arr.size());

		for (byte i : arr)
			buff.put(i);
		buff.flip();

		return buff;
	}

	public static void basicRender(int vao, int attributes, int vertexCount, int mode)
	{
		glBindVertexArray(vao);

		for (int i = 0; i < attributes; i++)
			glEnableVertexAttribArray(i);

		glDrawArrays(mode, 0, vertexCount);

		for (int i = 0; i < attributes; i++)
			glDisableVertexAttribArray(i);

		glBindVertexArray(0);
	}
}
