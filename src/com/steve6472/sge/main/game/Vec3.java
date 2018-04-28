package com.steve6472.sge.main.game;

import java.io.Serializable;

import com.steve6472.sge.main.Util;

public class Vec3 implements Serializable
{
	private static final long serialVersionUID = -6728175878841578459L;
	private double x, y, z;
	
	public Vec3()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vec3(Vec3 vec3)
	{
		setLocation(vec3.clone());
	}

	public Vec3(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3 move(Vec3 a)
	{
		return move(a.getX(), a.getY(), a.getZ());
	}
	
	public Vec3 move(double xx, double yy, double zz)
	{
		x += xx;
		y += yy;
		return this;
	}
	
	public Vec3 move(double xx, double yy, double zz, double speed)
	{
		x += xx * speed;
		y += yy * speed;
		z += zz * speed;
		return this;
	}
	
	/**
	 * 
	 * @param angle
	 * @return
	 * @deprecated This method will most likely not work
	 */
	@Deprecated
	public Vec3 move(double angle)
	{
		double xa = ((Math.cos(Math.toRadians(angle - 90))) * 1);
		double ya = ((Math.sin(Math.toRadians(angle - 90))) * 1);
		double za = ((Math.tan(Math.toRadians(angle - 90))) * 1);
		move(xa, ya, za);
		return this;
	}

	
	/**
	 * @deprecated This method will most likely not work
	 */
	@Deprecated
	public Vec3 move2(double angle, double speed)
	{
		double xa = ((Math.cos(Math.toRadians(angle - 90))) * 1);
		double ya = ((Math.sin(Math.toRadians(angle - 90))) * 1);
		double za = ((Math.tan(Math.toRadians(angle - 90))) * 1);
		move(xa, ya, za, speed);
		return this;
	}
	
	public double getX() { return x; }
	
	public double getY() { return y; }
	
	public double getZ() { return z; }
	
	public int getIntX() { return (int) x; }
	
	public int getIntY() { return (int) y; }
	
	public int getIntZ() { return (int) z; }
	
	public float getFX() { return (float) x; }
	
	public float getFY() { return (float) y; }
	
	public float getFZ() { return (float) z; }
	
	public Vec3 setX(double x) { this.x = x; return this; }
	
	public Vec3 setY(double y) { this.y = y; return this; }
	
	public Vec3 setZ(double z) { this.z = z; return this; }

	public Vec3 up() { this.y--; return this; }

	public Vec3 down() { this.y++; return this; }

	public Vec3 left() { this.x--; return this; }

	public Vec3 right() { this.x++; return this; }

	public Vec3 forward() { this.z--; return this; }

	public Vec3 back() { this.z++; return this; }

	public Vec3 up(double d) { this.y -= d; return this; }

	public Vec3 down(double d) { this.y += d; return this; }

	public Vec3 left(double d) { this.x -= d; return this; }

	public Vec3 right(double d) { this.x += d; return this; }
	
	public Vec3 devide(double d) { this.x /= d; this.y /= d; return this; }
	
	public Vec3 multiply(double m) { this.x /= m; this.y /= m; return this; }
	
//	public AABB toAABB(double width, double height) { return new AABB(this, width, height); }
	
	public void invert() { this.x = -x; this.y = -y; }

	/**
	 * 
	 * @return inverted clone (x = -x; y = -y;)
	 */
	public Vec3 getInverted() { Vec3 cloned = clone(); cloned.invert(); return cloned; }

	public Vec3 clone() { return new Vec3(x, y, z); }

	public Vec3 add(Vec3 add)
	{
		this.x += add.getX();
		this.y += add.getY();
		this.z += add.getZ();
		return this;
	}
	
	public Vec3 round()
	{
		this.x = Math.round(this.x);
		this.y = Math.round(this.y);
		this.z = Math.round(this.z);
		return this;
	}

	public Vec3 setLocation(double x, double y, double z) { this.x = x; this.y = y; this.z = z; return this; }
	
	public Vec3 setLocation(Vec3 vec3)
	{
		if (vec3 == null) 
			{ setLocation(0, 0, 0); return this; }
		else 
			{Vec3 clone = vec3.clone(); setLocation(clone.getX(), clone.getY(), clone.getZ()); return this; }
	}

	public static Vec3 getVec2InRange(double xmin, double xmax, double ymin, double ymax, double zmin, double zmax)
	{
		return new Vec3(Util.getRandomDouble(xmax, xmin), Util.getRandomDouble(ymax, ymin), Util.getRandomDouble(zmax, zmin));
	}
	
	public void printLocation()
	{
		System.out.println("X: " + getX() + " / Y: " + getY() + " / Z: " + getZ());
	}
	
	@Override
	public String toString()
	{
		return "Vec2 [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	public String format()
	{
		return "X/Y/Z: " + x + " / " + y + " / " + z;
	}

	public boolean equals(Object vec3)
	{
		if (vec3 instanceof Vec3)
		{
			Vec3 vec = (Vec3) vec3;
			return vec.getX() == getX() && vec.getY() == getY() && vec.getZ() == getZ();
		}
		return false;
	}
}
