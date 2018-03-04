package com.steve6472.sge.main.game.particle;

import com.steve6472.sge.gfx.Sprite;

public class BouncingParticle extends Particle
{
	private static final long serialVersionUID = -7227425943672444437L;
	protected double angle = 0;
	protected double staticY = 0;

	public BouncingParticle(double x, double y, int life, Sprite s)
	{
		super(x, 1, 0, 0, life, s);
		staticY = y;
		dy = 1;
	}

	@Override
	protected void move()
	{
		if (!isDead())
		{
			if (angle < 90)
				dy = dy * 1.05;
			else if (angle > 90)
				dy = dy * 0.95;
			
			angle++;
			
			if (angle >= 180)
				angle = 0;
			
			pos.setLocation(pos.getX() + dx * speed, staticY + (dy * speed));
			
			life--;
			if (life <= 0)
				setDead();
			checkPos();
		}
	}

}
