/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 13. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Model3
{
	private int drawCount;
	private int vId; //Vertex Id
	private int tId; //Texture Id
	private int cId; //Color Id
	public int mode;
	
	public Model3()
	{
		this.mode = GL_TRIANGLES;
		
		vId = glGenBuffers();
		tId = glGenBuffers();
		cId = glGenBuffers();
		
		System.out.println("Created Empty vId:" + vId + " tId:" + tId + " cId:" + cId);
	}
	
	public Model3(float[] vertices, float[] texture, float[] colors)
	{
		drawCount = vertices.length / 3;
		this.mode = GL_TRIANGLES;
		
		vId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);

		tId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, tId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(texture), GL_STATIC_DRAW);

		cId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, cId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(colors), GL_STATIC_DRAW);
        
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		System.out.println("Created vId:" + vId + " tId:" + tId + " cId:" + cId);
	}
	
	public static void start()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
	}
	
	public static void bindBuffers(Model3 model)
	{
		
		glBindBuffer(GL_ARRAY_BUFFER, model.vId);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, model.tId);
		glTexCoordPointer(2, GL_INT, 0, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, model.cId);
		glColorPointer(4, GL_FLOAT, 0, 0);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, 0);
	}
	
	public static void drawArrays(Model3 model, int mode)
	{
		glDrawArrays(mode, 0, model.drawCount);
	}
	
	public static void end()
	{
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	public void render()
	{
		start();
		
		bindBuffers(this);
		
		drawArrays(this, mode);
		
		end();
	}
	
	private FloatBuffer createBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public String toString()
	{
		return "Model3 [drawCount=" + drawCount + ", vId=" + vId + ", tId=" + tId + ", cId=" + cId + "]";
	}
	
	/**
	 * @Deprecated - Just don't use it in acutal program please!
	 * Use only for testing
	 */
	@Deprecated
	public void changeData(float[] vertices, float[] texture, float[] colors)
	{
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, tId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(texture), GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, cId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(colors), GL_STATIC_DRAW);
        
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		drawCount = vertices.length / 3;
	}
	
	/**
	 * @Deprecated - Just don't use it in acutal program please!
	 * Use only for testing
	 */
	@Deprecated
	public void changeData(FloatBuffer vertices, FloatBuffer texture, FloatBuffer colors)
	{
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, tId);
		glBufferData(GL_ARRAY_BUFFER, texture, GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, cId);
		glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
        
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		drawCount = vertices.capacity() / 3;
	}
	
	
}
