package com.steve6472.sge.main.game;

import java.io.Serializable;

import com.steve6472.sge.main.Util;

public class Vec2 implements Serializable
{
	private static final long serialVersionUID = -6728175878841578459L;
	private double x, y;
	private double maxx, minx, maxy, miny;
	private boolean checkLimit = false;
	
	public Vec2()
	{
		this.x = 0;
		this.y = 0;
		limit();
	}
	
	public Vec2(Vec2 vec2)
	{
		setLocation(vec2.clone());
	}

	public Vec2(double x, double y)
	{
		this.x = x;
		this.y = y;
		limit();
	}
	
	public Vec2 move(Vec2 a)
	{
		return move(a.getX(), a.getY());
	}
	
	public Vec2 move(double xx, double yy)
	{
		x += xx;
		y += yy;
		limit();
		return this;
	}
	
	public Vec2 move(double xx, double yy, double speed)
	{
		x += xx * speed;
		y += yy * speed;
		limit(); 
		return this;
	}
	
	/**
	 * 
	 * @param angle
	 * @return
	 */
	public Vec2 move(double angle)
	{
		double xa = ((Math.cos(Math.toRadians(angle - 90))) * 1);
		double ya = ((Math.sin(Math.toRadians(angle - 90))) * 1);
		move(xa, ya);
		return this;
	}
	
	public Vec2 move2(double angle, double speed)
	{
		double xa = ((Math.cos(Math.toRadians(angle - 90))) * 1);
		double ya = ((Math.sin(Math.toRadians(angle - 90))) * 1);
		move(xa, ya, speed);
		return this;
	}
	
	public Vec2 setLimit(double limit)
	{
		setLimit(-limit, limit, -limit, limit);
		return this;
	}
	
	public Vec2 fromAngle(double angle)
	{
		return new Vec2(Math.cos(angle), Math.sin(angle));
	}
	
	public Vec2 setLimit(double minx, double maxx, double miny, double maxy)
	{
		this.minx = minx;
		this.maxx = maxx;
		
		this.miny = miny;
		this.maxy = maxy;
		checkLimit = true;
		return this;
	}
	
	public boolean checkLimit()
	{
		if (!checkLimit) return false;
		return !Util.isInRectangle(minx, miny, maxx, maxy, x, y);
	}
	
	public void limit()
	{
		if (!checkLimit()) return;
		
		if (x < minx) x = minx;
		if (x > maxx) x = maxx;
		
		if (y < miny) y = miny;
		if (y > maxy) y = maxy;
	}
	
	public void limit(double limit)
	{
		if (x < -limit) x = -limit;
		if (x > limit) x = limit;
		
		if (y < -limit) y = -limit;
		if (y > limit) y = limit;
	}
	
	/**
	 * 
	 * @param limit
	 * @return true when out of limit
	 */
	public boolean checkLimit(double limit)
	{
		if (x < -limit) return true;
		if (x > limit) return true;
		
		if (y < -limit) return true;
		if (y > limit) return true;
		
		return false;
	}
	
	public double getX() { return x; }
	
	public double getY() { return y; }
	
	public int getIntX() { return (int) x; }
	
	public int getIntY() { return (int) y; }
	
	public float getFX() { return (float) x; }
	
	public float getFY() { return (float) y; }
	
	public Vec2 setX(double x) { this.x = x; limit(); return this; }
	
	public Vec2 setY(double y) { this.y = y; limit(); return this; }

	public Vec2 up() { this.y--; limit(); return this; }

	public Vec2 down() { this.y++; limit(); return this; }

	public Vec2 left() { this.x--; limit(); return this; }

	public Vec2 right() { this.x++; limit(); return this; }

	public Vec2 up(double d) { this.y -= d; limit(); return this; }

	public Vec2 down(double d) { this.y += d; limit(); return this; }

	public Vec2 left(double d) { this.x -= d; limit(); return this; }

	public Vec2 right(double d) { this.x += d; limit(); return this; }
	
	public Vec2 devide(double d) { this.x /= d; this.y /= d; limit(); return this; }
	
	public Vec2 multiply(double m) { this.x /= m; this.y /= m; limit(); return this; }
	
	public AABB toAABB(double width, double height) { return new AABB(this, width, height); }
	
	public void invert() { this.x = -x; this.y = -y; limit(); }

	/**
	 * 
	 * @return inverted clone (x = -x; y = -y;)
	 */
	public Vec2 getInverted() { Vec2 cloned = clone(); cloned.invert(); return cloned; }

	public Vec2 clone() { return new Vec2(x, y); }

	public Vec2 add(Vec2 add)
	{
		this.x += add.getX();
		this.y += add.getY();
		limit();
		return this;
	}
	
	public Vec2 round()
	{
		this.x = Math.round(this.x);
		this.y = Math.round(this.y);
		return this;
	}

	public Vec2 setLocation(double x, double y) { this.x = x; this.y = y; return this; }
	
	public Vec2 setLocation(Vec2 vec2)
	{
		if (vec2 == null) 
			{ setLocation(0, 0); limit(); return this; }
		else 
			{ setLocation(vec2.clone().getX(), vec2.clone().getY()); limit(); return this; }
	}

	public static Vec2 getVec2InRange(double xmin, double xmax, double ymin, double ymax)
	{
		return new Vec2(Util.getRandomDouble(xmax, xmin), Util.getRandomDouble(ymax, ymin));
	}
	
	public void printLocation()
	{
		System.out.println("X: " + getX() + " / Y: " + getY());
	}
	
	@Override
	public String toString()
	{
		return "Vec2 [x=" + x + ", y=" + y + "]";
	}
	
	public String format()
	{
		return "X/Y: " + x + " / " + y;
	}

	public boolean equals(Object vec2)
	{
		if (vec2 instanceof Vec2)
		{
			Vec2 vec = (Vec2) vec2;
			return vec.getX() == getX() && vec.getY() == getY();
		}
		return false;
	}
}
