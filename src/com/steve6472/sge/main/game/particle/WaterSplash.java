/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 18. 9. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.main.game.particle;

import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.Util;
import com.steve6472.sge.main.game.GravityUtil;

public class WaterSplash extends Particle
{
	private static final long serialVersionUID = 7805127716952171146L;
	double f = 0;
	double time = 0;
	
	public WaterSplash(double x, double y, int life, Sprite sprite)
	{
		super(x, y, Util.getRandomDouble(1, -1), Util.getRandomDouble(-1, -2), life, sprite);
		setSpeed(3);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		time++;
		pos.setY(GravityUtil.getVelY(GravityUtil.NORMAL_GRAVITY, time, pos.getY()));
	}

}
