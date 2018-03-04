/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 18. 9. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.main.game;

public class GravityUtil
{
	/**
	 * m/s^2
	 */
	public static final double NORMAL_GRAVITY = 9.80665;
	
	public static double getVel(double gravity, double time)
	{
		return gravity * time;
	}
	
	public static double getVelY(double gravity, double time, double y)
	{
		return (gravity * time) + y;
	}
	
	public static double getFallenDistance(double gravity, double time, double initialY)
	{
		return 0.5d * gravity * Math.pow(time, 2);
	}
	
	public static double getSpeed(double gravity, double time, double initialY)
	{
		double s = getFallenDistance(gravity, time, initialY);
		return Math.sqrt(2 * gravity * s);
	}
}
