package steve6472.sge.main.game;

import steve6472.sge.main.game.mixable.IPosition3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**********************
 * Created by steve6472
 * On date: 19.12.2018
 * Project: Poly Creator 2.0
 *
 ***********************/
public class Camera implements IPosition3f
{
	protected Vector3f viewPosition, center;
	protected float yaw, pitch, roll;
	protected Matrix4f viewMatrix;

	public Camera()
	{
		viewPosition = new Vector3f();
		center = new Vector3f();
		viewMatrix = new Matrix4f();
	}

	public int oldx;
	public int oldy;

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

		if (pitch > 1.5707963267948966f) pitch = 1.5707963267948966f;
		if (pitch < -1.5707963267948966f) pitch = -1.5707963267948966f;

		while (yaw > 6.283185307179586f) yaw -= 6.283185307179586f;
		while (yaw < 0) yaw += 6.283185307179586f;
	}

	/**
	 *
	 * @param mouseX mouseX
	 * @param mouseY mouseY
	 * @param sensitivity sensitivity
	 * @param orbitalDistance distance from orbited point
	 */
	public void headOrbit(int mouseX, int mouseY, float sensitivity, float orbitalDistance)
	{
		head(mouseX, mouseY, sensitivity);
		calculateOrbit(orbitalDistance);
	}

	public void calculateOrbit(float orbitalDistance)
	{
		float x, y, z;
		x = (float) (Math.sin(yaw) * (Math.cos(pitch) * orbitalDistance)) + center.x;
		y = (float) (-Math.sin(pitch) * orbitalDistance) + center.y;
		z = (float) (Math.cos(yaw) * (Math.cos(pitch) * orbitalDistance)) + center.z;
		viewPosition.set(x, y, z);
	}

	public void updateViewMatrix()
	{
		viewMatrix.identity();

		viewMatrix.rotate(getPitch(), -1, 0, 0);
		viewMatrix.rotate(getYaw(), 0, -1, 0);
		viewMatrix.rotate(getRoll(), 0, 0, -1);

		viewMatrix.translate(-viewPosition.x, -viewPosition.y, -viewPosition.z);
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

	@Override
	public Vector3f getPosition()
	{
		return center;
	}

	public Vector3f getViewPosition()
	{
		return viewPosition;
	}

	@Override
	public String toString()
	{
		return "Camera{" + "viewPosition=" + viewPosition + ", center=" + center + ", yaw=" + yaw + ", pitch=" + pitch + ", roll=" + roll + '}';
	}
}
