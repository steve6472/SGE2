/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 18. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test;

import org.joml.Matrix4f;

import com.steve6472.sge.main.game.Vec2;

public class Camera
{
	private Vec2 pos;
	private Matrix4f projection;
	private int width;
	private int height;
	
	public Camera()
	{
		pos = new Vec2();
		projection = new Matrix4f();
	}
	
	public void setSize(int width, int height)
	{
		projection.setOrtho2D(width / 2, -width / 2, -height / 2, height / 2);
		this.width = width;
		this.height = height;
	}
	
	public Matrix4f getProjection()
	{
		Matrix4f target = new Matrix4f();
		Matrix4f pos = new Matrix4f().
				setTranslation((float) this.pos.getX(), (float) this.pos.getY(), 0);
		
		target = projection.mul(pos, target);
		return target;
	}
	
	public void setPosition(Vec2 pos)
	{
		this.pos = pos;
	}
	
	public Vec2 getPosition()
	{
		return pos;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
