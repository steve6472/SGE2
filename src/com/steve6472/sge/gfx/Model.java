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

public class Model
{
	private int drawCount;
	private int vId; //Vertex Id
	private int tId; //Texture Id
	private int cId; //Color Id
	private int mode;
	
	public Model(float[] vertices, float[] texture, float[] colors)
	{
		drawCount = vertices.length / 2;
		
		mode = GL_TRIANGLES;
		
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
	
	public void render()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, vId);
		glVertexPointer(2, GL_FLOAT, 0, 0);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, tId);
		glTexCoordPointer(2, GL_FLOAT, 0, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, cId);
		glColorPointer(4, GL_FLOAT, 0, 0);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, 0);

		glDrawArrays(mode, 0, drawCount);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
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
		return "Model [drawCount=" + drawCount + ", vId=" + vId + ", tId=" + tId + ", cId=" + cId + "]";
	}

	public int getcId()
	{
		return cId;
	}
	
	public int getDrawCount()
	{
		return drawCount;
	}
	
	public int gettId()
	{
		return tId;
	}
	
	public int getvId()
	{
		return vId;
	}
	
	public int getMode()
	{
		return mode;
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
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
		
		drawCount = vertices.length / 2;
	}
	
}
