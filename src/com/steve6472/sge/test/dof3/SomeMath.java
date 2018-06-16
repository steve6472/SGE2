/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 6. 10. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.test.dof3;

public class SomeMath
{

	@SuppressWarnings("unused")
	private static double bilinearInterpolation(double bottomLeft, double topLeft, double bottomRight, double topRight, double xMin, double xMax,
			double zMin, double zMax, double x, double z)
	{
		double width = xMax - xMin, height = zMax - zMin,

				xDistanceToMaxValue = xMax - x, zDistanceToMaxValue = zMax - z,

				xDistanceToMinValue = x - xMin, zDistanceToMinValue = z - zMin;

		return 1.0f / (width * height)
				* (bottomLeft * xDistanceToMaxValue * zDistanceToMaxValue + bottomRight * xDistanceToMinValue * zDistanceToMaxValue
						+ topLeft * xDistanceToMaxValue * zDistanceToMinValue + topRight * xDistanceToMinValue * zDistanceToMinValue);
	}

	public static double smoothInterpolation(double bottomLeft, double topLeft, double bottomRight, double topRight, double xMin, double xMax,
			double zMin, double zMax, double x, double z)
	{
		double width = xMax - xMin, height = zMax - zMin;
		double xValue = 1 - (x - xMin) / width;
		double zValue = 1 - (z - zMin) / height;

		double a = smoothstep(bottomLeft, bottomRight, xValue);
		double b = smoothstep(topLeft, topRight, xValue);
		return smoothstep(a, b, zValue);
	}

	public static double smoothstep(double edge0, double edge1, double x)
	{
		// Scale, bias and saturate x to 0..1 range
		x = x * x * (3 - 2 * x);
		// Evaluate polynomial
		return (edge0 * x) + (edge1 * (1 - x));
	}

	public static double clamp(double x, double lowerlimit, double upperlimit)
	{
		if (x < lowerlimit)
			x = lowerlimit;
		if (x > upperlimit)
			x = upperlimit;
		return x;
	}

}
