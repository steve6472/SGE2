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

	public static Vector3f getVector3Color(int color)
	{
		return new Vector3f((float) getRed(color) / 255f, (float) getGreen(color) / 255f, (float) getBlue(color) / 255f);
	}

	public static Vector4f getVector4Color(int color)
	{
		return new Vector4f((float) getRed(color) / 255f, (float) getGreen(color) / 255f, (float) getBlue(color) / 255f, (float) getAlpha(color) / 255f);
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

	public static int blend(int c1, int c2, double ratio)
	{
		if (ratio > 1) ratio = 1;
		else if (ratio < 0f) ratio = 0;

		int a1 = getAlpha(c1);
		int r1 = getRed(c1);
		int g1 = getGreen(c1);
		int b1 = getBlue(c1);

		int a2 = getAlpha(c2);
		int r2 = getRed(c2);
		int g2 = getGreen(c2);
		int b2 = getBlue(c2);

		int a = (int) (a1 + (a2 - a1) * ratio);
		int r = (int) (r1 + (r2 - r1) * ratio);
		int g = (int) (g1 + (g2 - g1) * ratio);
		int b = (int) (b1 + (b2 - b1) * ratio);

		return a << 24 | r << 16 | g << 8 | b;
	}

	public static int blendNoAlpha(int c1, int c2, double ratio)
	{
		if (ratio > 1) ratio = 1;
		else if (ratio < 0f) ratio = 0;
		double iRatio = 1.0 - ratio;

		int r1 = getRed(c1);
		int g1 = getGreen(c1);
		int b1 = getBlue(c1);

		int r2 = getRed(c2);
		int g2 = getGreen(c2);
		int b2 = getBlue(c2);

//		System.out.println(String.format("C1: %d, %d, %d", r1, g1, b1));
//		System.out.println(String.format("C2: %d, %d, %d", r2, g2, b2));

		int r = (int) ((r1 * iRatio) + (r2 * ratio));
		int g = (int) ((g1 * iRatio) + (g2 * ratio));
		int b = (int) ((b1 * iRatio) + (b2 * ratio));

//		System.out.println(String.format("Mix: %d, %d, %d", r, g, b));

		return (r << 16) | (g << 8) | b;
	}
}
