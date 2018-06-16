/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 18. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

import org.lwjgl.glfw.GLFW;

import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.game.Vec3;

public class PlayerLocation
{
	private Vec3 pos;
	private int width;
	private int height;
	
	public float pitch = 0;
	public float yaw = 0;
	public float roll = 0;
	
	public PlayerLocation()
	{
		pos = new Vec3();
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		mx = width / 2;
		my = height / 2;
	}
	
	private int mx, my, omx, omy, tmx, tmy;
	
	public void head(long window, int mouseX, int mouseY, float sensitivity)
	{
		if (mouseX != omx)
		{
			mx = omx - mouseX;
			tmx += mx;
			yaw = (float) Math.toRadians(tmx * sensitivity);
		}
		
		if (mouseY != omy)
		{
			my = omy - mouseY;
			tmy += my;
			pitch = (float) Math.toRadians(tmy * sensitivity);
		}
		
		GLFW.glfwSetCursorPos(window, width / 2, height / 2);
		omx = width / 2;
		omy = height / 2;

		if (pitch > rad_90)
		{
			tmy -= omy - mouseY;
			pitch = (float) rad_90;
		}
		if (pitch < -rad_90)
		{
			tmy -= omy - mouseY;
			pitch = (float) -rad_90;
		}
	}
	
	private static final double rad_90 = Math.toRadians(90);
	
	public void move(MainApplication mainApp, double speed)
	{
		if (mainApp.isKeyPressed(GLFW.GLFW_KEY_W))
		{
			setX(-getX() + Math.sin(yaw) * speed);
			setZ(getZ() - Math.cos(yaw) * speed);
		}
		if (mainApp.isKeyPressed(GLFW.GLFW_KEY_S))
		{
			setX(-getX() + Math.sin(yaw) * -speed);
			setZ(getZ() - Math.cos(yaw) * -speed);
		}
		if (mainApp.isKeyPressed(GLFW.GLFW_KEY_A))
		{
			setX(-getX() + Math.sin(yaw + rad_90) * speed);
			setZ(getZ() - Math.cos(yaw + rad_90) * speed);
		}
		if (mainApp.isKeyPressed(GLFW.GLFW_KEY_D))
		{
			setX(-getX() + Math.sin(yaw - rad_90) * speed);
			setZ(getZ() - Math.cos(yaw - rad_90) * speed);
		}
	}
	
	public void setPosition(Vec3 pos)
	{
		this.pos = pos;
	}
	
	public Vec3 getPosition()
	{
		return pos.clone().multiply(-1);
	}
	
	public float getX() { return pos.getFX(); }
	
	public float getY() { return pos.getFY(); }
	
	public float getZ() { return -pos.getFZ(); }
	
	public Vec3 setX(double x) { pos.setX(-x); return pos; }
	
	public Vec3 setY(double y) { pos.setY(-y); return pos; }
	
	public Vec3 setZ(double z) { pos.setZ(-z); return pos; }
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
