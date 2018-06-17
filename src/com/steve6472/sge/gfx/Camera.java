/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 18. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

import org.joml.Matrix4f;

import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.game.Vec3;

public class Camera
{
	private Vec3 pos;
	private Matrix4f projection;
	private int width;
	private int height;
	
	public Camera()
	{
		pos = new Vec3();
		projection = new Matrix4f();
	}
	
	public void tick(MainApplication mainApp)
	{
		setSize(mainApp.getCurrentWidth(), mainApp.getCurrentHeight());
	}
	
	public void setSize(int width, int height)
	{
		projection.setOrtho2D(width / 2, -width / 2, -height / 2, height / 2);
		this.width = width;
		this.height = height;
	}
	
	public Matrix4f getViewMatrix(PlayerLocation player)
	{
		Matrix4f viewMatrix = new Matrix4f();
		
		viewMatrix.rotate(player.pitch, -1, 0, 0);
		viewMatrix.rotate(player.yaw, 0, -1, 0);
		viewMatrix.rotate(player.roll, 0, 0, -1);
		
		viewMatrix.translate(player.getPosition().getFX(), player.getPosition().getFY(), player.getPosition().getFZ());
		
		return viewMatrix;
	}
	
	public Matrix4f getProjection()
	{
		Matrix4f target = new Matrix4f();
		Matrix4f pos = new Matrix4f().
				setTranslation((float) this.pos.getX(), (float) this.pos.getY(), (float) this.pos.getZ());
		
		target = projection.mul(pos, target);
		return target;
	}
	
	public void setPosition(Vec3 pos)
	{
		this.pos = pos;
	}
	
	public Vec3 getPosition()
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
	
	public float getFX()
	{
		return pos.getFX();
	}
	
	public float getFY()
	{
		return pos.getFY();
	}
	
	public float getFZ()
	{
		return pos.getFZ();
	}
	
	public int getX()
	{
		return pos.getIntX();
	}
	
	public int getY()
	{
		return pos.getIntY();
	}
	
	public int getZ()
	{
		return pos.getIntZ();
	}
}
