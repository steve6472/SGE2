package com.steve6472.sge.main.game;

import com.steve6472.sge.main.game.mixable.IPosition3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.12.2018
 * Project: Poly Creator 2.0
 *
 ***********************/
public class Camera implements IPosition3f
{
	private Vector3f position;
	private float yaw, pitch, roll;
	private boolean canMoveHead = true;
	private Matrix4f viewMatrix;

	public Camera()
	{
		position = new Vector3f();
		viewMatrix = new Matrix4f();
	}

	public int oldx;
	public int oldy;

	private static final double rad_90 = Math.PI * 0.5;

	public void head(int mouseX, int mouseY, float sensitivity)
	{
		if (mouseX != oldx)
		{
			int dx = oldx - mouseX;
			yaw += (float) Math.toRadians((float) dx * sensitivity);
		}

		if (mouseY != oldy)
		{
			int dy = oldy - mouseY;
			pitch += (float) Math.toRadians((float) dy * sensitivity);
		}

		oldx = mouseX;
		oldy = mouseY;

		if (pitch > rad_90) pitch = (float) rad_90;
		if (pitch < -rad_90) pitch = (float) -rad_90;
	}

	/**
	 *
	 * @param mouseX mouseX
	 * @param mouseY mouseY
	 * @param sensitivity sensitivity
	 * @param cx observer point coord x
	 * @param cy observer point coord y
	 * @param cz observer point coord z
	 * @param orbitalDistance distance from orbited point
	 */
	public void headOrbit(int mouseX, int mouseY, float sensitivity, float cx, float cy, float cz, float orbitalDistance)
	{
		head(mouseX, mouseY, sensitivity);
		calculateOrbit(cx, cy, cz, orbitalDistance);
	}

	public void calculateOrbit(float cx, float cy, float cz, float orbitalDistance)
	{
		float x, y, z;
		x = (float) (Math.sin(yaw) * (Math.cos(pitch) * orbitalDistance)) + cx;
		y = (float) (-Math.sin(pitch) * orbitalDistance) + cy;
		z = (float) (Math.cos(yaw) * (Math.cos(pitch) * orbitalDistance)) + cz;
		setPosition(x, y, z);
	}

	public void updateViewMatrix()
	{
		viewMatrix.identity();

		viewMatrix.rotate(getPitch(), -1, 0, 0);
		viewMatrix.rotate(getYaw(), 0, -1, 0);
		viewMatrix.rotate(getRoll(), 0, 0, -1);

		viewMatrix.translate(-getX(), -getY(), -getZ());
	}

	public Matrix4f getViewMatrix()
	{
		return viewMatrix;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll()
	{
		return roll;
	}

	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}

	public void setYaw(float yaw)
	{
		this.yaw = yaw;
	}

	public void setRoll(float roll)
	{
		this.roll = roll;
	}

	public boolean canMoveHead()
	{
		return canMoveHead;
	}

	public void setCanMoveHead(boolean canMoveHead)
	{
		this.canMoveHead = canMoveHead;
	}

	@Override
	public Vector3f getPosition()
	{
		return position;
	}

	@Override
	public String toString()
	{
		return "Camera{" + "position=" + position + ", yaw=" + yaw + ", pitch=" + pitch + ", roll=" + roll + ", canMoveHead=" + canMoveHead + '}';
	}
}
