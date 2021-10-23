package steve6472.sge.main.game;

import org.joml.Vector3i;
import steve6472.sge.main.util.MathUtil;

import java.util.Objects;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 17.10.2020
 * Project: CaveGame
 *
 ***********************/
public class VoxPos
{
	private final int x;
	private final int y;
	private final int z;

	public VoxPos(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public VoxPos()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public VoxPos(VoxPos other)
	{
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public VoxPos up(int n)
	{
		return offset(Direction.UP, n);
	}

	public VoxPos up()
	{
		return up(1);
	}

	public VoxPos down(int n)
	{
		return offset(Direction.DOWN, n);
	}

	public VoxPos down()
	{
		return down(1);
	}

	public VoxPos north(int n)
	{
		return offset(Direction.NORTH, n);
	}

	public VoxPos north()
	{
		return north(1);
	}

	public VoxPos east(int n)
	{
		return offset(Direction.EAST, n);
	}

	public VoxPos east()
	{
		return east(1);
	}

	public VoxPos south(int n)
	{
		return offset(Direction.SOUTH, n);
	}

	public VoxPos south()
	{
		return south(1);
	}

	public VoxPos west(int n)
	{
		return offset(Direction.WEST, n);
	}

	public VoxPos west()
	{
		return west(1);
	}

	public VoxPos offset(Direction direction, int steps)
	{
		return new VoxPos(x + direction.getNormalI().x * steps, y + direction.getNormalI().y * steps, z + direction.getNormalI().z * steps);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getZ()
	{
		return z;
	}

	public Vector3i toVec3i()
	{
		return new Vector3i(x, y, z);
	}

	public VoxPos copy()
	{
		return new VoxPos(this);
	}

	/*
	 * Math
	 */

	public VoxPos add(VoxPos other)
	{
		return new VoxPos(x + other.x, y + other.y, z + other.z);
	}

	public VoxPos add(int x, int y, int z)
	{
		return new VoxPos(x + this.x, y + this.y, z + this.z);
	}

	public VoxPos mul(int factor)
	{
		return mul(factor, factor, factor);
	}

	public VoxPos mul(int x, int y, int z)
	{
		return new VoxPos(x * this.x, y * this.y, z * this.z);
	}

	public VoxPos floorMod(int mod)
	{
		return new VoxPos(Math.floorMod(x, mod), Math.floorMod(y, mod), Math.floorMod(z, mod));
	}

	public VoxPos clamp(int min, int max)
	{
		return new VoxPos(MathUtil.clamp(x, min, max), MathUtil.clamp(y, min, max), MathUtil.clamp(z, min, max));
	}

	public static VoxPos min(VoxPos a, VoxPos b)
	{
		return new VoxPos(Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.min(a.getZ(), b.getZ()));
	}

	public static VoxPos max(VoxPos a, VoxPos b)
	{
		return new VoxPos(Math.max(a.getX(), b.getX()), Math.max(a.getY(), b.getY()), Math.max(a.getZ(), b.getZ()));
	}

	public boolean equals(int x, int y, int z)
	{
		return this.x == x && this.y == y && this.z == z;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VoxPos blockPos = (VoxPos) o;
		return x == blockPos.x && y == blockPos.y && z == blockPos.z;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(x, y, z);
	}

	@Override
	public String toString()
	{
		return "VoxPos{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
