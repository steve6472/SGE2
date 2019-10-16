package com.steve6472.sge.gfx;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.04.2019
 * Project: SJP
 *
 ***********************/
public class RainbowColor
{
	private final float fraction;

	public RainbowColor(float fraction)
	{
		this.fraction = fraction;
	}

	public RainbowColor()
	{
		this.fraction = 1f / 60f;
	}

	/**
	 * 0 = Growing Red
	 * 1 = Growing Green
	 * 2 = Shrinking Red
	 * 3 = Growing Blue
	 * 4 = Shrinking Green
	 * 5 = Growing Red
	 * 6 = Shrinking Blue
	 *
	 * Cycle: 0-> 1> 2> 3> 4> 5> 6> -- 1> 2> 3> 4> 5> 6> -- ...
	 */
	private byte stage = 0;

	private float red, green, blue;

	public RainbowColor offset(int tickTimes)
	{
		for (int i = 0; i < tickTimes; i++)
		{
			tick();
		}
		return this;
	}

	public void tick()
	{
		if (stage == 0)
		{
			red += fraction;
			if (red >= 1.0f) stage++;
		}
		else if (stage == 1)
		{
			green += fraction;
			if (green >= 1.0f) stage++;
		}
		else if (stage == 2)
		{
			red -= fraction;
			if (red <= 0.0f) stage++;
		}
		else if (stage == 3)
		{
			blue += fraction;
			if (blue >= 1.0f) stage++;
		}
		else if (stage == 4)
		{
			green -= fraction;
			if (green <= 0.0f) stage++;
		}
		else if (stage == 5)
		{
			red += fraction;
			if (red >= 1.0f) stage++;
		}
		else if (stage == 6)
		{
			blue -= fraction;
			if (blue <= 0.0f) stage = 1;
		}

		if (red < 0.0f) red = 0.0f;
		if (red > 1.0f) red = 1.0f;
		if (green < 0.0f) green = 0.0f;
		if (green > 1.0f) green = 1.0f;
		if (blue < 0.0f) blue = 0.0f;
		if (blue > 1.0f) blue = 1.0f;
	}

	public byte getStage()
	{
		return stage;
	}

	public float getRed()
	{
			return red;
	}

	public float getGreen()
	{
			return green;
	}

	public float getBlue()
	{
			return blue;
	}

	public String toColorTag()
	{
		return "[" + getRed() + "," + getGreen() + "," + getBlue() + "]";
	}
}
