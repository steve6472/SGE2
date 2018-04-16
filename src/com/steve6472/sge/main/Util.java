package com.steve6472.sge.main;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class Util
{

	public static final int HOVERED_OVERLAY = 0x807f87be;
	public static final int SELECTED_OVERLAY = 0x806d76ad;
	public static final double PYThAGORASRATIO = 1.4142135623730950488016887242097;
	private static final Random random;
	//1,4142135623730950488016887242097
	
	static
	{
		random = new Random();
	}
	
	public static String getFormatedTime()
	{
		return String.format("%tY.%tm.%te-%tH.%tM.%tS", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance());
	}
	
	/**
	 * I just like the method from C++ :D
	 * @param s
	 * @param objects
	 */
	public static void printf(String s, Object... objects)
	{
		System.out.println(String.format(s, objects));
	}
	/**
	 * I just like the method from C++ :D
	 * @param s
	 * @param objects
	 */
	public static void printfd(String s, Object... objects)
	{
		System.err.println(String.format(s, objects));
	}
	
	/**
	 * @param r_1
	 * @param r_2
	 * @return
	 */
	public static double getDistance(AABB from, AABB to)
	{

		double a = -((from.getCenterY()) - (to.getCenterY()));
		double b = -((from.getCenterX()) - (to.getCenterX()));
		
		return Math.sqrt((a * a) + (b * b));
	}
	
	public static double getDistance(int fromX, int fromY, int toX, int toY)
	{

		double a = -((fromY) - (toY));
		double b = -((fromX) - (toX));
		
		return Math.sqrt((a * a) + (b * b));
	}
	
	/**
	 * @return
	 */
	public static double getDistance(Vec2 from, Vec2 to)
	{

		double a = -((from.getY()) - (to.getY()));
		double b = -((from.getX()) - (to.getX()));
		
		return Math.sqrt((a * a) + (b * b));
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static int getRandomInt(int max, int min)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		return random.nextInt((max - min) + 1) + min;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static int getRandomInt(int max, int min, long seed)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		Random ra = new Random(seed);
		return ra.nextInt((max - min) + 1) + min;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static double getRandomDouble(double max, double min)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		double r = min + (max - min) * random.nextDouble();
		return r;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static double getRandomDouble(double max, double min, long seed)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		Random ra = new Random(seed);
		double r = min + (max - min) * ra.nextDouble();
		return r;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static long getRandomLong(long max, long min, long seed)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		Random ra = new Random(seed);
		long r = min + (max - min) * ra.nextLong();
		return r;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static long getRandomLong(long max, long min)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		long r = min + (max - min) * random.nextLong();
		return r;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static long getRandomSeed()
	{
		long r = Long.MIN_VALUE + (Long.MAX_VALUE - Long.MIN_VALUE) * random.nextLong();
		return r;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static float getRandomFloat(float max, float min, long seed)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		Random ra = new Random(seed);
		float r = min + (max - min) * ra.nextFloat();
		return r;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static float getRandomFloat(float max, float min)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		float r = min + (max - min) * random.nextFloat();
		return r;
	}
	
	public static boolean flipACoin()
	{
		return getRandomInt(1, 0) == 1;
	}
	
	@FunctionalInterface
	public interface Function1
	{
		public void apply();
	}
	
	public static void decide(Function1... f1)
	{
		int i = Util.getRandomInt(f1.length - 1, 0);
		
		f1[i].apply();
	}
	
	/**
	 * 
	 * @param falseChance Must be bigger than 0!
	 * @return
	 */
	public static boolean decide(int falseChance)
	{
		return getRandomInt(falseChance, 0) == 1;
	}

	/**
	 * 
	 * @param fromx1 From X
	 * @param fromy1 From Y
	 * @param fromx2 To X
	 * @param fromy2 To Y
	 * @return angle
	 */
	public static double countAngle(double fromx1, double fromy1, double fromx2, double fromy2)
	{
		return -Math.toDegrees(Math.atan2(fromx1 - fromx2, fromy1 - fromy2));
	}
	
	public static double countAngle(Vec2 from, Vec2 to)
	{
		return -Math.toDegrees(Math.atan2(from.getX() - to.getX(), from.getY() - to.getY()));
	}

	public static boolean isInCircle(Vec2 circle, int circleRadius, Vec2 obj)
	{
		if (obj == null)
			return false;
		if (circle == null)
			return false;
		if (circleRadius == 0)
			return false;
		
		double i = (obj.getX() - circle.getX()) * (obj.getX() - circle.getX());
		double j = (obj.getY() - circle.getY()) * (obj.getY() - circle.getY());
		double k = Math.sqrt(i + j);
		if (k <= circleRadius)
		{
			return true;
		}
		return false;
	}
	
	public static String getLastClassName(Class<?> clazz) { return clazz.getName().split("\\.")[clazz.getName().split("\\.").length - 1]; }

	public static int getBiggestClosetsSqrt(int count)
	{
		int r = count;
		double temp = Math.sqrt(r);
		int iteration = 0;
		while (true)
		{
			if (temp == Math.floor(temp))
			{
				return (int) temp;
			} else
			{
				r++;
				temp = Math.sqrt(r);
			}
			iteration++;
			if (iteration >= 256)
			{
				System.err.println("More than 256 iterations!");
				return count;
			}
		}
	}
	
	public static boolean isNumber(String l)
	{
		try
		{
			new Long(l);
			return true;
		} catch (NumberFormatException ex)
		{
			return false;
		}
	}
	
	public static boolean isNumberDouble(String l)
	{
		try
		{
			new Double(l);
			return true;
		} catch (NumberFormatException ex)
		{
			return false;
		}
	}
	
	public static boolean isNumberInRange(double min, double max, double number)
	{
		return (number >= min && number <= max);
	}
	
	public static int getIntFromHex(String hex)
	{
		return (int) Long.parseLong(hex, 16);
	}

	public static Object[] combine(Object[] arr1, Object[] arr2)
	{
		int length = arr1.length + arr2.length;
		Object[] res = new Object[length];
		System.arraycopy(arr1, 0, res, 0, arr1.length);
		System.arraycopy(arr2, 0, res, arr1.length, arr2.length);
		return res;
	}
	
	/**
	 * Not recomended
	 */
	public static void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static double getNumberBetween(double min, double max, double number)
	{
		return Math.min(Math.max(number, min), max);
	}

	public static float getNumberBetween(float min, float max, float number)
	{
		return Math.min(Math.max(number, min), max);
	}

	public static int getNumberBetween(int min, int max, int number)
	{
		return Math.min(Math.max(number, min), max);
	}

	
	public static String[] loadDataFromFile(String path)
	{
		File f = new File(path);
		
		List<String> lines = new ArrayList<String>();
		
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			boolean endOfTheFile = false;
			while (!endOfTheFile)
			{
				String line = br.readLine();

				if (line == null)
				{
					endOfTheFile = true;
				} else 
				{
					lines.add(line);
				}
			}

			br.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return lines.toArray(new String[0]);
	}
	
	public static void save(File file, String...text)
	{
		try (PrintWriter out = new PrintWriter(file))
		{
			for (String s : text)
			{
				out.println(s);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveS(File file, Serializable...text)
	{
		try (PrintWriter out = new PrintWriter(file))
		{
			for (Serializable s : text)
			{
				out.println(toString(s));
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static int[] getIntArrayWithRandomValues(int sizeX, int sizeY, int min, int max)
	{
		int[] i = new int[sizeX * sizeY];
		
		for (int j = 0; j < i.length; j++)
		{
			i[j] = getRandomInt(max, min);
		}
		
		return i;
	}


	/**
	 * Stolen from https://stackoverflow.com/a/134918
	 * @param s
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object fromString(String s)
	{
		byte[] data = Base64.getDecoder().decode(s);
		ObjectInputStream ois;
		Object o = null;
		try
		{
			ois = new ObjectInputStream(new ByteArrayInputStream(data));
			o = ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * Stolen from https://stackoverflow.com/a/134918
	 * @param o
	 * @return
	 * @throws IOException
	 */
	public static String toString(Serializable o)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try
		{
			oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(baos.toByteArray());
	}

	public static void printObjects(Object...os)
	{
		for (Object s : os)
		{
			System.out.print(s + " ");
		}
		System.out.println();
	}

	public static void printObjectsHash(Object...os)
	{
		for (Object s : os)
		{
			if (s == null)
				System.out.print(s + " ");
			else
				System.out.print(s.hashCode() + " ");
		}
		System.out.println();
	}
	
	public static void removeNullsFromArray(Object[] arr)
	{
		List<Object> arr2 = new ArrayList<Object>();
		
		for (Object o : arr)
		{
			if (o != null)
				arr2.add(o);
		}
		
		arr = arr2.toArray();
	}

	
	@FunctionalInterface
	public interface DoubleInterface<C, I>
	{
		public void apply(C c, I i);
	}


	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param maxX
	 * @param maxY
	 * @param minX
	 * @param minY
	 * @param di x + i, y + j
	 */
	public static void fillRect(int x, int y, int width, int height, int maxX, int maxY, int minX, int minY, DoubleInterface<Integer, Integer> di)
	{
		
		// Ignore 0 width or height
		if (width == 0 || height == 0)
			return;
			
		// If Width is less than zero -> invert
		if (width < 0)
		{
			x = width + x;
			width = -width;
		}
			
		// If Height is less than zero -> invert
		if (height < 0)
		{
			y = height + y;
			height = -height;
		}
		
		// Don't render if x or y are outside selected box
		// Can be here even thou original width or height were negative cuz I changed it.
		if (x > maxX) return;
		if (y > maxY) return;

		// Limit to screen size
		if (x < minX) { width  = width  - minX + x; x = minX; }
		if (y < minY) { height = height - minY + y; y = minY; }
		if (x + width  > maxX) { width  -= x   + width  - maxX; }
		if (y + height > maxY) { height -= y   + height - maxY; }
		
		// Ignore 0 width or height again cuz of that math up there could mess it up
		if (width == 0 || height == 0)
			return;

		// Render
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				di.apply(i + x, j + y);
			}
		}
	}

	public static long locationValue(int x, int y)
	{
		long seed = y;
		seed = x + (seed << 32); // make x and z semi-independent parts of the seed
		Random r = new Random(seed);
		return getRandomLong(Long.MAX_VALUE, Long.MIN_VALUE, r.nextLong());
	}
	
	public static int maxi(int a, int b)
	{
		return Math.max(a, b);
	}
	
	public static double maxd(double a, double b)
	{
		return Math.max(a, b);
	}
	
	public static float maxf(float a, float b)
	{
		return Math.max(a, b);
	}
	
	public static long maxl(long a, long b)
	{
		return Math.max(a, b);
	}
	
	public static double getRandomAngle()
	{
		return getRandomDouble(360, 0);
	}
	
	/**
	 * Code stolen from https://gist.github.com/yfnick/227e0c12957a329ad138
	 * Thank you for creating this code!
	 * @param data
	 * @return
	 */
	public static byte[] compress(String data)
	{
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data.getBytes());
			gzip.close();
			byte[] compressed = bos.toByteArray();
			bos.close();
			return compressed;
		} catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Code stolen from https://gist.github.com/yfnick/227e0c12957a329ad138
	 * Thank you for creating this code!
	 * @param compressed
	 * @return
	 */
	public static String decompress(byte[] compressed)
	{
		try
		{
			ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
			GZIPInputStream gis = new GZIPInputStream(bis);
			BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
			}
			br.close();
			gis.close();
			bis.close();
			return sb.toString();
		} catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Stolen from @author https://stackoverflow.com/a/80503
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] combineArrays(byte[] a, byte[] b) 
	{
	    int aLen = a.length;
	    int bLen = b.length;

	    byte[] c = (byte[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	/**
	 * Stolen from @author https://stackoverflow.com/a/80503
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> T[] combineArrays(T[] a, T[] b) 
	{
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
		T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	public static float calculateValue(float time, float endTime, float valueStart, float valueEnd)
	{
//		Util.printObjects("### ", time, endTime, valueStart, valueEnd);
		return (time / endTime) * valueEnd - (time / endTime) * valueStart + valueStart;
	}
	
	public static float brazierCurve(float p0, float p1, float p2, float time, float timeEnd)
	{
		float t0 = time;
		float t1 = timeEnd;

		float y0 = calculateValue(t0, t1, p0, p1);

		float y1 = calculateValue(t0, t1, p1, p2);
		
		float py = calculateValue(t0, t1, y0, y1);
		
		return py;
	}
	
	public static Vec2 brazierCurve(float p0x, float p0y, float p1x, float p1y, float p2x, float p2y, float time, float timeEnd)
	{
		float t0 = time;
		float t1 = timeEnd;

		float x0 = calculateValue(t0, t1, p0x, p1x);
		float y0 = calculateValue(t0, t1, p0y, p1y);

		float x1 = calculateValue(t0, t1, p1x, p2x);
		float y1 = calculateValue(t0, t1, p1y, p2y);
		
		float px = calculateValue(t0, t1, x0, x1);
		float py = calculateValue(t0, t1, y0, y1);
		
		return new Vec2(px, py);
	}
	
/*
	public static double getRandomCircleX(double radius)
	{
//		double angle = Math.toRadians(Math.random()*Math.PI*2);
//		return (radius * Math.cos(angle * Math.PI / 180d));
		double angle = Math.toRadians(getRandomDouble(360, 0));
		double r = radius * new Random().nextDouble();
		return r * Math.cos(angle);
	}

	public static double getRandomCircleY(double radius)
	{
//		double angle = Math.toRadians(Math.random()*Math.PI*2);
//		return (radius * Math.sin(angle * Math.PI / 180d));
		double angle = Math.toRadians(getRandomDouble(360, 0));
		double r = radius * new Random().nextDouble();
		return r * Math.sin(angle);
	}*/
	
	/**
	 * TODO: Create regex escape method .$|()[{^?*+\\
	 */
}
