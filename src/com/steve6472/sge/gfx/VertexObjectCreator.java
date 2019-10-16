package com.steve6472.sge.gfx;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 31.8.2018
 * Project: 3DTest
 *
 ***********************/
public class VertexObjectCreator
{
	private static List<Integer> vaos = new ArrayList<>();
	private static List<Integer> vbos = new ArrayList<>();

	public static int storeDataInAttributeList(int attributeNumber, int size, List<Float> data)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		FloatBuffer buffer = Tessellator3D.toFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	public static void storeDataInAttributeList(int attributeNumber, int size, int vboId, List<Float> data)
	{
		FloatBuffer buffer = Tessellator3D.toFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public static int storeDataInAttributeList(int attributeNumber, int size, float[] data)
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

	public static void storeDataInAttributeList(int attributeNumber, int size, int vboId, float[] data)
	{
		FloatBuffer buffer = toFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public static int storeDataInAttributeListB(int attributeNumber, int size, List<Byte> data)
	{
		int vboID = glGenBuffers();
		vbos.add(vboID);
		ByteBuffer buffer = toByteBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_BYTE, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return vboID;
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

	private static FloatBuffer toFloatBuffer(float[] list)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(list.length);
		for (float f: list)
		{
			buff.put(f);
		}
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
