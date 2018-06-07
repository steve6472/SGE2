/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 1. 6. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

import org.lwjgl.opengl.GL11;

import com.steve6472.sge.main.SGArray;

public class Primitive
{
	
	public final int type;
	
	public final SGArray<Integer> vertices = new SGArray<Integer>(0, false, false);

	public Primitive(int type)
	{
		this.type = type;
	}
	
	private String getTypeString()
	{
		switch(type)
		{
		case GL11.GL_TRIANGLES: 		return "GL_TRIANGLES";
		case GL11.GL_TRIANGLE_STRIP: 	return "GL_TRIANGLE_STRIP";
		case GL11.GL_TRIANGLE_FAN: 		return "GL_TRIANGLE_FAN";
		default: 						return Integer.toString(type);
		}
	}
	
	@Override
	public String toString()
	{
		String s = "New Primitive " + getTypeString();
		for (int v : vertices)
		{
			s += "\nIndex: " + v;
		}
		return s;
	}

}
