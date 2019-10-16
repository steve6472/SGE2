/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 21. 5. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.util;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class ColorUtil
{
	public static int getColor(int r, int g, int b, int a)
	{
		return ((a & 0xff) << 24) | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
	}

	public static int getColor(int r, int g, int b)
	{
		return getColor(r, g, b, 255);
	}
	
	public static int getColor(int gray)
	{
		return getColor(gray, gray, gray, 255);
	}
	
	public static int getColor(int gray, float red, float green, float blue)
	{
		return getColor((int) (gray * red), (int) (gray * green), (int) (gray * blue));
	}
	
	public static int getColor(float red, float green, float blue, float alpha)
	{
		return getColor((int) (red * 255f), (int) (green * 255f), (int) (blue * 255f), (int) (alpha * 255f));
	}
	
	public static int getColor(double gray)
	{
		return getColor((int) gray);
	}
	
	public static float[] getColors(int color)
	{
		return new float[] { (float) getRed(color) / 255f, (float) getGreen(color) / 255f, (float) getBlue(color) / 255f, (float) getAlpha(color) / 255f };
	}

	public static Vector3f getVectorColor(int color)
	{
		return new Vector3f((float) getRed(color) / 255f, (float) getGreen(color) / 255f, (float) getBlue(color) / 255f);
	}
	
	public static int getRed(int color)
	{
		return (color >> 16) & 0xff;
	}
	
	public static int getGreen(int color)
	{
		return (color >> 8) & 0xff;
	}
	
	public static int getBlue(int color)
	{
		return color & 0xff;
	}
	
	public static int getAlpha(int color)
	{
		return (color >> 24) & 0xff;
	}

	public static int getColor(Vector4f color)
	{
		return getColor(color.x, color.y, color.z, color.w);
	}
}
