package com.steve6472.sge.main.game;

import java.awt.Dimension;
import java.io.Serializable;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;

public class AABB implements Serializable
{
	private static final long serialVersionUID = 3446849812898348436L;
	public Vec2 from;
	public Vec2 to;

	public AABB(double x0, double y0, double x1, double y1)
	{
		from = new Vec2(x0, y0);
		to = new Vec2(x1, y1);
	}

	public AABB(Vec2 loc, double width, double height)
	{
		this.from = loc.clone();
		this.to = new Vec2(loc.getX() + width, loc.getY() + height);
	}

	public AABB(Vec2 from, Vec2 to)
	{
		this.from = from.clone();
		this.to = to.clone();
	}

	/**
	 * Sets location of AABB by loc and size by sprite size
	 * @param loc
	 * @param size
	 */
	public AABB (Vec2 loc, Sprite size)
	{
		this.from = loc.clone();
		this.to = new Vec2(loc.getX() + size.getHeight(), loc.getY() + size.getWidth());
	}
	
	public AABB(Dimension size)
	{
		this(new Vec2(0, 0), size.getWidth(), size.getHeight());
	}

	public AABB clone()
	{
		return new AABB(from, to);
	}

	public double getWidth()
	{
		return to.getX() - from.getX();
	}

	public double getHeight()
	{
		return to.getY() - from.getY();
	}

	public void move(double xa, double ya)
	{
		this.from.move(xa, ya);
		this.to.move(xa, ya);
	}

	public void move(Vec2 a)
	{
		this.from.move(a.getX(), a.getY());
		this.to.move(a.getY(), a.getX());
	}

	public void setPosition(double x0, double y0, double x1, double y1)
	{
		this.from.setLocation(x0, y0);
		this.to.setLocation(x1, y1);
	}

	public void setLocation(double x, double y)
	{
		double width = getWidth();
		double height = getHeight();
		
		this.from.setLocation(x, y);
		this.to.setLocation(x + width, y + height);
	}

	public void setLocation(Vec2 loc)
	{
		double width = getWidth();
		double height = getHeight();
		
		this.from.setLocation(loc.getX(), loc.getY());
		this.to.setLocation(loc.getX() + width, loc.getY() + height);
	}
	
	public AABB expand(double xa, double ya)
	{
		double _x0 = this.from.getX();
		double _y0 = this.from.getY();
		double _x1 = this.to.getX();
		double _y1 = this.to.getY();
		if (xa < 0.0F)
		{
			_x0 += xa;
		}
		if (xa > 0.0F)
		{
			_x1 += xa;
		}
		if (ya < 0.0F)
		{
			_y0 += ya;
		}
		if (ya > 0.0F)
		{
			_y1 += ya;
		}
		return new AABB(_x0, _y0, _x1, _y1);
	}

	public AABB grow(double xa, double ya)
	{
		double _x0 = this.from.getX() - xa;
		double _y0 = this.from.getY() - ya;
		double _x1 = this.to.getX() + xa;
		double _y1 = this.to.getY() + ya;

		return new AABB(_x0, _y0, _x1, _y1);
	}

	public AABB expandFromCenter(double w, double h)
	{
		double x = getCenterX();
		double y = getCenterY();

		return new AABB(x - getWidth() / 2 - w / 2, y - getHeight() / 2 - h / 2, x + getWidth() / 2 + w / 2, y + getHeight() / 2 + h / 2);
	}

	public boolean intersects(AABB c)
	{
		if (c == null)
			return false;
		
		if ((c.to.getX() <= this.from.getX()) || (c.from.getX() >= this.to.getX()))
		{
			return false;
		}
		if ((c.to.getY() <= this.from.getY()) || (c.from.getY() >= this.to.getY()))
		{
			return false;
		}
		return true;
	}

	public boolean intersects(Vec2 point)
	{
		if (point == null)
			return false;
		return (point.getX() >= from.getX() && point.getX() <= getWidth() + from.getX())
				&& (point.getY() >= from.getY() && point.getY() <= getHeight() + from.getY());
	}
	
	public boolean isIdentical(AABB ab)
	{
		return ab.from.equals(from) && ab.to.equals(to);
	}

	public double getCenterX()
	{
		return from.getX() + getWidth() / 2;
	}

	public double getCenterY()
	{
		return from.getY() + getHeight() / 2;
	}

	public Vec2 getCenter()
	{
		return new Vec2(getCenterX(), getCenterY());
	}

	public void render(Screen screen, int color)
	{
		Screen.fillRect(from.getIX(), from.getIY(), (int) getWidth(), (int) getHeight(), color);
	}

	@Override
	public String toString()
	{
		return "AABB [x0=" + from.getX() + ", x1=" + to.getX() + ", y0=" + from.getY() + ", y1=" + to.getY() + "]";
	}
}
