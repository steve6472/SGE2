/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 9. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.main.game.particle;

import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.game.GravityUtil;

public class GravityParticle extends Particle
{
	private static final long serialVersionUID = 6797574605808154666L;
	double f = 0;
	double time = 0;

	public GravityParticle(double x, double y, double dx, double dy, int life, Sprite sprite)
	{
		super(x, y, dx, dy, life, sprite);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		time++;
		pos.setY(GravityUtil.getVelY(0.08, time, pos.getY()));
	}

}
