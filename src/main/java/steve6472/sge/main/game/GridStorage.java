package steve6472.sge.main.game;

import steve6472.sge.test.LongMap;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 30.9.2018
 * Project: 3DTest
 *
 ***********************/
public class GridStorage<T>
{
	private LongMap<T> objects;

	public GridStorage()
	{
		this.objects = new LongMap<>();
	}

	public static long getKey(int x, int z)
	{
		boolean zNegative = z < 0;

		if (zNegative) z = -z;

		long l = (long) x << 32 | (long) z;
		if (zNegative) l = -l;

		return l;
	}

	public T get(int x, int z)
	{
		long key = getKey(x, z);

		if (objects.containsKey(key))
			return objects.get(getKey(x, z));

		return null;
	}

	public T get(long key)
	{
		if (objects.containsKey(key))
			return objects.get(key);
		return null;
	}

	public void put(int x, int z, T obj)
	{
		objects.put(getKey(x, z), obj);
	}

	public void remove(int x, int z)
	{
		long key = getKey(x, z);
		if (objects.containsKey(key))
			objects.remove(key);
	}

	public LongMap<T> getMap()
	{
		return objects;
	}
}
