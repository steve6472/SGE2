package steve6472.sge.main.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.regex.Pattern;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class MathUtil
{
	private static final float FLOAT_COMPARE_PRECISION = 0.00000001f;
	private static final Pattern IS_DECIMAL = Pattern.compile("([+-]?\\d*(\\.\\d+)?)+");
	private static final Pattern IS_INTEGER = Pattern.compile("([+-]?\\d)+");

	/*
	 * Clamp
	 */

	public static double clamp(double number, double min, double max)
	{
		return Math.min(Math.max(number, min), max);
	}

	public static float clamp(float number, float min, float max)
	{
		return Math.min(Math.max(number, min), max);
	}

	public static int clamp(int number, int min, int max)
	{
		return Math.min(Math.max(number, min), max);
	}

	/*
	 * Is number in range
	 */

	public static boolean isNumberInRange(double number, double min, double max)
	{
		return (number >= min && number <= max);
	}

	public static boolean isNumberInRange(float number, float min, float max)
	{
		return (number >= min && number <= max);
	}

	public static boolean isNumberInRange(int number, int min, int max)
	{
		return (number >= min && number <= max);
	}

	/*
	 * Compare float
	 */

	public static boolean compareFloat(float f1, float f2)
	{
		return Math.abs(f1 - f2) < FLOAT_COMPARE_PRECISION;
	}

	public static boolean compareFloat(float f1, float f2, float epsilon)
	{
		return Math.abs(f1 - f2) < epsilon;
	}

	/*
	 * Animation Util
	 */

	public static double time(double start, double end, double time)
	{
		// Prevent NaN
		if (time - start == 0 || end - start == 0)
			return 0;

		return 1.0 / ((end - start) / (time - start));
	}

	public static double lerp(double start, double end, double value)
	{
		return start + value * (end - start);
	}

	public static double catmullLerp(double p0, double p1, double p2, double p3, double t)
	{
		return 0.5 * ((2.0 * p1) + (p2 - p0) * t + (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3) * t * t + (3.0 * p1 - p0 - 3.0 * p2 + p3) * t * t * t);
	}

	/*
	 * Geometry
	 */

	public static Vector3f yawPitchToVector(float yaw, float pitch)
	{
		float xzLen = (float) Math.cos(pitch);
		return new Vector3f(xzLen * (float) Math.cos(yaw), (float) Math.sin(pitch), xzLen * (float) Math.sin(-yaw));
	}

	public static boolean isInRectangle(int rminx, int rminy, int rmaxx, int rmaxy, int px, int py)
	{
		return px >= rminx && px <= rmaxx && py >= rminy && py <= rmaxy;
	}

	public static boolean isInRectangle(double rminx, double rminy, double rmaxx, double rmaxy, double px, double py)
	{
		return px >= rminx && px <= rmaxx && py >= rminy && py <= rmaxy;
	}

	public static float normalize(float x, float minX, float maxX)
	{
		return (x - minX) / (maxX - minX);
	}

	public static double normalize(double x, double minX, double maxX)
	{
		return (x - minX) / (maxX - minX);
	}

	/*
	 * Other
	 */

	public static boolean isEven(int number)
	{
		return number % 2 == 0;
	}

	public static boolean isInteger(String text)
	{
		return IS_INTEGER.matcher(text).matches();
	}

	public static boolean isDecimal(String text)
	{
		return IS_DECIMAL.matcher(text).matches();
	}

	public static Matrix4f createProjectionMatrix(float width, float height, float farPlane, float fov)
	{
		final float NEAR_PLANE = 0.1f;
		float aspectRatio = width / height;

		return new Matrix4f().perspective((float) Math.toRadians(fov), aspectRatio, NEAR_PLANE, farPlane);
	}
}
